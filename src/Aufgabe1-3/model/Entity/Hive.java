package model.Entity;

import controller.BufferElement;
import controller.GameBuffer;
import controller.HelperFunctions;
import controller.GameState;
import model.Colony;
import model.Point;
import model.Position;
import model.Status;

import java.util.concurrent.BlockingQueue;


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

    private final int id = HelperFunctions.generateRandomId();
    private final Colony colony;
    private int health = 1000000; // (invariant: health > 0)
    private Position position;


    /**
     * Initializes new hive object
     *
     * @param colony   colony the hive is a part of (precondition: colony != null)
     * @param position position of the hive (precondition: position != null)
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

    /**
     * Runs the hive's logic
     *
     * @param gameState the game state of the game (precondition: gameState != null)
     * @param status    the status of the game (precondition: status != null)
     * @param point     the point where the entity is located (precondition: point != null)
     * @param queue the buffer to which the entity is added (precondition: gameBuffer != null)
     */
    @Override
    //here we check if the hive has gotten any food recently,
    // if it has, it should increase in size
    //we increase the size by creating a new hive object in a different
    // position which is part of the same colony
    public void run(GameState gameState, Status status, Point point, BlockingQueue<BufferElement> queue) {

        colony.handleHiveUpdate(gameState, this);
        this.health--;
        if (health <= 0) {
            colony.removeHive(this);
        }
        if (this.position == null) {
            this.position = point.getPosition();
        }
        GameBuffer.add(queue, this, point.getPosition());
    }

    /**
     * @return a clone of the hive
     */
    @Override
    public Hive clone() {
        return new Hive(this.colony, this.position);
    }

    /**
     * @return the viewing priority of the hive
     */
    @Override
    public int getPriority() {
        return model.Parameters.HIVE_PRIORITY;
    }

    /**
     * @return the position of the hive
     */
    @Override
    public Position getPosition() {
        return position;
    }

    /**
     * Sets the position of the hive
     *
     * @param position the position to be set (precondition: position != null)
     */
    @Override
    public void setPosition(Position position) {
        this.position = position;
    }


    /**
     * @return the id of the hive
     */
    public int getId() {
        return this.id;
    }


}
