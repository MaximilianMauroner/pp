package model.Entity;

import controller.GameState;
import model.Point;
import model.Position;
import model.Status;

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
