package model.Entity;

import controller.GameState;
import model.Parameters;
import model.Point;
import model.Position;
import model.Status;

import java.util.Random;

/**
 * Class for the corpse entity
 * Corpse objects have no other utility than to be on a position on the grid
 * <p>
 * Modularization Units:
 * - same as in Entity.java but the also modularize a corpses strength/decay and seed
 * <p>
 * Abstraction: A subtype of Entity and a representation of a real world corpse of an ant. Does only serve as a visual representation
 */
public class Corpse implements Entity {
    private final int seed = new Random().nextInt();
    private float strength = 1;

    private Position position;

    @Override
    public void run(GameState gameState, Status status, Point point) {
        this.strength *= (float) status.getTrailDecay();
        if (this.strength < status.getLowTrail() / 2) {
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

    @Override
    public Position getPosition() {
        return this.position;
    }

    @Override
    public void setPosition(Position position) {
        this.position = position;
    }

    public int getSeed() {
        return seed;
    }

    public float getStrength() {
        return strength;
    }
}
