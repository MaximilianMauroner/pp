package model;

import java.util.ArrayList;
import java.util.List;

public class Path {
    private List<Position> positionList = new ArrayList<>();
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
