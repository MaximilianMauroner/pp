package src.model;

import src.controller.GameState;

import java.util.concurrent.ConcurrentHashMap;

public class Trail implements Entity {
    private ConcurrentHashMap<Integer, Double> value = new ConcurrentHashMap<Integer, Double>();

    public Trail(double strength, int origin) {
        this.changeStrength(strength, origin);
    }
    public Trail(ConcurrentHashMap<Integer,Double> value){
        this.value = value;
    }


    public double getStrength() {
        double total = 0;
        if (!this.value.isEmpty()) {
            for (double value : this.value.values()) {
                total += value;
            }
        }
        return total / this.value.size();
    }

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

    public boolean isNewPath(int origin) {
        return !(this.value.containsKey(origin) && this.value.get(origin) > 0.1);
    }

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

}
