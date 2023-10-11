package src.model;

import src.controller.GameState;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Ant implements Entity {
    private AntState currentState = AntState.EXPLORE;

    private AntDirection direction = AntDirection.values()[(int) (Math.random() * 4)];
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
        Position p = oldPoint.getPosition();
        Position[] nearestPositions;
        nearestPositions = oldPoint.getPosition().getPossibleNextPosition(this.direction);
        double highestTrail = 0;
        Position endPosition = nearestPositions[(int) (Math.random() * nearestPositions.length)];
        Point newPoint = null;
        for (Position pos : nearestPositions) {
            for (Point point : points) {
                if (point.getPosition().equals(pos) && point.getTrail() > highestTrail) {
                    highestTrail = point.getTrail();
                    endPosition = point.getPosition();
                    newPoint = point;
                }
            }
        }
        this.direction = oldPoint.getPosition().getRelativeChange(endPosition);
        if (newPoint == null) {
            newPoint = new Point(endPosition, List.of(this));
            points.add(newPoint);
        }
        oldPoint.addTrail(new Trail(1));

    }

    public void foodSearch() {

    }

    public void foodRetrieve() {

    }

    @Override
    public Entity clone() {
        return new Ant(this.currentState, this.direction, this.gameState, this.status);
    }

}
