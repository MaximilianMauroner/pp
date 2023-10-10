package src.model;

import src.controller.GameState;

public class Trail implements Entity {
    private double strength;

    public Trail(double strength) {

        this.strength = strength;
    }

    public double getStrength() {
        return this.strength;
    }

    @Override
    public void run(GameState gameState, Status status) {
        this.strength *= status.getTrailDecay();
    }
}
