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
                return;
            }
        }

        entities.add(trail);
    }

    @Override
    public Point clone() {
        List<Entity> e = new ArrayList<>();
        for (Entity entity : entities) {
            e.add(entity.clone());
        }
        return new Point(position.clone(), e);
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
