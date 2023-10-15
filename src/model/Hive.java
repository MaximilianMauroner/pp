package src.model;

import src.controller.GameState;

public class Hive implements Entity {

    public Hive() {
    }

    @Override
    public void run(GameState gameState, Status status, Point point) {
    }



    @Override
    public Entity clone() {
        return new Hive();
    }

}
