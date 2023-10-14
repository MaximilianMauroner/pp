package src.model;

import java.util.ArrayList;
import java.util.List;

public class Point {
    private List<Entity> entities;
    private Position position;

    public Point(Position position, List<Entity> entities) {
        this.position = position;
        this.entities = entities;
    }
    public Point(Position position, Entity entity) {
        this.position = position;
        this.entities = new ArrayList<>();
        this.entities.add(entity);
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public List<Entity> getEntities() {
        return entities;
    }

    public void addTrail(Trail trail) {
        for (Entity e : entities) {
            if (e instanceof Trail) {
                ((Trail) e).changeStrength(trail.getStrength());
                ((Trail) e).updateOrigin(trail.getOrigin());
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

}
