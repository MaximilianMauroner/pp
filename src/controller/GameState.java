package src.controller;

import src.model.Entity;
import src.model.Point;
import src.model.Position;
import src.model.Status;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class GameState {

    ConcurrentHashMap<Position, Point> points;

    int foodCount = 0;

    Status status;

    public GameState(ConcurrentHashMap<Position, Point> points, Status status) {
        this.points = points;

        this.status = status;
    }

    public void getNextFrame() {
        for (Point p : points.values()) {
            ArrayList<Entity> entities = new ArrayList<>(p.getEntities());
            for (Entity e : entities) {
                e.run(this, status, p);
            }
        }
    }

    public ConcurrentHashMap<Position,Point> getPoints() {
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

    public void addFood() {
        this.foodCount++;
        System.out.println("Food added to hive" + this.foodCount);
    }
}
