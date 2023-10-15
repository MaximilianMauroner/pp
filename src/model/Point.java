package src.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Class for the points of the game
 * Points have a specified position on the grid and a list of entities
 * Not every position has a point object
 */
public class Point {
    private List<Entity> entities;
    private Position position;

    private  boolean hasObstacle = false;

    public Point(Position position, List<Entity> entities) {
        this.position = position;
        this.entities = entities;
        this.hasObstacle = this.updateObstacle();
    }
    public Point(Position position, Entity entity) {
        this.position = position;
        this.entities = new ArrayList<>();
        this.entities.add(entity);
        this.hasObstacle = this.updateObstacle();
    }


    /**
     * Returns the position of the point
     */
    public Position getPosition() {
        return position;
    }


    /**
     * Returns the list of entities on the point
     */
    public List<Entity> getEntities() {
        return entities;
    }


    /**
     * Adds a trail to the point
     * @param trail trail to be added
     */
    public void addTrail(Trail trail) {
        for (Entity e : entities) {
            if (e instanceof Trail) {
                ((Trail) e).combineTrails(trail);
                return;
            }
        }
        entities.add(trail);
    }


    /**
     * Gets the strength of the trail on the point
     */
    public double getTrail() {
        for (Entity e : entities) {
            if (e instanceof Trail) {
                return ((Trail) e).getStrength();
            }
        }
        return 0;
    }


    /**
     * Checks wether the point has an obstacle
     */
    private boolean updateObstacle() {
        for (Entity e : entities) {
            if (e instanceof Obstacle) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns wether the point has an obstacle
     */
    public boolean hasObstacle() {
        return this.hasObstacle;
    }

}
