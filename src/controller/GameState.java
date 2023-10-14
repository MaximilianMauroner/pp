package src.controller;

import src.model.Entity;
import src.model.Point;
import src.model.Position;
import src.model.Status;

import java.util.*;

public class GameState {

    // List<Point> points;
    HashMap<Position, Point> points;

    Status status;

    public GameState(HashMap<Position, Point> points, Status status) {
        this.points = points;

        this.status = status;
    }

    public void getNextFrame() {
        for (Point p : new ArrayList<>(points.values())) {
            ArrayList<Entity> entities = new ArrayList<>(p.getEntities());

            for (Entity e : entities) {
                e.run(this, status, p);
            }
        }
    }

    public HashMap<Position,Point> getPoints() {
        return this.points;
    }

    public Point getPoint(Position position) {
        return points.get(position);
    }

    public void setPoint(Point point) {
        points.put(point.getPosition(), point);
    }

    public Status getStatus() {
        return status;
    }
}
