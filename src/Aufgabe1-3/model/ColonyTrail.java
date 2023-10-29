package model;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Modularization Units:
 * - Objects for storing the strength of the trail and the origin of the trail
 * - Module for combining trails, decaying trails and changing the strength of the trail
 *
 * Abstraction: A addition to the basic trail class that allows for trails of different colonies to interact
 */
public class ColonyTrail {
    private final ConcurrentHashMap<Integer, Double> trailStrengths;

    public ColonyTrail(ConcurrentHashMap<Integer, Double> value) {
        this.trailStrengths = value;
    }

    /**
     * Combines two trails
     *
     * @param trail trail to be combined with
     */
    public void combineTrails(ColonyTrail trail) {
        // STYLE: this uses a lambda expression so kind of functional programming :)
        trail.trailStrengths.forEach((k, v) -> {
            if (this.trailStrengths.containsKey(k)) {
                this.trailStrengths.put(k, (this.trailStrengths.get(k) + v) / 2);
            } else {
                this.trailStrengths.put(k, v);
            }
        });
    }

    public void decayTrails(Status status) {
        // here as well
        trailStrengths.forEach((k, v) -> {
            v *= status.getTrailDecay();
            trailStrengths.put(k, v);
            if (v < status.getLowTrail() / 2) {
                trailStrengths.remove(k);
            }
        });
    }

    /**
     * Checks if the trail is new
     *
     * @param origin origin of the trail
     * @return true if the trail is new
     */
    public boolean isNewPath(int origin) {
//        TODO: use a constant to replace 0.1
        return !(this.trailStrengths.containsKey(origin) && this.trailStrengths.get(origin) > 0.1);
    }

    /**
     * Changes the strength of the trail and updates the origin to the most recent ant that created it
     *
     * @param strength strength to be added
     * @param origin   ant that created the trail (using ant objects hashcode)
     */
    public void changeStrength(double strength, int origin) {
        double value = this.trailStrengths.containsKey(origin) ? this.trailStrengths.get(origin) : 0;
        value += strength;
        if (value > 1) {
            value = 1;
        } else if (value < 0) {
            value = 0;
        }

        this.trailStrengths.put(origin, value);
    }

    public double getStrength() {
        double total = 0;
        if (!this.trailStrengths.isEmpty()) {
            for (double value : this.trailStrengths.values()) {
                total += value;
            }
        }
        if (this.trailStrengths.isEmpty()) {
            return 0;
        }
        return total / this.trailStrengths.size();
    }

    public int getCount() {
        return this.trailStrengths.size();
    }

    public boolean isEmpty() {
        return this.trailStrengths.isEmpty();
    }


}
