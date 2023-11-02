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
    private final Path path; //(history-constraint: path != null)

    private Position position;

    /**
     * Initializes new optimal path point
     *
     * @param path the path it is associated with
     */
    public OptimalPathPoint(Path path) {
        this.path = path;
    }

    @Override
    public void run(GameState gameState, Status status, Point point, BlockingQueue<BufferElement> queue) {
        this.path.addPointMetric(point.getTrail());
        if (this.position == null) {
            this.position = point.getPosition();
        }
        GameBuffer.add(queue, this, point.getPosition());
    }

    @Override
    public Entity clone() {
        return new OptimalPathPoint(path);
    }

    @Override
    public int getPriority() {
        return model.Parameters.OPTIMAL_PATH_PRIORITY;
    }

    @Override
    public Position getPosition() {
        return this.position;
    }

    @Override
    public void setPosition(Position position) {
        this.position = position;
    }
}
