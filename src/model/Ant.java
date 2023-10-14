package src.model;

import src.controller.GameState;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Ant implements Entity {
    private AntState currentState = AntState.EXPLORE;
    private int emptySteps = 0;
    private AntDirection direction = AntDirection.values()[(int) (Math.random() * AntDirection.values().length)];
    private GameState gameState;
    private Status status;



    public void setState(AntState state) {
        this.currentState = state;
    }

    public AntState getState() {
        return this.currentState;
    }

    public Ant(AntState currentState, AntDirection direction, GameState gameState, Status status) {
        this.currentState = currentState;
        this.direction = direction;
        this.gameState = gameState;
        this.status = status;
    }

    public Ant() {
    }

    @Override
    public void run(GameState gameState, Status status, Point point) {
        this.gameState = gameState;
        this.status = status;
        switch (currentState) {
            case EXPLORE:
                explore(point);
                break;
            case FOODSEARCH:
                foodSearch(point);
                break;
            case FOODRETRIEVE:
                foodRetrieve(point);
                break;
        }
    }

    public void explore(Point oldPoint) {
        Position[] nearestPositions = oldPoint.getPosition().getPossibleNextPosition(this.direction);

        // ant prefers empty positions in exploration mode
        ArrayList<Position> emptyPositions = new ArrayList<>();
        for (Position pos : nearestPositions) {
            Point point = gameState.getPoint(pos);
            if (point == null) {
                emptyPositions.add(pos);
            }
        }
        Position endPosition;
        if (emptyPositions.isEmpty())
            endPosition = nearestPositions[(int) (Math.random() * nearestPositions.length)];
        else
            endPosition = emptyPositions.get((int) (Math.random() * emptyPositions.size()));

        // overwrite endPosition if there is a trail, food or another ant
        Point newPoint = null;
        double lowFactor = status.getLowTrail() - Math.random();
        double highFactor = status.getHighTrail() - Math.random();
        outerloop:
        for (Position pos : nearestPositions) {
            Point point = gameState.getPoint(pos);
            if (point != null) {
                for (Entity e : point.getEntities()) {
                    if (e instanceof Food) {
                        endPosition = pos;
                        newPoint = point;
                        this.currentState = AntState.FOODRETRIEVE;
                        System.out.println("Ant-" + this.hashCode() + ": Switching to foodretrieve mode");
                    } else if (e instanceof Ant && ((Ant) e).getState() == AntState.FOODSEARCH) {
                        endPosition = pos;
                        newPoint = point;
                        this.currentState = AntState.FOODSEARCH;
                        System.out.println("Ant-" + this.hashCode() + ": Switching to foodsearch mode");
                    } else if (e instanceof Trail && ((Trail) e).getOrigin() != this.hashCode()) { // origin so ant doesn't follow own trail
                        double strength = ((Trail) e).getStrength();
                        if (strength < lowFactor) {
                            endPosition = pos;
                            newPoint = point;
                        } else if (strength > highFactor) {
                            endPosition = pos;
                            newPoint = point;
                            this.currentState = AntState.FOODSEARCH;
                            System.out.println("Ant-" + this.hashCode() + ": Switching to foodsearch mode");
                        }else{
                            continue;
                        }
                        break outerloop;
                    }
                }
            }
        }
//        System.out.println("Ant-" + this.hashCode() + " moved from " + p.getX() + " " + p.getY() + " to " + endPosition.getX() + " " + endPosition.getY() + " " + this.direction);
        this.direction = oldPoint.getPosition().getRelativeChange(endPosition);
//        System.out.println("Ant is now facing " + this.direction);
        if (newPoint == null) {
            newPoint = new Point(endPosition, new ArrayList<>());
            gameState.setPoint(newPoint);
        }
        oldPoint.addTrail(new Trail(1, this.hashCode()));
        newPoint.getEntities().add(this);
        oldPoint.getEntities().remove(this);

    }

    public void foodSearch(Point oldPoint) {
        Position[] nearestPositions = oldPoint.getPosition().getPossibleNextPosition(this.direction);

        Position endPosition = nearestPositions[(int) (Math.random() * nearestPositions.length)];
        Point newPoint = null;
        double highestTrail = 0;
        double highFactor = status.getHighTrail() - Math.random();
        for (Position pos : nearestPositions) {
            Point point = gameState.getPoint(pos);

            if (point != null) {
                for (Entity e : point.getEntities()) {
                    if (e instanceof Food) {
                        endPosition = pos;
                        newPoint = point;
                        this.currentState = AntState.FOODRETRIEVE;
                        System.out.println("Ant-" + this.hashCode() + ": Switching to foodretrieve mode");
                    } else if (e instanceof Trail && ((Trail) e).getOrigin() != this.hashCode()) { // origin so ant doesn't follow own trail
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

        this.direction = oldPoint.getPosition().getRelativeChange(endPosition);

        if (newPoint == null || highestTrail < highFactor) {
            this.emptySteps++;

            if (this.emptySteps > status.getAntEmptySteps()) {
                System.out.println("Ant-" + this.hashCode() + ": Switching to explore mode");
                this.currentState = AntState.EXPLORE;
                this.emptySteps = 0;
            }
        }

        if (newPoint == null) {
            newPoint = new Point(endPosition, new ArrayList<>());
            gameState.setPoint(newPoint);
        }

        oldPoint.addTrail(new Trail(1, this.hashCode()));
        newPoint.getEntities().add(this);
        oldPoint.getEntities().remove(this);
    }

    public void foodRetrieve(Point oldPoint) {
        Position[] nearestPositions = oldPoint.getPosition().getPossibleNextPosition(this.direction);

        Position endPosition = nearestPositions[(int) (Math.random() * nearestPositions.length)];
        Point newPoint = null;
        double highFactor = status.getHighTrail() - Math.random();
        for (Position pos : nearestPositions) {
            Point point = gameState.getPoint(pos);

            if (point != null) {
                for (Entity e : point.getEntities()) {
                    if (e instanceof Hive) {
                        endPosition = pos;
                        newPoint = point;
                        this.currentState = AntState.FOODSEARCH;
                        System.out.println("Ant-" + this.hashCode() + ": Switching to foodsearch mode");
                    } else if (e instanceof Trail && ((Trail) e).getOrigin() != this.hashCode()) { // origin so ant doesn't follow own trail
                        double strength = ((Trail) e).getStrength();
                        if (strength > highFactor) {
                            endPosition = pos;
                            newPoint = point;
                        }
                    }
                }
            }
        }

        this.direction = oldPoint.getPosition().getRelativeChange(endPosition);

        if (newPoint == null) {
            newPoint = new Point(endPosition, new ArrayList<>());
            gameState.setPoint(newPoint);
        }

        oldPoint.addTrail(new Trail(1, this.hashCode()));
        newPoint.getEntities().add(this);
        oldPoint.getEntities().remove(this);
    }

//    private double[] getTrailIntensities(Point oldPoint) {
//        double[] weights = new double[4];
//
////        int weightIdx = 0;
////        for (AntDirection direction : AntDirection.values()) {
////            Position currentPosition = oldPoint.getPosition();
////            for (int i = 0; i < status.getAntViewDistance(); i++) {
////                Position nextPosition = currentPosition.getPossibleNextPosition(direction)[0];
////                Point nextPoint = gameState.getPoint(nextPosition);
////                if (nextPoint != null && (weights[weightIdx] == 0 || weights[weightIdx] < nextPoint.getTrail()))
////                    weights[weightIdx] = nextPoint.getTrail();
////
////                currentPosition = nextPosition;
////            }
////            weightIdx++;
////        }
//
//        int weightIdx = 0;
//        Position currentPosition = oldPoint.getPosition();
//        for (Position sourround : currentPosition.getPossibleNextPosition(direction)) {
//            AntDirection viewDirection = currentPosition.getRelativeChange(sourround);
//            // get sourrounding points in view distance radius
//            for (int i = 0; i < status.getAntViewDistance(); i++) {
//                // get next point in direction
//                Position nextPosition = currentPosition.getPossibleNextPosition(viewDirection)[0];
//                Point nextPoint = gameState.getPoint(nextPosition);
//                if (nextPoint != null && (weights[weightIdx] == 0 || weights[weightIdx] < nextPoint.getTrail()))
//                    weights[weightIdx] = nextPoint.getTrail();
//
//                currentPosition = nextPosition;
//            }
//            currentPosition = oldPoint.getPosition();
//            weightIdx++;
//        }
//
//
//        return weights;
//    }

}
