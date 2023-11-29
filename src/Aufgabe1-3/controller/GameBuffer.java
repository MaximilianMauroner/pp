package controller;

import model.Entity.Entity;
import model.Position;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;


/**
 * Class for the game buffer
 * Contains a utility method to add entitites to a buffer, which then may be propagated to the view
 * <p>
 * Modularization Units:
 * - Module for adding entities to a buffer
 * <p>
 * Abstraction: A Wrapper so to say facilitating the addition of entities to the buffer
 */
// BAD (object oriented): This class is not needed at all. It is only a module for a single method.
public class GameBuffer {


    /**
     * Adds an entity to the buffer
     *
     * @param buffer the buffer to which the entity is added (precondition: buffer != null)
     * @param entity the entity to be added (precondition: entity != null)
     */
    public static void add(BlockingQueue<BufferElement> buffer, Entity entity, Position oldPosition) {
        try {
            BufferElement bufferElement = new BufferElement(entity);
            buffer.put(bufferElement);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

