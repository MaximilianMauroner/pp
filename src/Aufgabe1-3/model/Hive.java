package model;

import controller.GameState;


/**
 * Class for the hive entity
 * Hive objects have no other utility than to be on a position on the grid
 * Therefore it does not have any logic
 */
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
