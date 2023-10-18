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
    private ConcurrentHashMap<Integer, Double> value = new ConcurrentHashMap<Integer, Double>();

    public Trail(double strength, int origin) {
        this.changeStrength(strength, origin);
    }
    public Trail(ConcurrentHashMap<Integer,Double> value){
        this.value = value;
    }


    /**
     * Returns the strength of the trail
     */
    public double getStrength() {
        double total = 0;
        if (!this.value.isEmpty()) {
            for (double value : this.value.values()) {
                total += value;
            }
        }
        return total / this.value.size();
    }

    /**
     * Changes the strength of the trail and updates the origin to the most recent ant that created it
     * @param strength strength to be added
     * @param origin ant that created the trail (using ant objects hashcode)
     */
    public void changeStrength(double strength, int origin) {
        double value = this.value.containsKey(origin) ? this.value.get(origin):0;
        value += strength;
        if (value > 1) {
            value = 1;
        } else if (value < 0) {
            value = 0;
        }
        this.value.put(origin, value);
    }

    /**
     * Checks if the trail is new
     * @param origin origin of the trail
     * @return true if the trail is new
     */
    public boolean isNewPath(int origin) {
        return !(this.value.containsKey(origin) && this.value.get(origin) > 0.1);
    }

    /**
     * Combines two trails
     * @param trail trail to be combined with
     */
    public void combineTrails(Trail trail) {
        trail.value.forEach((k,v) -> {
            if (this.value.containsKey(k)) {
                this.value.put(k, (this.value.get(k) + v) / 2);
            } else {
                this.value.put(k, v);
            }
        });
    }

    @Override
    public void run(GameState gameState, Status status, Point point) {
        value.forEach((k,v) -> {
            v *= status.getTrailDecay();
            value.put(k,v);
            if (v < status.getLowTrail() / 2) {
                value.remove(k);
            }
            if(value.isEmpty()){
                point.getEntities().remove(this);
            }
        });


    }

    @Override
    public Entity clone() {
        ConcurrentHashMap<Integer, Double> t = new ConcurrentHashMap<Integer, Double>(this.value);
        return new Trail(t);
    }

    @Override
    public int getPriority() {
        return Parameters.TRAIL_PRIORITY;
    }

}
