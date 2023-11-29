package model;

import controller.GameState;
import controller.HelperFunctions;
import datastore.DataManager;
import model.Entity.Ant;
import model.Entity.Hive;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;


// BAD (procedural): Also here some of the procedural snippets are a bit difficult to follow.

/**
 * Class for the colony
 * <p>
 * Modularization Units:
 * - Objects for storing the hives and ants that belong to the colony as well as the game-state (for method access)
 * - Module for all the logic ants and hive objects need to interact with each other (ants need to know what colony they belong to and where its located;
 * hives propagate the collected food)
 * <p>
 * Abstraction: A simulation of the abstract concept of a colony, which is a group of ants and hives that can grow and shrink depending on the amount of food it has.
 */

public class Colony {

    private final int id = HelperFunctions.generateRandomId();
    private int foodCount = 0;
    private ConcurrentHashMap<Integer, Hive> hives;
    private ConcurrentHashMap<Integer, Ant> ants;

    private final GameState gameState; //(invariant: gameState != null)

    /**
     * Initializes new colony object
     *
     * @param gameState game-state the colony is a part of (precondition: gameState != null)
     */
    public Colony(GameState gameState) {
        this.hives = new ConcurrentHashMap<>();
        this.ants = new ConcurrentHashMap<>();
        this.gameState = gameState;
    }

    /**
     * @return the unique id of the colony
     */
    public int getId() {
        return id;
    }

    /**
     * Removes an ant from the colony
     * @param ant ant to be removed (precondition: ant != null)
     */
    public void removeAnt(Ant ant) {
        ants.remove(ant.getId());
        this.gameState.getPoint(ant.getPosition()).removeEntity(ant);
    }

    /**
     * Removes a hive from the colony
     * @param hive hive to be removed (precondition: hive != null)
     */
    public void removeHive(Hive hive) {
        System.out.println("Remove HIVE");
        hives.remove(hive.getId());
        if (gameState.hasPosition(hive.getPosition())) {
            gameState.getPoint(hive.getPosition()).removeEntity(hive);
        }

    }

    /**
     * @return the hives of the colony
     */
    public ConcurrentHashMap<Integer, Hive> getHives() {
        return hives;
    }

    /**
     * @return the ants of the colony
     */
    public ConcurrentHashMap<Integer, Ant> getAnts() {
        return ants;
    }

    /**
     * Removes the colony from the game-state
     * @param gs game-state the colony is a part of (precondition: gs != null)
     */
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

    /**
     * @return the amount of food the colony has
     */
    public int getFoodCount() {
        return foodCount;
    }

    /**
     * How many ants and hives the game actually has compared to how much this colony thinks it has
     * @param gs game-state the colony is a part of (precondition: gs != null)
     */
    public void diffBetweenColonyAndGS(GameState gs) {
        AtomicInteger asize = new AtomicInteger(0);
        AtomicInteger hsize = new AtomicInteger(0);
        gameState.getPoints().forEach((position, point) -> point.getEntities().forEach(entity -> {
            if (entity instanceof Ant) {
                asize.getAndIncrement();
            } else if (entity instanceof Hive) {
                hsize.getAndIncrement();
            }
        }));
        System.out.println("A-gs:" + asize.get() + " A-colony:" + this.ants.size() + "\tH-gs:" + hsize.get() + " H-colony:" + this.hives.size());

    }

    /**
     * Updates the colony and ajusts size depending on the amount of food it has
     * @param gs game-state the colony is a part of (precondition: gs != null)
     * @param hive hive that's calling the update (precondition: hive != null)
     */
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

    /**
     * Increments the food count for the entire colony (e.g. when ant reaches hive)
     * and increment the health for the hive
     */
    public void addFood() {
        this.foodCount += 1000;
        DataManager dataManager = DataManager.getInstance();
        dataManager.incrementSimpleField("foodCount-" + this.getId());

        System.out.println("Food added to hive" + dataManager.getSimpleField("foodCount-" + this.getId()));
    }

    /**
     * Adds a hive to the colony
     * @param hive hive to be added (precondition: hive != null)
     */
    public void addHive(Hive hive) {
        System.out.println("Add HIVE");
        this.hives.put(hive.getId(), hive);
    }

    /**
     * Adds an ant to the colony
     * @param ant ant to be added (precondition: ant != null)
     */
    public void addAnt(Ant ant) {
        this.ants.put(ant.getId(), ant);
    }

    /**
     * @return the central point of the colony
     */
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
