package src.model;

import src.controller.GameState;

public class Trail implements Entity {
    private double strength;

    public Trail(double strength) {

        this.strength = strength;
    }

    public Trail() {
        this.strength = 1;
    }

    public double getStrength() {
        return this.strength;
    }

    public void addStrength(double strength) {
        this.strength += strength;
    }

    @Override
    public void run(GameState gameState, Status status, Point point) {
        this.strength *= status.getTrailDecay();
    }
}
