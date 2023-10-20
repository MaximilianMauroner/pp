package model;

import datastore.DataManager;
import controller.GameState;


/**
 * Class for the hive entity
 * Hive objects have no other utility than to be on a position on the grid
 * Therefore it does not have any logic
 */
public class Hive implements Entity {

    public Hive() {
    }

    /**
     * Increments the food count (e.g. when ant reaches hive)
     */
    public void addFood() {
        DataManager dataManager = DataManager.getInstance();
        dataManager.incrementSimpleField("foodCount");

        System.out.println("Food added to hive" + dataManager.getSimpleField("foodCount"));
    }

    @Override
    public void run(GameState gameState, Status status, Point point) {
    }

    @Override
    public Entity clone() {
        return new Hive();
    }

    @Override
    public int getPriority() {
        return model.Parameters.HIVE_PRIORITY;
    }

}
