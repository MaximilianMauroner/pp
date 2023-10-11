package src.controller;

import src.model.Entity;
import src.model.Point;
import src.model.Position;
import src.model.Status;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GameState {

    List<Point> points;

    Status status;

    public GameState(List<Point> points, Status status) {
        this.points = points;
        this.status = status;
    }

    public void getNextFrame() {
//        System.out.println("Next frame update random Point");

//        TODO:fix, temp workaround so i can do it
        if (status.getAntCount() == 10) {
            for (Point p : new ArrayList<>(points)) {
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
        return points;
    }
}
