package src.view;

import codedraw.CodeDraw;
import src.StartProgramm;
import src.controller.GameState;
import src.model.Ant;
import src.model.Entity;
import src.model.Point;
import src.model.Trail;

import java.awt.*;
import java.util.List;

public class View {
    private final int width, height;
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
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                cd.setPixel(i, j, Color.BLACK);
            }
        }
        drawElements(gameState, false);

        cd.show();
    }

    private void drawElements(GameState gameState, boolean clear) {
        Color antColor = clear ? Color.black : StartProgramm.ANT_COLOR;
        Color foodSourceColor = clear ? Color.black : StartProgramm.FOOD_SOURCE_COLOR;
        Color colonyHomeColor = clear ? Color.black : StartProgramm.COLONY_HOME_COLOR;
        Color obstacleColor = clear ? Color.black : StartProgramm.OBSTACLE_COLOR;


        List<Point> points = gameState.getPoints();
        for (Point point : points) {
            int x = point.getPosition().getX();
            int y = point.getPosition().getY();
            for (Entity entity : point.getEntities()) {
                if (entity instanceof Ant) {
                    setPixels(x, y, StartProgramm.ANT_SIZE, antColor);
                } else if (entity instanceof Trail) {
                    Color c = StartProgramm.TRAIL_COLOR;
                    double value = 1 - point.getTrail();
                    int r = (int) (c.getRed() * value);
                    int g = (int) (c.getGreen() * value);
                    int b = (int) (c.getBlue() * value);
                    int a = (int) (c.getAlpha() * value);
                    setPixels(x, y, StartProgramm.SMELL_SIZE, clear ? Color.BLACK : new Color(r, g, b, a)); //replace the 100 with the intensity of the "smell"
                } else if (entity instanceof Entity) {
                    setPixels(x, y, StartProgramm.FOOD_SOURCE_SIZE, foodSourceColor);
                } //insert food class instead of Entity here
                else if (entity instanceof Entity) {
                    setPixels(x, y, StartProgramm.COLONY_HOME_SIZE, colonyHomeColor);
                } //insert home class instead of Entity here
                else if (entity instanceof Entity) {
                    setPixels(x, y, StartProgramm.OBSTACLE_SIZE, obstacleColor);
                } //insert obstacle class instead of Entity here
                else if (entity instanceof Entity) {
                    setPixels(x, y, StartProgramm.SMELL_SIZE,//insert food class instead of Entity here
                            clear ? Color.BLACK : new Color(169, 0, 255, 100)); //replace the 100 with the intensity of the "smell"
                }
            }
        }
    }

    private void setPixels(int x, int y, int size, Color color) {
        for (int i = x - size / 2; i < x + size / 2; i++) {
            for (int j = y - size / 2; j < y + size / 2; j++) {
                if (i >= 0 && i < width && j >= 0 && j < height) cd.setPixel(i, j, color);
            }
        }
    }
}
