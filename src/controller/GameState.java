package src.controller;

import src.model.Entity;
import src.model.Point;
import src.model.Position;
import src.model.Status;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Class for the game state
 * Contains point objects and the parameters of the game
 */
public class GameState {

    ConcurrentHashMap<Position, Point> points;

    int foodCount = 0;

    Status status;

    /**
     * Generates the game state with randomized parameters
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
    public void getNextFrame() {
        for (Point p : points.values()) {
            ArrayList<Entity> entities = new ArrayList<>(p.getEntities());
            for (Entity e : entities) {
                e.run(this, status, p);
            }
        }
    }


    /**
     * Returns the points of the game
     */
    public ConcurrentHashMap<Position,Point> getPoints() {
        return this.points;
    }


    /**
     * Returns the point at the given position
     * @param position position of the point
     */
    public Point getPoint(Position position) {
        return points.get(position);
    }


    /**
     * Sets the point at the given position
     * @param point point to be set
     */
    public void setPoint(Point point) {
        points.put(point.getPosition(), point);
    }


    /**
     * Returns the parameters of the game
     */
    public Status getStatus() {
        return status;
    }


    /**
     * Increments the food count (e.g. when ant reaches hive)
     */
    public void addFood() {
        this.foodCount++;
        System.out.println("Food added to hive" + this.foodCount);
    }
}
