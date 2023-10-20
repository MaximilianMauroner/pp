package model;

import controller.GameState;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Class for the trail entity
 * Trail objects have no other utility than to be on a position on the grid with a given strength and origin (e.g. ant that created it)
 * Trail objects can be read and written to but do not affect the game themselves
 * The only logic they have is to decay over time
 */
public class Trail implements Entity {
    private ConcurrentHashMap<Integer, Double> trailStrengths = new ConcurrentHashMap<Integer, Double>();

    public Trail(double strength, int origin) {
        this.changeStrength(strength, origin);
    }
    public Trail(ConcurrentHashMap<Integer,Double> value){
        this.trailStrengths = value;
    }


    /**
     * Returns the strength of the trail
     */
    public double getStrength() {
        double total = 0;
        if (!this.trailStrengths.isEmpty()) {
            for (double value : this.trailStrengths.values()) {
                total += value;
            }
        }
        return total / this.trailStrengths.size();
    }

    /**
     * Changes the strength of the trail and updates the origin to the most recent ant that created it
     * @param strength strength to be added
     * @param origin ant that created the trail (using ant objects hashcode)
     */
    public void changeStrength(double strength, int origin) {
        double value = this.trailStrengths.containsKey(origin) ? this.trailStrengths.get(origin):0;
        value += strength;
        if (value > 1) {
            value = 1;
        } else if (value < 0) {
            value = 0;
        }

        this.trailStrengths.put(origin, value);
    }

    /**
     * Checks if the trail is new
     * @param origin origin of the trail
     * @return true if the trail is new
     */
    public boolean isNewPath(int origin) {
        return !(this.trailStrengths.containsKey(origin) && this.trailStrengths.get(origin) > 0.1);
    }

    /**
     * Combines two trails
     * @param trail trail to be combined with
     */
    public void combineTrails(Trail trail) {
        // STYLE: this uses a lambda expression so kind of functional programming :)
        trail.trailStrengths.forEach((k, v) -> {
            if (this.trailStrengths.containsKey(k)) {
                this.trailStrengths.put(k, (this.trailStrengths.get(k) + v) / 2);
            } else {
                this.trailStrengths.put(k, v);
            }
        });
    }

    @Override
    public void run(GameState gameState, Status status, Point point) {
        // here as well
        trailStrengths.forEach((k, v) -> {
            v *= status.getTrailDecay();
            trailStrengths.put(k,v);
            if (v < status.getLowTrail() / 2) {
                trailStrengths.remove(k);
            }
            if(trailStrengths.isEmpty()){
                point.removeEntity(this);
            }
        });
    }

    @Override
    public Entity clone() {
        ConcurrentHashMap<Integer, Double> t = new ConcurrentHashMap<Integer, Double>(this.trailStrengths);
        return new Trail(t);
    }

    @Override
    public int getPriority() {
        return Parameters.TRAIL_PRIORITY;
    }

}
