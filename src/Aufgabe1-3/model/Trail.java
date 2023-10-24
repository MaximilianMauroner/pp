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
    private ConcurrentHashMap<Integer, ColonyTrail> colonyStrength = new ConcurrentHashMap<Integer, ColonyTrail>();

    public Trail(double strength, int origin, int colony) {
        this.changeStrength(strength, origin, colony);
    }

    public Trail(double strength, Ant ant) {
        this.changeStrength(strength, ant.getOrigin(), ant.getColony());
    }

    public Trail(ConcurrentHashMap<Integer, ColonyTrail> value) {
        this.colonyStrength = value;
    }


    /**
     * Returns the strength of the trail
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

    public double getColonyStrength(int colony) {
        if (this.colonyStrength.containsKey(colony)) {
            double total = this.colonyStrength.get(colony).getStrength();
            int count = this.colonyStrength.get(colony).getCount();
            return total / count;
        }
        return 0;
    }

    /**
     * Changes the strength of the trail and updates the origin to the most recent ant that created it
     *
     * @param strength strength to be added
     * @param origin   ant that created the trail (using ant objects hashcode)
     */
    public void changeStrength(double strength, int origin, int colony) {
        if (this.colonyStrength.contains(colony)) {
            this.colonyStrength.get(colony).changeStrength(strength, origin);
        } else {
            this.colonyStrength.put(colony, new ColonyTrail(new ConcurrentHashMap<Integer, Double>()));
            this.colonyStrength.get(colony).changeStrength(strength, origin);
        }
    }

    /**
     * Checks if the trail is new
     *
     * @param ant of the trail
     * @return true if the trail is new
     */
    public boolean isNewPath(Ant ant) {
        return !(this.colonyStrength.containsKey(ant.getColony()) && this.colonyStrength.get(ant.getColony()).isNewPath(ant.getOrigin()));
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
        ConcurrentHashMap<Integer, ColonyTrail> t = new ConcurrentHashMap<Integer, ColonyTrail>(this.colonyStrength);
        return new Trail(t);
    }

    @Override
    public int getPriority() {
        return Parameters.TRAIL_PRIORITY;
    }

}


class ColonyTrail {
    private ConcurrentHashMap<Integer, Double> trailStrengths = new ConcurrentHashMap<Integer, Double>();

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