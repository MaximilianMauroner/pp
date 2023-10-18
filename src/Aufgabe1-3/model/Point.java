package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Class for the points of the game
 * Points have a specified position on the grid and a list of entity objects
 * Not every position has a point object
 */
public class Point {
    private final List<Entity> entities;
    private Position position;

    private boolean hasObstacle = false;

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

    private void sortedAdd(Entity entity) {
        if (entities == null || entities.size() == 0) entities.add(entity);
        else {
            if (entities.get(entities.size() / 2).getPriority() < entity.getPriority()) {

            }
        }
    }

    //credits go to: https://www.geeksforgeeks.org/binary-search/
    private int binarySearch(int arr[], int l, int r, int x) {
        while (l <= r) {
            int m = l + (r - l) / 2;
            if (arr[m] == x)
                return m;
            if (arr[m] < x)
                l = m + 1;
            else
                r = m - 1;
        }
        return -1;
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
     *
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


    @Override
    public String toString() {
        return "Point{" +
                "entities=" + entities.toString() +
                ", position=" + position.toString() +
                '}';
    }

}
