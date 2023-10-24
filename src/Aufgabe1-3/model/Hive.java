package model;

import datastore.DataManager;
import controller.GameState;

import java.util.concurrent.ConcurrentHashMap;


/**
 * Class for the hive entity
 * Hive objects have no other utility than to be on a position on the grid
 * Therefore it does not have any logic
 */
public class Hive implements Entity {

    private int foodCount = 0;
    private int colony = this.hashCode();

    public Hive() {
    }

    public Hive(int colony) {
        this.colony = colony;
    }

    public int getColony() {
        return colony;
    }

    /**
     * Increments the food count (e.g. when ant reaches hive)
     */
    public void addFood() {
        DataManager dataManager = DataManager.getInstance();
        dataManager.incrementSimpleField("foodCount");
        foodCount++;

        System.out.println("Food added to hive" + dataManager.getSimpleField("foodCount"));


    }

    @Override
    public void run(GameState gameState, Status status, Point point) {
        //here we check if the hive has gotten any food recently,
        // if it has, it should increase in size
        //we increase the size by creating a new hive object in a different
        // position which is part of the same colony
        if(foodCount > 0) {
            foodCount--;
            boolean found = false;
            Position p = point.getPosition();
            ConcurrentHashMap<Position, Point> chm = gameState.getPoints();
            while(!found){
                int x = (int)(Math.random() * 5) -3;
                int y = (int)(Math.random() * 5) -3;
                Position newPos = new Position(p.getX() + x, p.getY() + y);
                if(!chm.contains(newPos)){
                    found = true;
                    chm.put(newPos, new Point(newPos, this.clone()));
                }
            }
        }
    }

    @Override
    public Hive clone() {
        return new Hive();
    }

    @Override
    public int getPriority() {
        return model.Parameters.HIVE_PRIORITY;
    }

}
