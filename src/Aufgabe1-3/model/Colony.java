package model;

import controller.GameState;
import controller.HelperFunctions;
import datastore.DataManager;
import model.Entity.Ant;
import model.Entity.Hive;

import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class Colony {

    private final int id = HelperFunctions.generateRandomId();
    private int foodCount = 0;
    private ConcurrentHashMap<Integer, Hive> hives;
    private ConcurrentHashMap<Integer, Ant> ants;

    private GameState gameState;

    public Colony(GameState gameState) {
        this.hives = new ConcurrentHashMap<>();
        this.ants = new ConcurrentHashMap<>();
        this.gameState = gameState;
    }

    public int getId() {
        return id;
    }

    public void removeAnt(Ant ant) {
        ants.remove(ant.getId());
        this.gameState.getPoint(ant.getPosition()).removeEntity(ant);
    }

    public void removeHive(Hive hive) {
        System.out.println("Remove HIVE");
        hives.remove(hive.getId());
        if (gameState.hasPosition(hive.getPosition())) {
            gameState.getPoint(hive.getPosition()).removeEntity(hive);
        }

    }

    public ConcurrentHashMap<Integer, Hive> getHives() {
        return hives;
    }

    public ConcurrentHashMap<Integer, Ant> getAnts() {
        return ants;
    }

    public void removeColony(GameState gs) {
        for (Hive hive : this.hives.values()) {
            Point p = gs.getPoint(hive.getPosition());
            p.removeEntity(hive);
        }
        for (Ant ant : this.ants.values()) {
            Point p = gs.getPoint(ant.getPosition());
            p.removeEntity(ant);
        }
        this.hives = new ConcurrentHashMap<>();
        this.ants = new ConcurrentHashMap<>();
        System.out.println("Remove Colony");
    }

    public int getFoodCount() {
        return foodCount;
    }

    public void diffBetweenColonyAndGS(GameState gs) {
        AtomicInteger asize = new AtomicInteger(0);
        AtomicInteger hsize = new AtomicInteger(0);
        gameState.getPoints().forEach((position, point) -> {
            point.getEntities().forEach(entity -> {
                if (entity instanceof Ant) {
                    asize.getAndIncrement();
                } else if (entity instanceof Hive) {
                    hsize.getAndIncrement();
                }
            });
        });
        System.out.println("A-gs:" + asize.get() + " A-colony:" + this.ants.size() + "\tH-gs:" + hsize.get() + " H-colony:" + this.hives.size());

    }

    public void handleHiveUpdate(GameState gs, Hive hive) {
//        this.diffBetweenColonyAndGS(gs);
        Position hivePosition = hive.getPosition();
        if (this.foodCount > 100) {
            this.foodCount -= 100;
            Position newPosition = hivePosition.getRandomPositionNearPos();
            while (gs.hasPosition(newPosition) && !gs.getPoint(newPosition).hasHive()) {
                newPosition = hivePosition.getRandomPositionNearPos();
            }
            Hive newHive = new Hive(this, newPosition);
            this.addHive(newHive);
            gs.getPoint(newPosition).addEntity(newHive);
            this.hives.put(newHive.getId(), newHive);
        } else if (foodCount == 0) {
            double val = Math.random();
            if ((val < 0.00003 && this.hives.size() < 10) || this.hives.size() <= 1) {
                this.removeColony(gs);
            }
        }
    }

    public void addFood() {
        this.foodCount += 1000;
        DataManager dataManager = DataManager.getInstance();
        dataManager.incrementSimpleField("foodCount-" + this.getId());

        System.out.println("Food added to hive" + dataManager.getSimpleField("foodCount-" + this.getId()));
    }

    public void addHive(Hive hive) {
        System.out.println("Add HIVE");
        this.hives.put(hive.getId(), hive);
    }

    public void addAnt(Ant ant) {
        this.ants.put(ant.getId(), ant);
    }

    public Position getCentralHivePoint() {
        int x = 0;
        int y = 0;
        for (Hive hive : this.hives.values()) {
            x += hive.getPosition().getX();
            y += hive.getPosition().getY();
        }
        x /= this.hives.size();
        y /= this.hives.size();
        return new Position(x, y);
    }


}
