package model;

import datastore.DataManager;
import controller.GameState;


/**
 * Class for the hive entity
 * Hive objects have no other utility than to be on a position on the grid
 * Therefore it does not have any logic
 */
public class Hive implements Entity {
    private int id;


    public Hive(int id) {
        this.id = id;
    }

    /**
     * Increments the food count (e.g. when ant reaches hive)
     */
    public void addFood() {
        DataManager dataManager = DataManager.getInstance();
        dataManager.incrementSimpleField("foodCount");

        System.out.println("Food added to hive" + dataManager.getSimpleField("foodCount"));
    }

    public int getId() {
        return id;
    }

    @Override
    public void run(GameState gameState, Status status, Point point) {
    }

    @Override
    public Entity clone() {
        return new Hive(id);
    }

    @Override
    public int getPriority() {
        return model.Parameters.HIVE_PRIORITY;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Hive) {
            return ((Hive) obj).getId() == this.id;
        }
        return false;
    }
}
