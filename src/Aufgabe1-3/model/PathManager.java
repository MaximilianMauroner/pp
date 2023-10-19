package model;


import controller.GameState;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

/**
 * Manages the optimal paths of the simulation
 * STYLE: Objektorientiertes Programmierung (es wird hier das Singleton Pattern verwendet was klar auf Objektorientierung hinweist)
 */
public class PathManager {

    private static PathManager instance = null;
    private List<Path> paths;
    private List<Position> startPositions;
    private List<Position> endPositions;
    private GameState gameState;

    private PathManager(GameState gameState) {
        paths = new ArrayList<>();
        startPositions = new ArrayList<>();
        endPositions = new ArrayList<>();
        this.gameState = gameState;
    }

    public static PathManager getInstance(GameState gameState) {
        if (instance == null) {
            instance = new PathManager(gameState);
        }
        instance.gameState = gameState;
        return instance;
    }

    public void addStart(Position position) {
        startPositions.add(position);
    }

    public void addEnd(Position position) {
        endPositions.add(position);
    }

    public void registerNewPaths(Position position) {
        endPositions.add(position);
        for (Position start : startPositions) {
            paths.add(a_star(start, position));
        }
    }

    public void deregisterPaths(Position position) {
        endPositions.remove(position);
        paths.removeIf(path -> path.contains(position));
    }

    public void removePosition(Position position) {
        startPositions.remove(position);
        endPositions.remove(position);

        paths.removeIf(path -> path.contains(position));
    }

    public void calculatePaths() {
        for (Position start : startPositions) {
            for (Position end : endPositions) {
                paths.add(a_star(start, end));
            }
        }
    }

    public void clear() {
        startPositions.clear();
        endPositions.clear();
        paths.clear();
    }


    // STYLE: Berechnung vom Pfad ist prozedural
    // (Node hat nur public Variablen, static utility functions, Kommunikation im weiteren Sinne über die PriorityQueue, ...)

    /**
     * Calculates the optimal path from the start position to the end position
     * @param start the start position
     * @param end the end position
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

}

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
