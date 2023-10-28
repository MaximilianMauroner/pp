package model.Entity;

import controller.HelperFunctions;
import datastore.DataManager;
import controller.GameState;
import model.Colony;
import model.Point;
import model.Position;
import model.Status;

import java.util.concurrent.ConcurrentHashMap;


/**
 * Class for the hive entity
 * Hive objects have no other utility than to be on a position on the grid
 * Therefore it does not have any logic
 */
public class Hive implements Entity {

    private int health = 1000000;

    private final int id = HelperFunctions.generateRandomId();
    private final Colony colony;

    private Position position;


    public Hive(Colony colony, Position position) {
        this.colony = colony;
        this.colony.addHive(this);
        this.position = position;
    }

    public Colony getColony() {
        return colony;
    }

    public void updateVisited() {
        health += 1000;
    }

    /**
     * Increments the food count (e.g. when ant reaches hive)
     */
    public void addFood() {
        this.health = 1000000;
        this.colony.addFood();
    }

    @Override
    public void run(GameState gameState, Status status, Point point) {
        //here we check if the hive has gotten any food recently,
        // if it has, it should increase in size
        //we increase the size by creating a new hive object in a different
        // position which is part of the same colony
        colony.handleHiveUpdate(gameState, this);
        this.health--;
        if (health <= 0) {
            colony.removeHive(this);
        }
    }

    @Override
    public Hive clone() {
        return new Hive(this.colony, this.position);
    }

    @Override
    public int getPriority() {
        return model.Parameters.HIVE_PRIORITY;
    }

    @Override
    public Position getPosition() {
        return position;
    }

    @Override
    public void setPosition(Position position) {
        this.position = position;
    }

    public int getId() {
        return this.id;
    }


}
