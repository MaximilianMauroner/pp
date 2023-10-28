package model;

import controller.GameState;
import model.Entity.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * Class for the points of the game
 * Points have a specified position on the grid and a list of entity objects
 * Not every position has a point object
 */
public class Point {
    private final List<Entity> entities;
    private final Position position;


    private int hasObstacle = -1;
    private int hasHive = -1;

    private int hasTrail = -1;

    public Point(Position position, List<Entity> entities) {
        this.position = position;
        this.entities = entities;
        this.updateEntities();
    }

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

    public void addEntity(Entity entity) {
        entity.setPosition(this.position);
        entities.add(entity);
        Collections.sort(entities);
        this.updateEntities();
    }

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
     * @param trail trail to be added
     */
    public void addTrail(Trail trail) {
        if (hasTrail != -1) {
            Trail t = (Trail) entities.get(hasTrail);
            t.combineTrails(trail);
            return;
        }
        this.addEntity(trail);
    }

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
     * Gets the strength of the trail on the point
     */
    public double getTrail() {
        if (hasTrail != -1) {
            return ((Trail) entities.get(hasTrail)).getStrength();
        }
        return 0;
    }


    /**
     * Checks whether the point has an obstacle
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

    /**
     * Returns whether the point has an obstacle
     */
    public boolean hasObstacle() {
        return this.hasObstacle != -1;
    }

    public void updateHiveVisited() {
        if (hasHive != -1) {
            Hive e = (Hive) entities.get(hasHive);
            e.updateVisited();
        }
    }

    /**
     * Returns whether the point has a hive
     */
    public boolean hasHive() {
        return this.hasHive == -1;
    }

}
