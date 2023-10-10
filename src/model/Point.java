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
}
