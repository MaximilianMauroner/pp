package model.Entity;

import controller.BufferElement;
import controller.GameBuffer;
import controller.GameState;
import model.Path;
import model.Point;
import model.Position;
import model.Status;

import java.util.concurrent.BlockingQueue;

/**
 * Modularization Units:
 * - same as in Entity.java except that it stores the Object of the path it is associated with (as well its position)
 * <p>
 * Abstraction: Represents the theoretical point of a path on the grid, which for information purposes is displayed but does not interact with the simulation
 */

public class OptimalPathPoint implements Entity {
    private final Path path;

    private Position position;

    /**
     * Initializes new optimal path point
     *
     * @param path the path it is associated with (precondition: path != null)
     */
    public OptimalPathPoint(Path path) {
        this.path = path;
    }

    /**
     * Runs the pathpoint's logic
     *
     * @param gameState the game state of the game (precondition: gameState != null)
     * @param status    the status of the game (precondition: status != null)
     * @param point     the point where the entity is located (precondition: point != null)
     * @param queue the buffer to which the entity is added (precondition: gameBuffer != null)
     */
    @Override
    public void run(GameState gameState, Status status, Point point, BlockingQueue<BufferElement> queue) {
        this.path.addPointMetric(point.getTrail());
        if (this.position == null) {
            this.position = point.getPosition();
        }
        GameBuffer.add(queue, this, point.getPosition());
    }

    /**
     * @return a clone of the pathpoint
     */
    @Override
    public Entity clone() {
        return new OptimalPathPoint(path);
    }

    /**
     * @return the viewing priority of the pathpoint
     */
    @Override
    public int getPriority() {
        return model.Parameters.OPTIMAL_PATH_PRIORITY;
    }

    /**
     * @return the position of the pathpoint
     */
    @Override
    public Position getPosition() {
        return this.position;
    }

    /**
     * Sets the position of the pathpoint
     *
     * @param position the position to be set (precondition: position != null)
     */
    @Override
    public void setPosition(Position position) {
        this.position = position;
    }
}
