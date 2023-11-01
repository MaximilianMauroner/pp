package model.Entity;

import controller.HelperFunctions;
import controller.GameState;
import model.Colony;
import model.Point;
import model.Position;
import model.Status;


/**
 * Class for the hive entity
 * Hive objects have no other utility than to be on a position on the grid, and "store" food
 * <p>
 * Modularization Units:
 * - Module for all hive and food related variables and methods for updating them
 * - Objects for its position and colony it's a part of
 * - otherwise just the same as in Entity.java
 * <p>
 * Abstraction: Is a subtype of Entity and represents one part of a colonies home
 */
public class Hive implements Entity {

    private int health = 1000000;

    private final int id = HelperFunctions.generateRandomId();
    private final Colony colony; //(history-constraint: colony != null)

    private Position position;


    /**
     * Initializes new hive object
     * @param colony colony the hive is a part of
     * @param position position of the hive
     */
    public Hive(Colony colony, Position position) {
        this.colony = colony;
        this.colony.addHive(this);
        this.position = position;
    }

    /**
     * @return the colony the hive is a part of
     */
    public Colony getColony() {
        return colony;
    }


    /**
     * updates the health of the hive
     */
    public void updateVisited() {
        health += 1000;
    }

    /**
     * Increments the food count for the entire colony (e.g. when ant reaches hive)
     * and increment the health for the hive
     */
    public void addFood() {
        this.health = 1000000;
        this.colony.addFood();
    }

    @Override
    //here we check if the hive has gotten any food recently,
    // if it has, it should increase in size
    //we increase the size by creating a new hive object in a different
    // position which is part of the same colony
    public void run(GameState gameState, Status status, Point point) {

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
