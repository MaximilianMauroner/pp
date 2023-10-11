package src.model;

public class Point {
    private Entity[] entities;
    private Position position;

    public Point(Position position, Entity[] entities) {
        this.position = position;
        this.entities = entities;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Entity[] getEntities() {
        return entities;
    }

    public void addTrail(Trail trail) {
        for (Entity e : entities) {
            if (e instanceof Trail) {
                ((Trail) e).addStrength(trail.getStrength());
            }
        }
    }

    @Override
    public Point clone() {
        return new Point(position.clone(), entities.clone());
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
