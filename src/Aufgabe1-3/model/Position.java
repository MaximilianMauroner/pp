package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Class for the position of the entities
 * The position is defined by the x and y coordinates
 * <p>
 * Modularization Units:
 * - A module for making calculations concerned about positions (e.g. distances, directions, neighbourhoods. etc.)
 * <p>
 * Abstraction: Represents the numerical part of a real world position on a grid.
 */
public class Position {
    private final int x;
    private final int y;

    /**
     * Creates a new Position object
     *
     * @param x the x coordinate of the position
     * @param y the y coordinate of the position
     */
    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }


    /**
     * Returns the x coordinate of the position
     */
    public int getX() {
        return x;
    }

    /**
     * Returns the y coordinate of the position
     */
    public int getY() {
        return y;
    }

    /**
     * Calculates the direction to the given position (e.g. position the ant object moves to)
     *
     * @param p the position to which the direction is calculated (precondition: p != null)
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
     *
     * @param direction direction the ant is looking
     * @return list of possible next positions
     */
    public List<Position> getPossibleNextPosition(AntDirection direction) {
        //Geradeaus, Links, Rechts, Halblinks, Halbrechts
        return switch (direction) {
            case NORTH -> new ArrayList<>(Arrays.asList(
                    new Position(x, y - 1),
                    new Position(x - 1, y),
                    new Position(x + 1, y),
                    new Position(x - 1, y - 1),
                    new Position(x + 1, y - 1)
            ));
            case NORTHEAST -> new ArrayList<>(Arrays.asList(
                    new Position(x - 1, y + 1),
                    new Position(x - 1, y - 1),
                    new Position(x + 1, y + 1),
                    new Position(x, y - 1),
                    new Position(x + 1, y)
            ));
            case EAST -> new ArrayList<>(Arrays.asList(
                    new Position(x + 1, y),
                    new Position(x, y - 1),
                    new Position(x, y + 1),
                    new Position(x - 1, y + 1),
                    new Position(x + 1, y + 1)
            ));
            case SOUTHEAST -> new ArrayList<>(Arrays.asList(
                    new Position(x, y + 1),
                    new Position(x + 1, y + 1),
                    new Position(x - 1, y + 1),
                    new Position(x + 1, y),
                    new Position(x - 1, y)
            ));
            case SOUTH -> new ArrayList<>(Arrays.asList(
                    new Position(x, y + 1),
                    new Position(x - 1, y),
                    new Position(x + 1, y),
                    new Position(x + 1, y + 1),
                    new Position(x - 1, y + 1)
            ));
            case SOUTHWEST -> new ArrayList<>(Arrays.asList(
                    new Position(x + 1, y - 1),
                    new Position(x - 1, y - 1),
                    new Position(x + 1, y + 1),
                    new Position(x - 1, y),
                    new Position(x, y + 1)
            ));
            case WEST -> new ArrayList<>(Arrays.asList(
                    new Position(x - 1, y),
                    new Position(x, y - 1),
                    new Position(x, y + 1),
                    new Position(x - 1, y - 1),
                    new Position(x + 1, y - 1)
            ));
            case NORTHWEST -> new ArrayList<>(Arrays.asList(
                    new Position(x - 1, y - 1),
                    new Position(x - 1, y + 1),
                    new Position(x + 1, y - 1),
                    new Position(x - 1, y),
                    new Position(x, y - 1)
            ));
            default -> null;
        };
    }

    /**
     * Calculates the euclidean distance to the given position
     *
     * @param p the position to which the distance is calculated (precondition: p != null)
     * @return the euclidean distance to the given position
     */
    public double euclideanDistance(Position p) {
        return Math.sqrt(Math.pow(x - p.getX(), 2) + Math.pow(y - p.getY(), 2));
    }

    /**
     * Calculates all neighbours of the position
     * @return a list of all eight neighbours of the position
     */
    public List<Position> getNeighbours() {
        List<Position> neighbours = new ArrayList<>();

        // NOTE: using for loops here would've been better. I don't know why I did it like this
//        for (int i = -1; i <= 1; i++) {
//            for (int j = -1; j <=1; j++) {
//                if (i == 0 && j == 0 || x + i < 0 || x + i >= status.getWidth() || y + j < 0 || y + j >= status.getHeight()) continue;
//                neighbours.add(new Position(x + i, y + j, status));
//            }
//        }

        neighbours.add(new Position(x - 1, y - 1));
        neighbours.add(new Position(x - 1, y));
        neighbours.add(new Position(x - 1, y + 1));
        neighbours.add(new Position(x, y - 1));
        neighbours.add(new Position(x, y + 1));
        neighbours.add(new Position(x + 1, y - 1));
        neighbours.add(new Position(x + 1, y));
        neighbours.add(new Position(x + 1, y + 1));

        return neighbours;
    }

    /**
     * Checks if the given position is within a given radius of the position
     *
     * @param p      the position to check (precondition: p != null)
     * @param radius the radius to check (precondition: radius >= 0)
     * @return true if the given position is within the given radius of the position
     */
    public boolean withinRadius(Position p, int radius) {
        return this.euclideanDistance(p) <= radius;
    }

    /**
     * Returns a random position near the position
     *
     * @return a random position near the position
     */
    public Position getRandomPositionNearPos() {
        int x = (int) (Math.random() * 5) - 3 + this.getX();
        int y = (int) (Math.random() * 5) - 3 + this.getY();
        return new Position(x, y);
    }

    /**
     * Checks if the given object is equal to the position
     * @param obj object to be checked (precondition: obj != null)
     * @return true if the given object is equal to the position
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Position p) {
            return p.getX() == x && p.getY() == y;
        }
        return false;
    }

    /**
     * Calculates the hashcode of the position
     * @return the hashcode of the position
     */
    @Override
    public int hashCode() {
        return Integer.hashCode(x) + Integer.hashCode(y);
    }

    /**
     * Returns a string representation of the position
     * @return a string representation of the position
     */
    @Override
    public String toString() {
        return "Position{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
