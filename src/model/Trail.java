package src.model;

import src.controller.GameState;

public class Trail implements Entity {
    private double strength;
    private int origin;

    public Trail(double strength, int origin) {
        this.strength = strength;
        this.origin = origin;
    }

    public Trail() {
        this.strength = 1;
    }

    public double getStrength() {
        return this.strength;
    }

    public void changeStrength(double strength) {
        this.strength += strength;
        if (strength > 1) {
            this.strength = 1;
        } else if (this.strength < 0) {
            this.strength = 0;
        }
    }

    @Override
    public void run(GameState gameState, Status status, Point point) {
        this.strength *= status.getTrailDecay();
    }

}
