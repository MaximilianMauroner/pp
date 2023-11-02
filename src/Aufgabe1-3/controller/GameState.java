package controller;

import model.Entity.Entity;
import model.Point;
import model.Position;
import model.Status;

import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Class for the game state
 * Contains point objects and the parameters of the game
 * <p>
 * Modularization Units:
 * - Objects for storing the points of the game and the parameters of the game
 * - Module for all simulated entities and their manipulation of their environment
 * <p>
 * Abstraction: A simulation of the abstract concept of a game state, which is a collection of points that contain entities
 */
public class GameState {

    final ConcurrentHashMap<Position, Point> points;
    final Status status;

    /**
     * Generates the game state with randomized parameters
     *
     * @param points points of the game containing entities
     * @param status parameters of the game
     */
    public GameState(ConcurrentHashMap<Position, Point> points, Status status) {
        this.points = points;
        this.status = status;
    }

    /**
     * Runs one iteration of the game
     */
    public void getNextFrame(BlockingQueue<BufferElement> queue) {
        for (Point p : points.values()) {
            ArrayList<Entity> entities = new ArrayList<>(p.getEntities());
            for (Entity e : entities) {
                e.run(this, status, p, queue);
            }
        }
    }

    /**
     * Returns the points of the game
     */
    public ConcurrentHashMap<Position, Point> getPoints() {
        return this.points;
    }


    /**
     * Returns the point at the given position if it exists
     *
     * @param position position of the point
     */
    public Point getPoint(Position position) {
        if (position != null) {
            return points.get(position);
        }
        return null;
    }

    public Point getPoint(int x, int y) {
        return points.get(new Position(x, y));
    }


    /**
     * Sets the point at the given position
     *
     * @param point point to be set
     */
    public void setPoint(Point point) {
        points.put(point.getPosition(), point);
    }

    public boolean hasPosition(Position position) {
        return points.containsKey(position);
    }

    public boolean hasPosition(int x, int y) {
        return points.containsKey(new Position(x, y));
    }

    /**
     * Returns the parameters of the game
     */
    public Status getStatus() {
        return status;
    }
}
