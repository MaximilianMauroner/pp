package model;

import controller.GameState;

public class OptimalPath implements Entity {
    @Override
    public void run(GameState gameState, Status status, Point point) {

    }

    @Override
    public Entity clone() {
        return new OptimalPath();
    }

    @Override
    public int getPriority() {
        return model.Parameters.OPTIMAL_PATH_PRIORITY;
    }
}
