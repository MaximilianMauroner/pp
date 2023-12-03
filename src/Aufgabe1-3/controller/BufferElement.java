package controller;

import model.Entity.Entity;
import model.Position;

/**
 * Class for a game buffer element (e.g. an Entity and its position)
 * <p>
 * Modularization Units:
 * - Class for objects that contain an entity and its position
 * - Objects for the entities and positions
 * <p>
 * Abstraction: A encapsulation of the abstract concept of an entity (in this case: ant, hive, etc.) and its position (on the game map)
 */
// GOOD (object oriented): While being only a small class here we use encapsulation to hide the details of the implementation of the buffer element.
public class BufferElement {


    private Position position;
    private Entity entity;


    /**
     * Creates a new buffer element
     * @param entity the entity to be added (precondition: entity != null)
     */
    public BufferElement(Entity entity) {
        this.entity = entity;
        this.position = entity.getPosition();
    }

    /**
     * Returns the position of the buffer element
     */
    public Position getPosition() {
        return position;
    }


    /**
     * Sets the position of the buffer element
     * @param position the position to be set (precondition: position != null)
     */
    public void setPosition(Position position) {
        this.position = position;
    }

    /**
     * Returns the entity of the buffer element
     */
    public Entity getEntity() {
        return entity;
    }

    /**
     * Sets the entity of the buffer element
     * @param entity the entity to be set (precondition: entity != null)
     */
    public void setEntity(Entity entity) {
        this.entity = entity;
    }

    /**
     * Returns true if the buffer element is equal to the given object
     * @param o the object to be compared to (precondition: o != null)
     * @return true if the buffer element is equal to the given object
     */
    @Override
    public boolean equals(Object o) {
        if (o != null && o.getClass() == this.getClass()) {
            BufferElement bufferElement = (BufferElement) o;
            return bufferElement.position.equals(this.position);
        }
        return false;
    }
}
