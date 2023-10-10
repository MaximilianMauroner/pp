package src.controller;

import src.model.Point;
import src.model.Position;

import java.util.List;

public class GameState {

    List<Point> points;

    public GameState(List<Point> points) {
        this.points = points;
    }

    public void getNextFrame() {
        System.out.println("Next frame");
        if (!points.isEmpty()) {
            int x = (int) (Math.random() * 1000);
            int y = (int) (Math.random() * 1000);
            int idx = (int) (Math.random() * points.size());
            if (idx < points.size()) {
                points.get(idx).setPosition(new Position(x, y));
                System.out.println(points.get(idx).getPosition().getX() + " " + points.get(idx).getPosition().getY());
            }
        }
    }

    public List<Point> getPoints() {
        return points;
    }
}
