package model.Entity;

import controller.BufferElement;
import controller.GameBuffer;
import controller.GameState;
import model.Point;
import model.Position;
import model.Status;

import java.util.concurrent.BlockingQueue;

/**
 * Class for the food entity
 * Food is an entity that can be placed on the grid
 * It is used to feed the ants
 * <p>
 * Modularization Units:
 * - same as in Entity.java as no additional methods are added (does however contain the object of its current position)
 * <p>
 * Abstraction: A representation of a real world piece of food
 */
public class Food implements Entity {

    private Position position;

    /**
     * Runs the food's logic
     *
     * @param gameState the game state of the game (precondition: gameState != null)
     * @param status    the status of the game (precondition: status != null)
     * @param point     the point where the entity is located (precondition: point != null)
     * @param queue the buffer to which the entity is added (precondition: gameBuffer != null)
     */
    @Override
    public void run(GameState gameState, Status status, Point point, BlockingQueue<BufferElement> queue) {
        if (this.position == null) {
            this.position = point.getPosition();
        }
        GameBuffer.add(queue, this, point.getPosition());
    }

    /**
     * @return a clone of the food
     */
    @Override
    public Entity clone() {
        return new Food();
    }

    /**
     * @return the viewing priority of the food
     */
    @Override
    public int getPriority() {
        return model.Parameters.FOOD_PRIORITY;
    }

    /**
     * @return the position of the food
     */
    @Override
    public Position getPosition() {
        return position;
    }

    /**
     * Sets the position of the food
     *
     * @param position the new position of the food (precondition: position != null)
     */
    @Override
    public void setPosition(Position position) {
        this.position = position;
    }


}
