package model;

import controller.GameState;

/**
 * Class for the obstacle entity
 * Obstacle objects have no other utility than to be on a position on the grid
 * Therefore it does not have any logic
 */
public class Obstacle implements Entity {
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
}
