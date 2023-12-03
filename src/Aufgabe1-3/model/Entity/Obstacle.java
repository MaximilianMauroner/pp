package model.Entity;

import controller.BufferElement;
import controller.GameBuffer;
import controller.GameState;
import model.Point;
import model.Position;
import model.Status;

import java.util.concurrent.BlockingQueue;

/**
 * Class for the obstacle entity
 * Obstacle objects have no other utility than to be on a position on the grid
 * Therefore it does not have any logic
 * <p>
 * Modularization Units:
 * - same as in Entity.java as no additional methods are added (does however contain the object of its current position)
 * <p>
 * Abstraction: A representation of a real world obstacle (e.g. a rock)
 */
public class Obstacle implements Entity {

    private Position position;

    /**
     * Runs the obstacle's logic
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
     * @return a clone of the obstacle
     */
    @Override
    public Entity clone() {
        return new Obstacle();
    }

    /**
     * @return the viewing priority of the obstacle
     */
    @Override
    public int getPriority() {
        return model.Parameters.OBSTACLE_PRIORITY;
    }

    /**
     * @return the position of the obstacle
     */
    @Override
    public Position getPosition() {
        return this.position;
    }

    /**
     * Sets the position of the obstacle
     *
     * @param position the position to be set (precondition: position != null)
     */
    @Override
    public void setPosition(Position position) {
        this.position = position;
    }
}
