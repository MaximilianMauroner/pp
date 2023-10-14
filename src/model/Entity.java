package src.model;

import src.controller.GameState;

public interface Entity {
    void run(GameState gameState, Status status, Point point);


    Entity clone();

}
