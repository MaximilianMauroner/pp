package model.Entity;

import controller.BufferElement;
import controller.Game;
import controller.GameBuffer;
import controller.GameState;
import model.*;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Class for the trail entity
 * Trail objects have no other utility than to be on a position on the grid with a given strength and origin (e.g. ant that created it)
 * Trail objects can be read and written to but do not affect the game themselves
 * <p>
 * Modularization Units:
 * - Module for employing ants to lay trails, have trails affect each other and decay, as well as some utility methods
 * - Objects for its position and strengths associated with each colony (each colony at least one ant has laid a trail for)
 * <p>
 * Abstraction: Is a subtype of Entity and simulates the addition, interaction and decay of pheromones a real world ant would leave behind
 */
public class Trail implements Entity {

    private Position position;
    private ConcurrentHashMap<Integer, ColonyTrail> colonyStrength = new ConcurrentHashMap<>();

    /**
     * Initializes new trail object
     *
     * @param strength strength of the trail (precondition: strength >= 0)
     * @param origin   origin (ant) of the trail
     * @param colony   colony of the trail (precondition: colony != null)
     */
    public Trail(double strength, int origin, Colony colony) {
        this.changeStrength(strength, origin, colony);
    }

    /**
     * Initializes new trail object with a strength and an ant
     *
     * @param strength strength of the trail (precondition: strength >= 0)
     * @param ant      ant that created the trail (precondition: ant != null)
     */
    public Trail(double strength, Ant ant) {
        this.changeStrength(strength, ant.getId(), ant.getColony());
    }

    /**
     * Initializes new trail object with a map of colony ids and their respective trails
     *
     * @param value map of colony ids and their respective trails (precondition: value != null)
     */
    public Trail(ConcurrentHashMap<Integer, ColonyTrail> value) {
        this.colonyStrength = value;
    }

    /**
     * Removes the trail for a colony
     *
     * @param colony colony to be removed (precondition: colony != null)
     */
    public void removeColony(Colony colony) {
        this.colonyStrength.remove(colony.getId());
    }

    /**
     * @return true if there are no trails for any colony
     */
    public boolean isEmpty() {
        return this.colonyStrength.isEmpty();
    }


    /**
     * Calculates the average strength for all the trails
     *
     * @return average strength of all trails (if no trails exist, returns 0)
     */
    public double getStrength() {
        double total = this.colonyStrength.values().stream().reduce(0.0, (k, v) -> v.getStrength(), Double::sum);
        int size = this.colonyStrength.values().stream().reduce(0, (k, v) -> v.getCount(), Integer::sum);

        if (size == 0) {
            return 0;
        }
        return total / size;
    }

    /**
     * Returns the strength for one trail colony
     *
     * @param colony colony to get the average strength for (precondition: colony != null)
     * @return strength of the trail for the colony (if no trail exists for that colony, returns 0)
     */
    public double getColonyStrength(Colony colony) {
        if (this.colonyStrength.containsKey(colony.getId())) {
            double total = this.colonyStrength.get(colony.getId()).getStrength();
            int count = this.colonyStrength.get(colony.getId()).getCount();
            return total / count;
        }
        return 0;
    }

    /**
     * Changes the strength of the trail and updates the antId to the most recent ant that created it
     *
     * @param strength strength to be added (precondition: strength >= 0)
     * @param antId    ant that created the trail (using ant ids)
     * @param colony   colony of the ant (precondition: colony != null)
     */
    public void changeStrength(double strength, int antId, Colony colony) {
        if (this.colonyStrength.containsKey(colony.getId())) {
            this.colonyStrength.get(colony.getId()).changeStrength(strength, antId);
        } else {
            this.colonyStrength.put(colony.getId(), new ColonyTrail(new ConcurrentHashMap<>()));
            this.colonyStrength.get(colony.getId()).changeStrength(strength, antId);
        }
    }

    /**
     * Checks if the trail is new
     *
     * @param ant of the trail (precondition: ant != null)
     * @return true if the trail is new
     */
    public boolean isNewPath(Ant ant) {
        return !(this.colonyStrength.containsKey(ant.getColony().getId()) && this.colonyStrength.get(ant.getColony().getId()).isNewPath(ant.getId()));
    }

    /**
     * Combines two trails
     *
     * @param trail trail to be combined with (precondition: trail != null)
     */
    public void combineTrails(Trail trail) {
        // STYLE: this uses a lambda expression so kind of functional programming :)
        trail.colonyStrength.forEach((colony, trails) -> {
            if (this.colonyStrength.containsKey(colony)) {
                this.colonyStrength.get(colony).combineTrails(trails);
            } else {
                this.colonyStrength.put(colony, trails);
            }
        });
    }

    /**
     * Performs the trails decay
     */
    @Override
    public void run(GameState gameState, Status status, Point point, BlockingQueue<BufferElement> queue) {
        // STYLE: here as well
        colonyStrength.forEach((k, v) -> {
            v.decayTrails(status);
            if (v.isEmpty()) {
                point.removeEntity(this);
            }
        });
        if (this.position == null) {
            this.position = point.getPosition();
        }
        GameBuffer.add(queue, this, point.getPosition());
    }

    @Override
    public Entity clone() {
        ConcurrentHashMap<Integer, ColonyTrail> t = new ConcurrentHashMap<>(this.colonyStrength);
        return new Trail(t);
    }

    @Override
    public int getPriority() {
        return Parameters.TRAIL_PRIORITY;
    }

    @Override
    public Position getPosition() {
        return this.position;
    }

    @Override
    public void setPosition(Position position) {
        this.position = position;
    }

}


