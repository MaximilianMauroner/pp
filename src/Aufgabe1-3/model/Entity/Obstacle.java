package model.Entity;

import controller.GameState;
import model.Point;
import model.Position;
import model.Status;

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

    Position position;

    @Override
    public void run(GameState gameState, Status status, Point point) {
    }

    @Override
    public Entity clone() {
        return new Obstacle();
    }

    @Override
    public int getPriority() {
        return model.Parameters.OBSTACLE_PRIORITY;
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
