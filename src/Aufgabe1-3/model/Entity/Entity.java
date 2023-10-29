package model.Entity;

import controller.GameState;
import model.Point;
import model.Position;
import model.Status;

/**
 * Interface for the entities of the game
 * Entities are the objects that are placed on the grid
 * They can be ants, food, trails, etc.
 * STYLE: eigentlich ist hier auch objektorientierte Programmierung zu sehen, da Entity der Obertyp von Ant, Food, etc. ist
 * und Untertyp-Beziehungen oft bei der objektorientierten Programmierung verwendet werden
 */
public interface Entity extends Comparable<Entity> {

    /**
     * Runs the entity's logic
     *
     * @param gameState the game state of the game
     * @param status    the status of the game
     * @param point     the point where the entity is located
     */
    void run(GameState gameState, Status status, Point point);

    /**
     * Returns a clone of the entity
     */
    Entity clone();

    /**
     * Returns the viewing priority of the entity
     * @return the viewing priority
     */
    int getPriority();

    /**
     * Compares the viewing priority of two entities
     * @param o the entity to be compared to
     * @return 0 if the priorities are equal, 1 if the priority of the other entity is higher, -1 if the priority of this entity is higher
     */
    @Override
    default int compareTo(Entity o) {
        if (o.getPriority() == this.getPriority())
            return 0;
        return o.getPriority() > this.getPriority() ? 1 : -1;
    }

    /**
     * Returns the position of the entity
     */
    Position getPosition();

    /**
     * Sets the position of the entity
     */
    void setPosition(Position position);
}
