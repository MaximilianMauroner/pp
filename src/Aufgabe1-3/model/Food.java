package model;

import controller.GameState;

public class Food implements Entity {

    @Override
    public void run(GameState gameState, Status status, Point point) {

    }

    @Override
    public Entity clone() {
        return new Food();
    }


}