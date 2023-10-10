package src.model;

import src.controller.GameState;

import java.util.HashMap;

public class Trail implements Entity {
    private int strength;

    public Trail(int strength) {

        this.strength = strength;
    }

    public int getStrength() {
        return this.strength;
    }

    @Override
    public void run(GameState gameState, HashMap<String, Double>[] parameters) {
        this.strength *= parameters[0].get("traildecay");
    }
}
