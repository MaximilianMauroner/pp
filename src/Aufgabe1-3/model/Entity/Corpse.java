package model.Entity;

import controller.BufferElement;
import controller.GameBuffer;
import controller.GameState;
import model.Parameters;
import model.Point;
import model.Position;
import model.Status;

import java.util.Random;
import java.util.concurrent.BlockingQueue;

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
    private float strength = 1; //(invariant: strength >= 0)

    private Position position;

    /**
     * Runs the corpse's logic
     *
     * @param gameState the game state of the game (precondition: gameState != null)
     * @param status    the status of the game (precondition: status != null)
     * @param point     the point where the entity is located (precondition: point != null)
     * @param queue the buffer to which the entity is added (precondition: gameBuffer != null)
     */
    @Override
    public void run(GameState gameState, Status status, Point point, BlockingQueue<BufferElement> queue) {
        this.strength *= (float) status.getTrailDecay();
        if (this.strength < status.getLowTrail() / 2) {
            point.removeEntity(this);
        }
        if (this.position == null) {
            this.position = point.getPosition();
        }
        GameBuffer.add(queue, this, point.getPosition());
    }

    /**
     * @return a clone of the corpse
     */
    @Override
    public Entity clone() {
        return new Corpse();
    }

    /**
     * @return the viewing priority of the corpse
     */
    @Override
    public int getPriority() {
        return Parameters.CORPSE_PRIORITY;
    }

    /**
     * @return the position of the corpse
     */
    @Override
    public Position getPosition() {
        return this.position;
    }

    /**
     * Sets the position of the corpse
     *
     * @param position the position to be set (precondition: position != null)
     */
    @Override
    public void setPosition(Position position) {
        this.position = position;
    }

    /**
     * @return the seed of the corpse
     */
    public int getSeed() {
        return seed;
    }

    /**
     * @return the strength of the corpse
     */
    public float getStrength() {
        return strength;
    }
}
