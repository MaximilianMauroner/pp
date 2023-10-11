package src.model;

import src.controller.GameState;

import java.util.List;

public class Ant implements Entity {
    private AntState currentState = AntState.EXPLORE;

    private AntDirection direction = AntDirection.values()[(int)(Math.random() * 4)] ;
    private GameState gameState;
    private Status status;
    private Point point;

    public void setState(AntState state) {
        this.currentState = state;
    }

    public AntState getState() {
        return this.currentState;
    }

    @Override
    public void run(GameState gameState, Status status, Point point) {
        this.gameState = gameState;
        this.status = status;
        this.point = point;
        switch (currentState) {
            case EXPLORE:
                explore();
                break;
            case FOODSEARCH:
                foodSearch();
                break;
            case FOODRETRIEVE:
                foodRetrieve();
                break;
        }
    }

    public void explore() {
        List<Point> points = gameState.getPoints();
        Position p = point.getPosition();
        Position[] nearestPositions;
        switch (direction) {
            case EAST:
                nearestPositions = new Position[]{
                        new Position(p.getX() + 1, p.getY(), status),
                        new Position(p.getX() + 1, p.getY() + 1, status),
                        new Position(p.getX() + 1, p.getY() - 1, status),
                        new Position(p.getX(), p.getY() + 1, status),
                        new Position(p.getX(), p.getY() - 1, status),
                };
                break;
            case SOUTH:
                nearestPositions = new Position[]{
                        new Position(p.getX(), p.getY() + 1, status),
                        new Position(p.getX() + 1, p.getY() + 1, status),
                        new Position(p.getX() - 1, p.getY() + 1, status),
                        new Position(p.getX() + 1, p.getY(), status),
                        new Position(p.getX() - 1, p.getY(), status),
                };
                break;
            case WEST:
                nearestPositions = new Position[]{
                        new Position(p.getX() - 1, p.getY(), status),
                        new Position(p.getX() - 1, p.getY() + 1, status),
                        new Position(p.getX() - 1, p.getY() - 1, status),
                        new Position(p.getX(), p.getY() + 1, status),
                        new Position(p.getX(), p.getY() - 1, status),
                };
                break;
            default:
                nearestPositions = new Position[]{
                        new Position(p.getX(), p.getY() - 1, status),
                        new Position(p.getX() + 1, p.getY() - 1, status),
                        new Position(p.getX() - 1, p.getY() - 1, status),
                        new Position(p.getX() + 1, p.getY(), status),
                        new Position(p.getX() - 1, p.getY(), status),
                };
                break;
        }
        double highestTrail = 0;
        Position endPosition = nearestPositions[(int) (Math.random() * nearestPositions.length)];
        for (Position pos : nearestPositions) {
            for (Point point : points) {
                if (point.getPosition().equals(pos) && point.getTrail() > highestTrail) {
                    highestTrail = point.getTrail();
                    endPosition = pos;
                }
            }
        }
        this.point.addTrail(new Trail(1));
        this.point.setPosition(endPosition);

    }

    public void foodSearch() {

    }

    public void foodRetrieve() {

    }

}
