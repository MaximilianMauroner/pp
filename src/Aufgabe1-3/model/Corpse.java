package model;

import controller.GameState;

import java.util.Random;

public class Corpse implements Entity {
    private final int seed = new Random().nextInt();
    private float strength = 1;

    @Override
    public void run(GameState gameState, Status status, Point point) {
        this.strength*= (float) status.getTrailDecay();
        if(this.strength < status.getLowTrail() / 2){
            point.removeEntity(this);
        }
    }


    @Override
    public Entity clone() {
        return new Corpse();
    }

    public int getPriority() {
        return Parameters.CORPSE_PRIORITY;
    }

    public int getSeed() {
        return seed;
    }

    public float getStrength() {
        return strength;
    }
}
