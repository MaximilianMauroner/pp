package src.view;

import codedraw.CodeDraw;
import src.controller.GameState;
import src.model.Ant;
import src.model.Point;

import java.awt.*;
import java.util.List;

public class View {
    private int width, height;
    private final CodeDraw cd;

    public View(int width, int height) {
        this.width = width;
        this.height = height;
        cd = new CodeDraw(width, height);
        cd.setTitle("Ants colony simulation");

        //TODO: Besser machen
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                cd.setPixel(i, j, Color.BLACK);
            }
        }
    }

    public void draw(GameState gameState) {
        List<Point> points = gameState.getPoints();
        for (Point point : points) {
            int x = point.getPosition().getX();
            int y = point.getPosition().getY();
            if (point.getEntities()[0] instanceof Ant) cd.setPixel(x, y, Color.RED);
        }

        cd.show();
    }
}
