package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Class for the position of the entities
 * The position is defined by the x and y coordinates
 */
public class Position {
    private int x, y;
    private Status status;

    public Position(int x, int y, Status status) {
        if (x > status.getWidth()) {
            x = 0;
        }
        if (y > status.getHeight()) {
            y = 0;
        }
        if (x < 0) {
            x = status.getWidth();
        }
        if (y < 0) {
            y = status.getHeight();
        }
        this.status = status;
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }


    /**
     * Calculates the direction to the given position (e.g. position the ant object moves to)
     * @param p the position to which the direction is calculated
     */
    public AntDirection getRelativeChange(Position p) {
        if (x == p.getX() && y > p.getY()) {
            return AntDirection.NORTH;
        } else if (x == p.getX() && y < p.getY()) {
            return AntDirection.SOUTH;
        } else if (x > p.getX() && y == p.getY()) {
            return AntDirection.WEST;
        } else if (x < p.getX() && y == p.getY()) {
            return AntDirection.EAST;
        } else if (x > p.getX() && y > p.getY()) {
            return AntDirection.NORTHWEST;
        } else if (x < p.getX() && y > p.getY()) {
            return AntDirection.NORTHEAST;
        } else if (x > p.getX() && y < p.getY()) {
            return AntDirection.SOUTHWEST;
        } else if (x < p.getX() && y < p.getY()) {
            return AntDirection.SOUTHEAST;
        } else {
            return AntDirection.NORTH;
        }
    }

    /**
     * Calculates possible next positions given direction (e.g. position the ant object can move to)
     * @param direction direction the ant came from
     */
    public List<Position> getPossibleNextPosition(AntDirection direction) {
        //Geradeaus, Links, Rechts, Halblinks, Halbrechts
        switch (direction) {
            case NORTH:
                return new ArrayList<>(Arrays.asList(
                        new Position(x, y - 1, status),
                        new Position(x - 1, y, status),
                        new Position(x + 1, y, status),
                        new Position(x - 1, y - 1, status),
                        new Position(x + 1, y - 1, status)
                        ));
            case NORTHEAST:
                return new ArrayList<>(Arrays.asList(
                        new Position(x - 1, y + 1, status),
                        new Position(x - 1, y - 1, status),
                        new Position(x + 1, y + 1, status),
                        new Position(x, y - 1, status),
                        new Position(x + 1, y, status)
                        ));
            case EAST:
                return new ArrayList<>(Arrays.asList(
                        new Position(x + 1, y, status),
                        new Position(x, y - 1, status),
                        new Position(x, y + 1, status),
                        new Position(x - 1, y + 1, status),
                        new Position(x + 1, y + 1, status)
                        ));
            case SOUTHEAST:
                return new ArrayList<>(Arrays.asList(
                        new Position(x, y + 1, status),
                        new Position(x + 1, y + 1, status),
                        new Position(x - 1, y + 1, status),
                        new Position(x + 1, y, status),
                        new Position(x - 1, y, status)
                        ));
            case SOUTH:
                return new ArrayList<>(Arrays.asList(
                        new Position(x , y + 1, status),
                        new Position(x - 1, y, status),
                        new Position(x + 1, y , status),
                        new Position(x + 1, y + 1, status),
                        new Position(x - 1, y + 1, status)
                        ));
            case SOUTHWEST:
                return new ArrayList<>(Arrays.asList(
                        new Position(x + 1, y - 1, status),
                        new Position(x - 1, y - 1, status),
                        new Position(x + 1, y + 1, status),
                        new Position(x - 1, y, status),
                        new Position(x , y + 1, status)
                        ));
            case WEST:
                return new ArrayList<>(Arrays.asList(
                        new Position(x - 1, y, status),
                        new Position(x , y - 1, status),
                        new Position(x , y + 1, status),
                        new Position(x - 1, y - 1, status),
                        new Position(x + 1, y - 1, status)
                        ));
            case NORTHWEST:
                return new ArrayList<>(Arrays.asList(
                        new Position(x - 1, y - 1, status),
                        new Position(x - 1, y + 1, status),
                        new Position(x + 1, y - 1, status),
                        new Position(x - 1, y, status),
                        new Position(x, y - 1 , status)
                        ));
            default:
                return null;
        }
    }

    public double euclideanDistance(Position p) {
        return Math.sqrt(Math.pow(x - p.getX(), 2) + Math.pow(y - p.getY(), 2));
    }

    public List<Position> getNeighbours() {
        List<Position> neighbours = new ArrayList<>();

//        for (int i = -1; i <= 1; i++) {
//            for (int j = -1; j <=1; j++) {
//                if (i == 0 && j == 0 || x + i < 0 || x + i >= status.getWidth() || y + j < 0 || y + j >= status.getHeight()) continue;
//                neighbours.add(new Position(x + i, y + j, status));
//            }
//        }

        neighbours.add(new Position(x - 1, y - 1, status));
        neighbours.add(new Position(x - 1, y, status));
        neighbours.add(new Position(x - 1, y + 1, status));
        neighbours.add(new Position(x, y - 1, status));
        neighbours.add(new Position(x, y, status));
        neighbours.add(new Position(x, y + 1, status));
        neighbours.add(new Position(x + 1, y - 1, status));
        neighbours.add(new Position(x + 1, y, status));
        neighbours.add(new Position(x + 1, y + 1, status));

        return neighbours;
    }

    public boolean withinRadius(Position p, int radius) {
        return Math.abs(x - p.getX()) <= radius && Math.abs(y - p.getY()) <= radius;
    }


    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Position p) {
            return p.getX() == x && p.getY() == y;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(x) + Integer.hashCode(y);
    }

    @Override
    public String toString() {
        return "Position{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
