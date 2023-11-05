package model;

import controller.GameState;
import model.Entity.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// BAD (object oriented): However good we implemented dynamic binding, this class has a lot of methods which indicates high coupling.
/**
 * Class for the points of the game
 * Points have a specified position on the grid and a list of entity objects
 * Not every position has a point object
 * <p>
 * Modularization Units:
 * - Contains objects (in a list) for entities currently on this point (e.g. ants, trails, obstacles).
 * - Modularize the logic for manipulating the entities on the point (e.g. adding, removing, updating, querying)
 * <p>
 * Abstraction: Represents the objects of a real world position on a grid, which can contain multiple entities.
 */
public class Point {
    private final List<Entity> entities;
    private final Position position; // (client-side history-constraint: position != null)


    private int hasObstacle = -1;
    private int hasHive = -1;

    private int hasTrail = -1;

    /**
     * Initialize new point with a position and a list of entities
     * @param position position of the point
     * @param entities list of entities on the point (precondition: entities is not null)
     */
    public Point(Position position, List<Entity> entities) {
        this.position = position;
        this.entities = entities;
        this.updateEntities();
    }

    /**
     * Initialize new point with a position and a single entity
     * @param position position of the point
     * @param entity entity on the point
     */
    public Point(Position position, Entity entity) {
        this.position = position;
        this.entities = new ArrayList<>();
        this.entities.add(entity);
        this.updateEntities();
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
     * Adds an entity to the point
     *
     * @param entity entity to be added (precondition: entity is not null)
     */
    public void addEntity(Entity entity) {
        entity.setPosition(this.position);
        entities.add(entity);
        Collections.sort(entities);
        this.updateEntities();
    }

    /**
     * Removes an entity from the point
     * @param entity entity to be removed (precondition: entity is not null)
     */
    public void removeEntity(Entity entity) {
        entities.remove(entity);
        if (entity.getClass() == Hive.class) {
            Hive e = (Hive) entity;
            e.getColony().getHives().remove(e.getId());
        } else if (entity.getClass() == Ant.class) {
            Ant e = (Ant) entity;
            e.getColony().getAnts().remove(e.getId());
        }
        this.updateEntities();
    }


    /**
     * Adds a trail to the point
     *
     * @param trail trail to be added (precondition: trail is not null)
     */
    public void addTrail(Trail trail) {
        if (hasTrail != -1) {
            Trail t = (Trail) entities.get(hasTrail);
            t.combineTrails(trail);
            return;
        }
        this.addEntity(trail);
    }

    // BAD (procedural): This also stretches to the methods of the Trail class, but the control flow is a bit difficult to follow.
    /**
     * Adds a trail with more than one trail object to surrounding points within a specified radius
     *
     * @param gameState game-state to get the point objects
     * @param ant       ant so the ant can be associated with the trail
     */
    public void addTrail(GameState gameState, Ant ant) {
        int radius = Math.ceilDiv(Parameters.TRAIL_SIZE, 2);
        int x = this.position.getX();
        int y = this.position.getY();

        for (int i = x - radius; i <= x + radius; i++) {
            for (int j = y - radius; j <= y + radius; j++) {
                Position p = new Position(i, j);
                if (this.position.withinRadius(p, radius)) {
                    double strength = (radius - this.position.euclideanDistance(p) / radius);

                    if (gameState.hasPosition(p)) {
                        Point point = gameState.getPoint(p);
                        point.addTrail(new Trail(strength, ant));
                    } else {
                        gameState.setPoint(new Point(p, new Trail(strength, ant)));
                    }
                }
            }
        }
    }

    /**
     * Gets the strength of the trail on the point (if no trail is present, 0 is returned)
     */
    public double getTrail() {
        if (hasTrail != -1) {
            return ((Trail) entities.get(hasTrail)).getStrength();
        }
        return 0;
    }

    /**
     * Returns whether the point has an obstacle
     */
    public boolean hasObstacle() {
        return this.hasObstacle != -1;
    }

    /**
     * Notifies the hive that it has been visited
     */
    public void updateHiveVisited() {
        if (hasHive != -1) {
            Hive e = (Hive) entities.get(hasHive);
            e.updateVisited();
        }
    }

    /**
     * Returns whether the point has a hive
     * @return true if the point has a hive
     */
    public boolean hasHive() {
        return this.hasHive == -1;
    }

    /**
     * Starts checks for the entities on the point (e.g. is there a Hive, Obstacle, etc.)
     */
    private void updateEntities() {
        this.hasObstacle = -1;
        this.hasHive = -1;
        this.hasTrail = -1;
        for (int i = 0; i < this.entities.size(); i++) {
            Entity e = this.entities.get(i);
            if (e.getClass() == Hive.class) {
                this.hasHive = i;
            }
            if (e.getClass() == Obstacle.class) {
                this.hasObstacle = i;
            }
            if (e.getClass() == Trail.class) {
                this.hasTrail = i;
            }
        }
    }
}
