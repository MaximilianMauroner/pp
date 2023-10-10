package src.model;

import src.controller.GameState;
import src.controller.GameplayLoop;

import java.util.HashMap;

public class Ant implements Entity {
    private AntState currentState = AntState.EXPLORE;

    public void setState(AntState state) {
        this.currentState = state;
    }

    public AntState getState() {
        return this.currentState;
    }

    @Override
    public void run(GameState gameState, HashMap<String, Double>[] parameters) {

    }
}
