package model.Entity;

import controller.GameState;
import model.Point;
import model.Position;
import model.Status;

/**
 * Interface for the entities of the game
 * Entities are the objects that are placed on the grid
 * They can be ants, food, trails, etc.
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

    Entity clone();

    int getPriority();

    @Override
    default int compareTo(Entity o) {
        if (o.getPriority() == this.getPriority())
            return 0;
        return o.getPriority() > this.getPriority() ? 1 : -1;
    }

    Position getPosition();

    void setPosition(Position position);
}
