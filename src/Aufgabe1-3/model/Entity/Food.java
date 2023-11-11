package model.Entity;

import controller.BufferElement;
import controller.GameBuffer;
import controller.GameState;
import model.Point;
import model.Position;
import model.Status;

import java.util.concurrent.BlockingQueue;

/**
 * Class for the food entity
 * Food is an entity that can be placed on the grid
 * It is used to feed the ants
 * <p>
 * Modularization Units:
 * - same as in Entity.java as no additional methods are added (does however contain the object of its current position)
 * <p>
 * Abstraction: A representation of a real world piece of food
 */
public class Food implements Entity {

    private Position position;

    @Override
    public void run(GameState gameState, Status status, Point point, BlockingQueue<BufferElement> queue) {
        if (this.position == null) {
            this.position = point.getPosition();
        }
        GameBuffer.add(queue, this, point.getPosition());
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
