package src.model;

import java.util.ArrayList;
import java.util.List;

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

    public Position getPosition() {
        return position;
    }


    public List<Entity> getEntities() {
        return entities;
    }

    public void addTrail(Trail trail) {
        for (Entity e : entities) {
            if (e instanceof Trail) {
                ((Trail) e).combineTrails(trail);
                return;
            }
        }
        entities.add(trail);
    }


    public double getTrail() {
        for (Entity e : entities) {
            if (e instanceof Trail) {
                return ((Trail) e).getStrength();
            }
        }
        return 0;
    }

    public boolean updateObstacle() {
        for (Entity e : entities) {
            if (e instanceof Obstacle) {
                return true;
            }
        }
        return false;
    }
    public boolean hasObstacle() {
        return this.hasObstacle;
    }

}
