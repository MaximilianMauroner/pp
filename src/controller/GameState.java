package src.controller;

import src.model.Entity;
import src.model.Point;
import src.model.Position;
import src.model.Status;

import javax.swing.text.html.HTMLDocument;
import java.util.List;

public class GameState {

    List<Point> points;

    Status status;

    public GameState(List<Point> points, Status status) {
        this.points = points;
        this.status = status;
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
//        temp
        if (status.getAntCount() == 10) {
            for (Point p : points) {
                Entity[] entities = p.getEntities();
                for (Entity e : entities) {
                    e.run(this, status);
                }
            }


        }
    }

    public List<Point> getPoints() {
        return points;
    }
}
