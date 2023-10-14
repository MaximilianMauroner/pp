package src.model;

import src.controller.GameState;

public class Obstacle implements Entity {
    @Override
    public void run(GameState gameState, Status status, Point point) {

    }
    @Override
    public Entity clone() {
        return new Obstacle();
    }
}
