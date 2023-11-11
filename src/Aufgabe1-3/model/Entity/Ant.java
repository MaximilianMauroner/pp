package model.Entity;

import controller.BufferElement;
import controller.GameBuffer;
import controller.GameState;
import controller.HelperFunctions;
import model.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

// BAD (procedural): While the Ant class is part of a object oriented structure, within we use a lot of procedural programming.
// However this is not a good example for a procedural style. We have convoluted aliases (like endPosition, oldPoint, newPoint)
// and the control flow takes some time to understand.

/**
 * Class for the ant entity and its logic
 * contains reference objects to the game and status (for parameter access)
 * <p>
 * Modularization Units:
 * - Objects such as GameState, Status, Colony for reference and access to their methods
 * - Module for all internal variables (e.g. current state, direction, etc.) and methods for interaction as well as implementing ant logic/behaviour
 * <p>
 * Abstract: Is a subtype of Entity and represents a single ant.
 * It simulates the decisions an ant would make one step at a time, based on its surroundings and current state. It's also a core part of the simulation.
 */
public class Ant implements Entity {
    private final int id = HelperFunctions.generateRandomId();
    private final Colony colony;
    private final Position hivePos;
    private AntState currentState;
    private int emptySteps = 0;
    private AntDirection direction = AntDirection.values()[(int) (Math.random() * AntDirection.values().length)];
    private GameState gameState;
    private Status status;
    private int moveSteps;
    private int waitSteps;
    private Position position;
    private int searchRadius = Parameters.INITIAL_ANT_SEARCH_RADIUS;
    private int foodCount = 0;
    private int oldFoodCount = 0;

    /**
     * Initializes new ant object
     *
     * @param currentState the current state of the ant
     * @param gameState    the game state the ant is a part of
     * @param status       the status of the current simulation
     * @param colony       the ants colony
     * @param position     the position of the ant
     */
    public Ant(AntState currentState, GameState gameState, Status status, Colony colony, Position position) {
        this.currentState = currentState;
        this.gameState = gameState;
        this.status = status;
        this.moveSteps = status.getAntMoveSteps();
        this.waitSteps = status.getAntWaitSteps();
        this.colony = colony;
        this.hivePos = colony.getCentralHivePoint();
        this.colony.addAnt(this);
        this.position = position;
    }

    public Position getPosition() {
        return position;
    }

    @Override
    public void setPosition(Position position) {
        this.position = position;
    }

    /**
     * @return the current state of the ant
     */
    public AntState getState() {
        return this.currentState;
    }

    /**
     * @return the colony the ant is a part of
     */
    public Colony getColony() {
        return colony;
    }

    /**
     * @return the id of the ant
     */
    public int getId() {
        return this.id;
    }

    /**
     * Starts the State Logic of the Ant. The Ant will move to the next Point.
     */
    @Override
    public void run(GameState gameState, Status status, Point oldPoint, BlockingQueue<BufferElement> queue) {
        this.gameState = gameState;
        this.status = status;

        // filter out positions with obstacles
        List<Position> nearestPositions = oldPoint.getPosition().getPossibleNextPosition(this.direction);
        for (Iterator<Position> iter = nearestPositions.iterator(); iter.hasNext(); ) {
            Position pos = iter.next();
            Point point = gameState.getPoint(pos);
            if (point != null && point.hasObstacle()) {
                iter.remove();
            }
        }

        // ant runs into a dead end and has to turn around
        if (nearestPositions.isEmpty()) {
            this.direction = AntDirection.values()[(int) (Math.random() * AntDirection.values().length)];
            return; // ant doesn't move for one step
        }

        Position endPosition = nearestPositions.get((int) (Math.random() * nearestPositions.size()));

        if (this.moveSteps > 0) {
            this.moveSteps--;

            switch (currentState) {
                case EXPLORE:
                    explore(oldPoint, nearestPositions, endPosition);
                    break;
                case FOODSEARCH:
                    foodSearch(oldPoint, nearestPositions, endPosition);
                    break;
                case FOODRETRIEVE:
                    foodRetrieve(oldPoint, nearestPositions, endPosition);
                    break;
                case RETURN:
                    antReturn(oldPoint, nearestPositions, endPosition);
                    break;
            }
        } else if (this.waitSteps > 0) {
            this.waitSteps--;
        } else {
            this.moveSteps = status.getAntMoveSteps();
            this.waitSteps = status.getAntWaitSteps();

            boolean timeLimit = gameState.getStatus().getSimulationTime() % 24 > 9;
            if (this.currentState != AntState.RETURN && this.currentState != AntState.FOODRETRIEVE
                    && (timeLimit || oldPoint.getPosition().euclideanDistance(hivePos) > searchRadius)) {
                if (timeLimit)
                    System.out.println("Ant-" + this.hashCode() + ": Simulation time reached, switching to return mode");
                else
                    System.out.println("Ant-" + this.hashCode() + ": Reached search radius, switching to return mode");

                this.currentState = AntState.RETURN;
            } else if (this.currentState == AntState.RETURN) {
                this.direction = oldPoint.getPosition().getRelativeChange(hivePos);
            } else {
                changeDirection(oldPoint.getPosition());
            }
        }
        GameBuffer.add(queue, this, oldPoint.getPosition());

    }

    /**
     * Changes the direction of the ant based on the current state
     *
     * @param position the current position of the ant
     */
    public void changeDirection(Position position) {
        AntDirection[] directions = search(position);
        AntDirection foodDirection = directions[0];
        AntDirection hiveDirection = directions[1];
        AntDirection highTrailDirection = directions[2];
        AntDirection lowTrailDirection = directions[3];

        // disable directions depending on the current state
        if (currentState == AntState.EXPLORE) {
            hiveDirection = null;
        } else if (currentState == AntState.FOODSEARCH) {
            hiveDirection = null;
            lowTrailDirection = null;
        } else if (currentState == AntState.FOODRETRIEVE) {
            lowTrailDirection = null;
            foodDirection = null;
        }

        // choose direction based on priority
        if (foodDirection != null) {
            this.direction = foodDirection;
        } else if (hiveDirection != null) {
            this.direction = hiveDirection;
        } else if (highTrailDirection != null) {
            this.direction = highTrailDirection;
        } else if (lowTrailDirection != null) {
            this.direction = lowTrailDirection;
        } else {
            this.direction = AntDirection.values()[(int) (Math.random() * AntDirection.values().length)];
        }
    }

    /**
     * The ant will explore the environment and leave a trail.
     *
     * @param oldPoint         the point the ant is currently on
     * @param nearestPositions the possible next positions of the ant
     * @param endPosition      the position the ant will move to (if no other position is chosen)
     */
    public void explore(Point oldPoint, List<Position> nearestPositions, Position endPosition) {
        // ant prefers empty positions in exploration mode
        ArrayList<Position> emptyPositions = new ArrayList<>();
        Iterator<Position> iter = nearestPositions.iterator();
        //noinspection WhileLoopReplaceableByForEach
        while (iter.hasNext()) {
            Position pos = iter.next();
            Point point = gameState.getPoint(pos);
            if (point == null) {
                emptyPositions.add(pos);
            }
        }

        if (!emptyPositions.isEmpty())
            endPosition = emptyPositions.get((int) (Math.random() * emptyPositions.size()));

        // overwrite endPosition if there is a trail, food or another ant
        Point newPoint = null;
        double lowFactor = status.getLowTrail() - Math.random();
        double highFactor = status.getHighTrail() - Math.random();
        for (Position pos : nearestPositions) {
            Point point = gameState.getPoint(pos);
            if (point != null) {
                for (Entity e : new ArrayList<>(point.getEntities())) {
                    if (e instanceof Food) {
                        endPosition = pos;
                        newPoint = point;
                        point.removeEntity(e);
                        this.foodCount++;
                        this.currentState = AntState.FOODRETRIEVE;
                        System.out.println("Ant-" + this.hashCode() + ": Found food, switching to food-retrieve mode:" + this.currentState);
                    } else if (e instanceof Ant otherAnt) {
                        if (otherAnt.currentState == AntState.FOODRETRIEVE) {
                            if (otherAnt.hivePos.equals(this.hivePos)) {
                                endPosition = pos;
                                newPoint = point;
                                this.currentState = AntState.FOODSEARCH;
                                System.out.println("Ant-" + this.hashCode() + ": Met another ant, switching to food-search mode");
                            } else {
                                // kill ant
                                if (Math.random() < 0.5) {
                                    System.out.println("Ant-" + this.hashCode() + ": Met another ant, killing it");
                                    point.removeEntity(e);
                                    point.addEntity(new Corpse());
                                } else {
                                    System.out.println("Ant-" + this.hashCode() + ": Met another ant, killing itself");
                                    oldPoint.removeEntity(this);
                                    oldPoint.addEntity(new Corpse());
                                }
                            }
                        }
                    }
                }
            }
        }

        move(oldPoint, newPoint, endPosition);
    }


    /**
     * The ant will search for food and follow trails.
     *
     * @param oldPoint         the point the ant is currently on
     * @param nearestPositions the possible next positions of the ant
     * @param endPosition      the position the ant will move to (if no other position is chosen)
     */
    public void foodSearch(Point oldPoint, List<Position> nearestPositions, Position endPosition) {
        Point newPoint = null;
        double highestTrail = 0;
        double highFactor = status.getHighTrail() - Math.random();
        for (Position pos : nearestPositions) {
            Point point = gameState.getPoint(pos);
            if (point != null) {
                for (Entity e : new ArrayList<>(point.getEntities())) {
                    if (e instanceof Food) {
                        endPosition = pos;
                        newPoint = point;
                        point.removeEntity(e);
                        this.foodCount++;
                        this.currentState = AntState.FOODRETRIEVE;
                        System.out.println("Ant-" + this.getId() + ": Found food, switching to food-retrieve mode");
                    } else if (e instanceof Trail && ((Trail) e).isNewPath(this)) { // origin so ant doesn't follow own trail
                        double strength = ((Trail) e).getColonyStrength(this.colony);
                        if (strength > highFactor) {
                            highestTrail = strength;
                            endPosition = pos;
                            newPoint = point;
                        }
                    }
                }
            }
        }

        // if no trail is found, ant will continue to explore
        if (newPoint == null || highestTrail < highFactor) {
            this.emptySteps++;

            if (this.emptySteps > status.getAntEmptySteps()) {
                System.out.println("Ant-" + this.getId() + ": Too many empty steps, switching to explore mode");
                this.currentState = AntState.EXPLORE;
                this.emptySteps = 0;
            }
        }

        move(oldPoint, newPoint, endPosition);
    }


    /**
     * The ant will retrieve food and follow trails
     *
     * @param oldPoint         the point the ant is currently on
     * @param nearestPositions the possible next positions of the ant
     * @param endPosition      the position the ant will move to (if no other position is chosen)
     */
    public void foodRetrieve(Point oldPoint, List<Position> nearestPositions, Position endPosition) {
        Point newPoint = null;
        double highFactor = status.getHighTrail() - Math.random();
        for (Position pos : nearestPositions) {
            Point point = gameState.getPoint(pos);

            if (point != null) {
                for (Entity e : point.getEntities()) {
                    if (e instanceof Hive hive && hive.getColony().equals(this.colony)) {
                        endPosition = pos;
                        newPoint = point;
                        this.currentState = AntState.FOODSEARCH;
                        hive.addFood();
                        this.oldFoodCount = this.foodCount;
                        System.out.println("Ant-" + this.getId() + ": Reached hive, switching to food-search mode");
                    }
                }
            }
        }

        move(oldPoint, newPoint, endPosition);
    }

    /**
     * The ant will return to the hive directly
     *
     * @param oldPoint         the point the ant is currently on
     * @param nearestPositions the possible next positions of the ant
     * @param endPosition      the position the ant will move to (if no other position is chosen)
     */
    public void antReturn(Point oldPoint, List<Position> nearestPositions, Position endPosition) {
        Point newPoint = null;

        for (Position pos : nearestPositions) {
            Point point = gameState.getPoint(pos);

            if (point != null) {
                for (Entity e : point.getEntities()) {
                    if (e instanceof Hive hive && hive.getColony().equals(this.colony)) {
                        endPosition = pos;
                        newPoint = point;
                        this.currentState = AntState.FOODSEARCH;
                        if (this.foodCount == this.oldFoodCount) {
                            this.searchRadius += (int) (Parameters.INITIAL_ANT_SEARCH_RADIUS * this.status.getSearchRadiusGrowthFactor());
                            System.out.println("Ant-" + this.hashCode() + ": new search radius: " + this.searchRadius);
                        }
                        this.oldFoodCount = this.foodCount;
                        System.out.println("Ant-" + this.hashCode() + ": Reached hive, switching to food-search mode");
                        break;
                    }
                }
            }
        }

        move(oldPoint, newPoint, endPosition);
    }

    @Override
    public Entity clone() {
        return new Ant(this.currentState, this.gameState, this.status, this.colony, this.position);
    }

    /**
     * Clones this ant but gives it a new colony
     *
     * @param newColony new colony for the ant (precondition: newColony != null)
     * @return the cloned ant with the new colony
     */
    public Entity cloneWithDifferentColony(Colony newColony) {
        return new Ant(this.currentState, this.gameState, this.status, newColony, this.position);
    }

    @Override
    public int getPriority() {
        return Parameters.ANT_PRIORITY;
    }

    /**
     * Searches the environment for all points of interest within a specified radius.
     *
     * @param currentPosition the current position of the ant (precondition: currentPosition != null)
     * @return an array of AntDirections containing the direction to the food, hive, high trail and low trail (in that order)
     */
    private AntDirection[] search(Position currentPosition) {
        AntDirection food = null;
        AntDirection hive = null;
        AntDirection highTrail = null;
        AntDirection lowTrail = null;

        double highFactor = status.getHighTrail() - Math.random();
        double highest = 0;
        double lowFactor = status.getLowTrail() - Math.random();
        double low = 1;
        double foodDistance = Parameters.ANT_VIEW_DISTANCE;

        int x = currentPosition.getX();
        int y = currentPosition.getY();

        for (int i = x - Parameters.ANT_VIEW_DISTANCE; i <= x + Parameters.ANT_VIEW_DISTANCE; i++) {
            for (int j = y - Parameters.ANT_VIEW_DISTANCE; j <= y + Parameters.ANT_VIEW_DISTANCE; j++) {
                Position p = new Position(i, j);
                if (currentPosition.withinRadius(p, Parameters.ANT_VIEW_DISTANCE)) {
                    if (gameState.hasPosition(p)) {
                        Point point = gameState.getPoint(p);
                        for (Entity e : point.getEntities()) {
                            if (e instanceof Food && currentPosition.euclideanDistance(p) < foodDistance) {
                                foodDistance = currentPosition.euclideanDistance(p);
                                food = currentPosition.getRelativeChange(p);
                            } else if (e instanceof Hive h && h.getColony().equals(this.colony)) {
                                hive = currentPosition.getRelativeChange(p);
                            } else if (e instanceof Trail && ((Trail) e).isNewPath(this)) { // origin so ant doesn't follow own trail
                                double strength = ((Trail) e).getColonyStrength(this.colony);
                                if (strength > highFactor && strength > highest) {
                                    highTrail = currentPosition.getRelativeChange(p);
                                    highest = strength;
                                } else if (strength < lowFactor && strength < low) {
                                    lowTrail = currentPosition.getRelativeChange(p);
                                    low = strength;
                                }
                            }
                        }
                    }
                }
            }
        }

        return new AntDirection[]{food, hive, highTrail, lowTrail};
    }

    /**
     * Moves the ant to the new position and updates the trail.
     *
     * @param oldPoint    the point the ant is currently on
     * @param newPoint    the point the ant will move to
     * @param endPosition the position the ant will move to (if not overwritten previously by newPoint)
     */
    private void move(Point oldPoint, Point newPoint, Position endPosition) {
        if (newPoint == null && !gameState.hasPosition(endPosition)) {
            newPoint = new Point(endPosition, new ArrayList<>());
            gameState.setPoint(newPoint);
        } else if (newPoint == null) {
            newPoint = gameState.getPoint(endPosition);
        }
        if (newPoint.hasHive()) {
            newPoint.updateHiveVisited();
        }
        oldPoint.addTrail(gameState, this);
        newPoint.addEntity(this);
        oldPoint.removeEntity(this);
    }
}
