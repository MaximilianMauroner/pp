package model;

import controller.GameState;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Class for the ant entity and its logic
 * contains reference objects to the game and status (for parameter access)
 */
public class Ant implements Entity {
    private Hive home;
    private Position hivePos;
    private AntState currentState = AntState.EXPLORE;
    private int emptySteps = 0;
    private AntDirection direction = AntDirection.values()[(int) (Math.random() * AntDirection.values().length)];
    private GameState gameState;
    private Status status;

    private int moveSteps;

    private int waitSteps;

    private int searchRadius = Parameters.INITIAL_ANT_SEARCH_RADIUS;

    private int foodCount = 0;
    private int oldFoodCount = 0;


    public AntState getState() {
        return this.currentState;
    }

    // ToDo: home and hivePos will be replaces by colony which should contain a hive id and position
    public Ant(AntState currentState, Hive home, Position hivePos, GameState gameState, Status status) {
        this.home = home;
        this.hivePos = hivePos;
        this.currentState = currentState;
        this.gameState = gameState;
        this.status = status;
        this.moveSteps = status.getAntMoveSteps();
        this.waitSteps = status.getAntWaitSteps();
        System.out.println("Ant created");
    }

    public Ant() {
        System.out.println("Ant created");
    }


    /**
     * Starts the State Logic of the Ant. The Ant will move to the next Point.
     *
     * @param gameState the current game state
     * @param status    the current parameters of the game
     * @param oldPoint  the point the ant is currently on
     */
    @Override
    public void run(GameState gameState, Status status, Point oldPoint) {
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

    }

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
        for (Iterator<Position> iter = nearestPositions.iterator(); iter.hasNext(); ) {
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
                        System.out.println("Ant-" + this.hashCode() + ": Found food, switching to foodretrieve mode:" + this.currentState);
                    } else if (e instanceof Ant otherAnt) {
                        if (otherAnt.currentState == AntState.FOODRETRIEVE) {
                            if (otherAnt.home.equals(this.home)) {
                                endPosition = pos;
                                newPoint = point;
                                this.currentState = AntState.FOODSEARCH;
                                System.out.println("Ant-" + this.hashCode() + ": Met another ant, switching to foodsearch mode");
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
                        System.out.println("Ant-" + this.hashCode() + ": Found food, switching to foodretrieve mode");
                    } else if (e instanceof Trail && ((Trail) e).isNewPath(this.hashCode())) { // origin so ant doesn't follow own trail
                        double strength = ((Trail) e).getStrength();
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
                System.out.println("Ant-" + this.hashCode() + ": Too many empty steps, switching to explore mode");
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
                    if (e instanceof Hive && ((Hive) e).equals(this.home)) {
                        endPosition = pos;
                        newPoint = point;
                        this.currentState = AntState.FOODSEARCH;
                        ((Hive) e).addFood();
                        this.oldFoodCount = this.foodCount;
                        System.out.println("Ant-" + this.hashCode() + ": Reached hive, switching to foodsearch mode");
                    }
                }
            }
        }

        move(oldPoint, newPoint, endPosition);
    }

    public void antReturn(Point oldPoint, List<Position> nearestPositions, Position endPosition) {
        Point newPoint = null;

        for (Position pos: nearestPositions) {
            Point point = gameState.getPoint(pos);

            if (point != null) {
                for (Entity e : point.getEntities()) {
                    if (e instanceof Hive && ((Hive) e).equals(this.home)) {
                        endPosition = pos;
                        newPoint = point;
                        this.currentState = AntState.FOODSEARCH;
                        if (this.foodCount == this.oldFoodCount) {
                            this.searchRadius += (int) (Parameters.INITIAL_ANT_SEARCH_RADIUS * this.status.getSearchRadiusGrowthFactor());
                            System.out.println("Ant-" + this.hashCode() + ": new search radius: " + this.searchRadius);
                        }
                        this.oldFoodCount = this.foodCount;
                        System.out.println("Ant-" + this.hashCode() + ": Reached hive, switching to foodsearch mode");
                        break;
                    }
                }
            }
        }

        move(oldPoint, newPoint, endPosition);
    }


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
                            } else if (e instanceof Hive && ((Hive) e).equals(this.home)) {
                                hive = currentPosition.getRelativeChange(p);
                            } else if (e instanceof Trail && ((Trail) e).isNewPath(this.hashCode())) { // origin so ant doesn't follow own trail
                                double strength = ((Trail) e).getStrength();
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
        }else if(newPoint == null) {
            newPoint = gameState.getPoint(endPosition);
        }
        //oldPoint.addTrail(new Trail(1, this.hashCode()));
        oldPoint.addTrail(gameState, this.hashCode());
        newPoint.addEntity(this);
        oldPoint.removeEntity(this);
    }

    @Override
    public Entity clone() {
        return new Ant(this.currentState, this.home, this.hivePos, this.gameState, this.status);
    }

    @Override
    public int getPriority() {
        return Parameters.ANT_PRIORITY;
    }



}
