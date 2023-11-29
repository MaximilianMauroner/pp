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

    private final ConcurrentHashMap<Position, Point> points;
    private final Status status;

    /**
     * Generates the game state with randomized parameters
     *
     * @param points points of the game containing entities (precondition: points != null)
     * @param status parameters of the game (precondition: status != null)
     */
    public GameState(ConcurrentHashMap<Position, Point> points, Status status) {
        this.points = points;
        this.status = status;
    }

    /**
     * Runs one iteration of the game
     * @param queue queue to which the entities are added (precondition: queue != null)
     */
    public void getNextFrame(BlockingQueue<BufferElement> queue) {
        points.values().forEach(p -> {
            ArrayList<Entity> entities = new ArrayList<>(p.getEntities());
            entities.forEach(e -> e.run(this, status, p, queue));
        });
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

    /**
     * Returns the point at the given position if it exists, otherwise null
     *
     * @param x x-coordinate of the point
     * @param y y-coordinate of the point
     */
    public Point getPoint(int x, int y) {
        return points.get(new Position(x, y));
    }


    /**
     * Sets the point at the given position
     *
     * @param point point to be set (precondition: point != null)
     */
    public void setPoint(Point point) {
        points.put(point.getPosition(), point);
    }

    /**
     * Checks wether a given point exists
     *
     * @param position position of the point to be checked
     * @return true if the point exists, false otherwise
     */
    public boolean hasPosition(Position position) {
        return points.containsKey(position);
    }

    /**
     * Checks wether a given point exists
     * @param x x-coordinate of the point to be checked
     * @param y y-coordinate of the point to be checked
     * @return true if the point exists, false otherwise
     */
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
