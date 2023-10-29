package model.Entity;

import controller.GameState;
import model.Point;
import model.Position;
import model.Status;

/**
 * Class for the food entity
 * Food is an entity that can be placed on the grid
 * It is used to feed the ants
 *
 * Modularization Units:
 * - same as in Entity.java as no additional methods are added (does however contain the object of its current position)
 *
 * Abstraction: A representation of a real world piece of food
 */
public class Food implements Entity {

    private Position position;

    @Override
    public void run(GameState gameState, Status status, Point point) {

    }

    @Override
    public Entity clone() {
        return new Food();
    }

    @Override
    public int getPriority() {
        return model.Parameters.FOOD_PRIORITY;
    }

    @Override
    public Position getPosition() {
        return position;
    }

    @Override
    public void setPosition(Position position) {
        this.position = position;
    }


}
