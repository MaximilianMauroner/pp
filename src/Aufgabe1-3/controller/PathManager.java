package controller;


import datastore.DataManager;
import datastore.Operation;
import model.*;
import model.Entity.OptimalPathPoint;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

/**
 * Manages the optimal paths of the simulation
 *
 * Modularization Units:
 * - Objects for the paths and positions as well as game state (for accessing the points)
 * - Module that adds all utility for adding, deleting and calculating paths
 * - Class of Nodes for the a* algorithm
 *
 * Abstraction: A representation of the real world concept of multiple paths. Sort of the base class for the path objects
 */
public class PathManager {

    private List<Path> paths;
    private List<Position> startPositions;
    private List<Position> endPositions;
    private GameState gameState;

    public PathManager(GameState gameState) {
        paths = new ArrayList<>();
        startPositions = new ArrayList<>();
        endPositions = new ArrayList<>();
        this.gameState = gameState;
    }


    /**
     * Adds a start position from which paths are calculated
     *
     * @param position the start position
     */
    public void addStart(Position position) {
        startPositions.add(position);
    }

    /**
     * Adds an end position to which paths are calculated
     *
     * @param position the end position
     */
    public void addEnd(Position position) {
        endPositions.add(position);
    }

    /**
     * Adds the position to end position and calculates a path from all start positions to the end position
     *
     * @param position the start position
     */
    public void registerNewPaths(Position position) {
        endPositions.add(position);
        for (Position start : startPositions) {
            paths.add(a_star(start, position));
        }
    }

    /**
     * Removes the position from end position and removes all paths that contain the position
     *
     * @param position the end position
     */
    public void deregisterPaths(Position position) {
        endPositions.remove(position);
        paths.removeIf(path -> path.contains(position));
    }

    /**
     * Removes the position both start and end positions and removes all paths that contain the position
     *
     * @param position the start position
     */
    public void removePosition(Position position) {
        startPositions.remove(position);
        endPositions.remove(position);

        paths.removeIf(path -> path.contains(position));
    }

    /**
     * Gathers all metrics the paths have calculated and adds them to the simulation data
     */
    public void calculatePaths() {
        for (Path path : paths) {
            DataManager.getInstance().updateComplexField("pathMetric", path.getPathMetric());
        }
        DataManager.getInstance().summarizeComplexField("pathMetric", new Operation.Mean());
    }

    /**
     * Clears all paths and positions
     */
    public void clear() {
        startPositions.clear();
        endPositions.clear();
        paths.clear();
    }


    // STYLE: Berechnung vom Pfad ist prozedural
    // (Node hat nur public Variablen, static utility functions, Kommunikation im weiteren Sinne über die PriorityQueue, ...)

    /**
     * Calculates the optimal path from the start position to the end position using a* algorithm
     *
     * @param start the start position
     * @param end   the end position
     * @return the optimal path
     */
    private Path a_star(Position start, Position end) {
        Path path = new Path();
        Node target = new Node(end, null);
        PriorityQueue<Node> openList = new PriorityQueue<>();
        List<Node> closedList = new ArrayList<>();

        openList.add(new Node(start, null));

        while (!openList.isEmpty()) {
            Node current = openList.poll();
            closedList.add(current);

            // added relaxation to reduce time
            if (current.equals(target) || current.position.withinRadius(target.position, Parameters.FOOD_SIZE)) {
                target = current;
                break;
            }

            for (Position position : current.position.getNeighbours()) {
                Node neighbor = new Node(position, current);
                Point point = gameState.getPoint(position);

                if (closedList.contains(neighbor) || (point != null && point.hasObstacle())) {
                    continue;
                }

                double newPathCost = current.g + Node.getDistance(current, neighbor);
                if (newPathCost < neighbor.g || !openList.contains(neighbor)) {
                    neighbor.g = newPathCost;
                    neighbor.h = Node.getDistance(neighbor, target);
                    neighbor.f = neighbor.g + neighbor.h;
                    neighbor.parent = current;

                    if (!openList.contains(neighbor)) {
                        openList.add(neighbor);
                    }
                }
            }
        }

        // backtracking
        while (target.parent != null) {
            Point point = this.gameState.getPoint(target.position);
            if (point == null) {
                point = new Point(target.position, new OptimalPathPoint(path));
                this.gameState.setPoint(point);
            } else {
                point.addEntity(new OptimalPathPoint(path));
            }

            path.addPosition(target.position);
            target = target.parent;
        }

        return path;
    }


    /**
     * Represents a position in a tree. Used for the a* algorithm
     *
     * Modularization Units:
     * - Module for storing tree node data and utility methods
     *
     * Abstraction: A representation of the nodes the a star algorithm creates as it "explores" a grid of positions. Sort of a simulation of a tree
     */
    class Node implements Comparable<Node> {
        public Position position;
        public Node parent;
        public double g;
        public double h;
        public double f;

        public Node(Position position, Node parent) {
            this.position = position;
            this.parent = parent;
        }


        public static int getDistance(Node n1, Node n2) {
            int dx = Math.abs(n1.position.getX() - n2.position.getX());
            int dy = Math.abs(n1.position.getY() - n2.position.getY());

            if (dx > dy)
                return 14 * dy + 10 * (dx - dy);
            else
                return 14 * dx + 10 * (dy - dx);
        }

        @Override
        public boolean equals(Object o) {
            if (o instanceof Node) {
                Node n = (Node) o;
                return n.position.equals(this.position);
            }
            return false;
        }

        @Override
        public int compareTo(Node o) {
            return Double.compare(this.f, o.f);
        }
    }
}