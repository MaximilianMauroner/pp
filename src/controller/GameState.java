package src.controller;

import src.model.Entity;
import src.model.Point;
import src.model.Position;
import src.model.Status;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class GameState {

    // List<Point> points;
    HashMap<Position, Point> points;

    Status status;

    public GameState(List<Point> points, Status status) {
        this.points = new HashMap<>();
        for (Point p:
             points) {
            this.points.put(p.getPosition(), p);
        }

        this.status = status;
    }

    public void getNextFrame() {
//        System.out.println("Next frame update random Point");

//        TODO:fix, temp workaround so i can do it
        if (status.getAntCount() == 10) {
            for (Point p : new ArrayList<>(points.values())) {
                ArrayList<Entity> entities = new ArrayList<>(p.getEntities());

                for (Entity e : entities) {
                    e.run(this, status, p);
                }
            }
        } else {
            if (!points.isEmpty()) {
                int x = (int) (Math.random() * 1000);
                int y = (int) (Math.random() * 1000);
                int idx = (int) (Math.random() * points.size());
                if (idx < points.size()) {
                    points.get(idx).setPosition(new Position(x, y, status));
                    System.out.println(points.get(idx).getPosition().getX() + " " + points.get(idx).getPosition().getY());
                }
            }
        }
    }

    public List<Point> getPoints() {
        return new ArrayList<>(points.values());
    }

    public Point getPoint(Position position) {
        return points.get(position);
    }

    public void setPoint(Point point) {
        points.put(point.getPosition(), point);
    }
}
