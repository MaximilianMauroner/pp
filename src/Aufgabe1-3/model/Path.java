package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Class storing useful information about a path
 * <p>
 * Modularization Units:
 * - Contains objects (in a list) for positions of the path (e.g. Positions/Pixels that lie on the path)
 * - Provides a module for retrieving information about the path (e.g. length, metric)
 * <p>
 * Abstraction: Represents a theoretical (often optimal in terms of length) sequence of positions.
 * Is used as a guideline for measuring the ants performance in terms of finding optimal paths
 */
public class Path {
    private final List<Position> positionList = new ArrayList<>();
    private double metric = 0; // ToDo: think of a statistically more sound metric

    /**
     * @return list of all positions on the path
     */
    public List<Position> getPositionList() {
        return positionList;
    }

    /**
     * Adds a position to the path
     * @param position position to be added to the path
     */
    public void addPosition(Position position) {
        positionList.add(position);
    }

    /**
     * @param metric metric to be added to the path (see OptimalPathPoint)
     *               (precondition: metric >= 0)
     */
    public void addPointMetric(double metric) {
        this.metric += metric;
    }

    /**
     * Calculates the mean of all point metrics
     * @return metric for the path
     */
    public double getPathMetric() {
        return metric / positionList.size();
    }

    @Override
    public Path clone() {
        Path clone = new Path();
        for (Position position : positionList) {
            clone.addPosition(position);
        }
        return clone;
    }

    /**
     * Checks whether the path contains the given position
     * @param position position to be checked
     * @return true if the path contains the given position
     */
    public boolean contains(Position position) {
        for (Position p : positionList) {
            if (p.equals(position)) {
                return true;
            }
        }
        return false;
    }
}
