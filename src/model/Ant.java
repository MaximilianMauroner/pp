package src.model;

import src.controller.GameState;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Ant implements Entity {
    private AntState currentState = AntState.EXPLORE;

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
                foodSearch();
                break;
            case FOODRETRIEVE:
                foodRetrieve();
                break;
        }
    }

    public void explore(Point oldPoint) {
        List<Point> points = gameState.getPoints();

        //double[] weights = getTrailIntensities(oldPoint);


        Position[] nearestPositions = oldPoint.getPosition().getPossibleNextPosition(this.direction);
        Position endPosition = nearestPositions[(int) (Math.random() * nearestPositions.length)];
        Point newPoint = null;
        double highestTrail = 0;
        for (Position pos : nearestPositions) {
            Point point = gameState.getPoint(pos);
            if (point != null && point.getTrail() > highestTrail) {
                highestTrail = point.getTrail();
                endPosition = pos;
                newPoint = point;
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

    public void foodSearch() {

    }

    public void foodRetrieve() {

    }

    private double[] getTrailIntensities(Point oldPoint) {
        double[] weights = new double[8];

        int weightIdx = 0;
        for (AntDirection direction : AntDirection.values()) {
            Position currentPosition = oldPoint.getPosition();
            for (int i = 0; i < status.getAntViewDistance(); i++) {
                Position nextPosition = currentPosition.getPossibleNextPosition(direction)[0];
                Point nextPoint = gameState.getPoint(nextPosition);
                if (nextPoint != null && (weights[weightIdx] == 0 || weights[weightIdx] < nextPoint.getTrail()))
                    weights[weightIdx] = nextPoint.getTrail();

                currentPosition = nextPosition;
            }
            weightIdx++;
        }


        return weights;
    }

}
