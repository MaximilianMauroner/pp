package src.model;

import src.controller.GameState;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Ant implements Entity {
    private AntState currentState = AntState.EXPLORE;
    private int emptySteps = 0;
    private AntDirection direction = AntDirection.values()[(int) (Math.random() * AntDirection.values().length)];
    private GameState gameState;
    private Status status;
    public AntState getState() {
        return this.currentState;
    }

    public Ant(AntState currentState, AntDirection direction, GameState gameState, Status status) {
        this.currentState = currentState;
        this.direction = direction;
        this.gameState = gameState;
        this.status = status;
        System.out.println("Ant created");
    }

    public Ant() {
        System.out.println("Ant created");
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
        List<Position> nearestPositions = oldPoint.getPosition().getPossibleNextPosition(this.direction);
        // ant prefers empty positions in exploration mode
        ArrayList<Position> emptyPositions = new ArrayList<>();
        for (Iterator<Position> iter = nearestPositions.iterator(); iter.hasNext(); ) {
            Position pos = iter.next();
            Point point = gameState.getPoint(pos);
            if (point == null) {
                emptyPositions.add(pos);
            } else if (point.hasObstacle()) {
                iter.remove();
            }
        }
        Position endPosition;
        if (emptyPositions.isEmpty())
            endPosition = nearestPositions.get((int) (Math.random() * nearestPositions.size()));
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
                for (Entity e : new ArrayList<>(point.getEntities())) {
                    if (e instanceof Food) {
                        endPosition = pos;
                        newPoint = point;
                        point.getEntities().remove(e);
                        this.currentState = AntState.FOODRETRIEVE;
                        System.out.println("Ant-" + this.hashCode() + ": Found food, switching to foodretrieve mode:" + this.currentState);
                    } else if (e instanceof Ant && ((Ant) e).getState() == AntState.FOODSEARCH) {
                        endPosition = pos;
                        newPoint = point;
                        this.currentState = AntState.FOODSEARCH;
                        System.out.println("Ant-" + this.hashCode() + ": Met another ant, switching to foodsearch mode");
                    } else if (e instanceof Trail && ((Trail) e).isNewPath(this.hashCode())) { // origin so ant doesn't follow own trail
                        double strength = ((Trail) e).getStrength();
                        if (strength < lowFactor) {
                            endPosition = pos;
                            newPoint = point;
                        } else if (strength > highFactor) {
                            endPosition = pos;
                            newPoint = point;
                            this.currentState = AntState.FOODSEARCH;
                            System.out.println("Ant-" + this.hashCode() + ": Found strong trail, switching to foodsearch mode");
                        } else {
                            continue;
                        }
                        break outerloop;
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

    public void foodSearch(Point oldPoint) {
        List<Position> nearestPositions = oldPoint.getPosition().getPossibleNextPosition(this.direction);
        for (Iterator<Position> iter = nearestPositions.iterator(); iter.hasNext(); ) {
            Position pos = iter.next();
            Point point = gameState.getPoint(pos);
            if (point != null && point.hasObstacle()) {
                iter.remove();
            }
        }
        Position endPosition = nearestPositions.get((int) (Math.random() * nearestPositions.size()));
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

        this.direction = oldPoint.getPosition().getRelativeChange(endPosition);

        if (newPoint == null || highestTrail < highFactor) {
            this.emptySteps++;

            if (this.emptySteps > status.getAntEmptySteps()) {
                System.out.println("Ant-" + this.hashCode() + ": Too many empty steps, switching to explore mode");
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
        List<Position> nearestPositions = oldPoint.getPosition().getPossibleNextPosition(this.direction);
        for (Iterator<Position> iter = nearestPositions.iterator(); iter.hasNext(); ) {
            Position pos = iter.next();
            Point point = gameState.getPoint(pos);
            if (point != null && point.hasObstacle()) {
                iter.remove();
            }
        }
        Position endPosition = nearestPositions.get((int) (Math.random() * nearestPositions.size()));
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
                        this.gameState.addFood();
                    } else if (e instanceof Trail && ((Trail) e).isNewPath(this.hashCode())) { // origin so ant doesn't follow own trail
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

    @Override
    public Entity clone() {
        return new Ant(this.currentState,this.direction,this.gameState,this.status);
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
