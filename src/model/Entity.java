package src.model;

import src.controller.GameState;

import java.util.HashMap;

public interface Entity {
    public void run(GameState gameState, Status status);
}
