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

    public List<Position> getPositionList() {
        return positionList;
    }

    public void addPosition(Position position) {
        positionList.add(position);
    }

    public void addPointMetric(double metric) {
        this.metric += metric;
    }

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

    public boolean contains(Position position) {
        for (Position p : positionList) {
            if (p.equals(position)) {
                return true;
            }
        }
        return false;
    }
}
