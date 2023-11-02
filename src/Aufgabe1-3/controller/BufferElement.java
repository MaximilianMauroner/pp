package controller;

import model.Entity.Entity;
import model.Position;

public class BufferElement {


    private Position position;
    private Entity entity;

    public BufferElement(Entity entity) {
        this.entity = entity;
        this.position = entity.getPosition();
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Entity getEntity() {
        return entity;
    }

    public void setEntity(Entity entity) {
        this.entity = entity;
    }

    public boolean equals(BufferElement o) {
        if (o.getClass() == this.getClass()) {
            return o.position.equals(this.position);
        }
        return false;
    }
}
