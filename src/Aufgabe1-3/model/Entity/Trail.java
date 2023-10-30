package model.Entity;

import controller.GameState;
import model.*;

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

    public Trail(double strength, int origin, Colony colony) {
        this.changeStrength(strength, origin, colony);
    }

    public Trail(double strength, Ant ant) {
        this.changeStrength(strength, ant.getId(), ant.getColony());
    }

    public Trail(ConcurrentHashMap<Integer, ColonyTrail> value) {
        this.colonyStrength = value;
    }

    public void removeColony(Colony colony) {
        this.colonyStrength.remove(colony.getId());
    }

    public boolean isEmpty() {
        return this.colonyStrength.isEmpty();
    }


    /**
     * Returns the strength for all the trails
     */
    public double getStrength() {
        double total = 0;
        int size = 0;
        for (ColonyTrail value : this.colonyStrength.values()) {
            total += value.getStrength();
            size += value.getCount();
        }
        if (size == 0) {
            return 0;
        }
        return total / size;
    }

    /**
     * Returns the strength for one trail colony
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
     * @param strength strength to be added
     * @param antId    ant that created the trail (using ant ids)
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
     * @param ant of the trail
     * @return true if the trail is new
     */
    public boolean isNewPath(Ant ant) {
        return !(this.colonyStrength.containsKey(ant.getColony().getId()) && this.colonyStrength.get(ant.getColony().getId()).isNewPath(ant.getId()));
    }

    /**
     * Combines two trails
     *
     * @param trail trail to be combined with
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

    @Override
    public void run(GameState gameState, Status status, Point point) {
        // here as well
        colonyStrength.forEach((k, v) -> {
            v.decayTrails(status);
            if (v.isEmpty()) {
                point.removeEntity(this);
            }
        });
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


