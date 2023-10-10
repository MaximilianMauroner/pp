package src.model;

import src.controller.GameplayLoop;

public class Ant implements Entity {
    private AntState currentState = AntState.EXPLORE;

    public void setState(AntState state) {
        this.currentState = state;
    }

    public AntState getState() {
        return this.currentState;
    }

    @Override
    public void run() {

    }
}