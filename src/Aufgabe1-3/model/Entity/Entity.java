package model.Entity;

import controller.BufferElement;
import controller.GameBuffer;
import controller.GameState;
import model.Point;
import model.Position;
import model.Status;

import java.util.concurrent.BlockingQueue;


// GOOD (object oriented): Dynamic Binding: This is true for all subclasses of Entity, but this part of the program is a good example for dynamic binding.
// Wherever we could use a concrete type, we use the abstract type Entity instead (see Point.java).


/**
 * Interface for the entities of the game
 * Entities are the objects that are placed on the grid
 * They can be ants, food, trails, etc.
 * <p>
 * Modularization Units:
 * - Modularization methods for manipulating the entities and their properties (e.g. doing some behaviour, updating, comparing, etc.)
 * - In some way a component that is used to ensure the modularity of the game logic
 * <p>
 * Abstraction: Is the base-type (super-type) for all things that populate the simulation.
 * <p>
 * STYLE: eigentlich ist hier auch objektorientierte Programmierung zu sehen, da Entity der Obertyp von Ant, Food, etc. ist
 * und Untertyp-Beziehungen oft bei der objektorientierten Programmierung verwendet werden
 */
public interface Entity extends Comparable<Entity> {

    /**
     * Runs the entity's logic
     *
     * @param gameState the game state of the game (precondition: gameState != null)
     * @param status    the status of the game (precondition: status != null)
     * @param point     the point where the entity is located (precondition: point != null)
     * @param gameBuffer the buffer to which the entity is added (precondition: gameBuffer != null)
     */
    void run(GameState gameState, Status status, Point point, BlockingQueue<BufferElement> gameBuffer);

    /**
     * Returns a clone of the entity
     */
    Entity clone();

    /**
     * Returns the viewing priority of the entity
     *
     * @return the viewing priority
     */
    int getPriority();

    /**
     * Compares the viewing priority of two entities
     *
     * @param o the entity to be compared to (precondition: o != null)
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
     *
     * @return the position
     */
    Position getPosition();

    /**
     * Sets the position of the entity
     *
     * @param position the position to be set (precondition: position != null)
     */
    void setPosition(Position position);
}
