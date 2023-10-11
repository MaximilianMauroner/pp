package src.view;

import codedraw.CodeDraw;
import src.StartProgramm;
import src.controller.GameState;
import src.model.*;
import src.model.Point;

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
    }

    /**
     * Draw the given gameState on the canvas. The settings can be found in the StartProgramm class.
     * The canvas will be cleared automatically.
     *
     * @param gameState gameState to draw on the canvas. The gameState must include a Point[] with all Entities. If the array is empty, then nothing will be drawn.
     */
    public void draw(GameState gameState) {
        resetCanvas();
        drawElements(gameState);


        cd.show();
    }

    /**
     * drawElements is the actual methode to draw the given gameState on the canvas. The settings can be found in the StartProgramm class.
     * The canvas should be cleared before drawing again. Otherwise, the old elements will still be visible.
     *
     * @param gameState gameState to draw on the canvas
     */
    private void drawElements(GameState gameState) {

        List<Point> points = gameState.getPoints();
        for (Point point : points) {
            int x = point.getPosition().getX();
            int y = point.getPosition().getY();

            for (Entity entity : point.getEntities()) {
                if (entity instanceof Ant) setPixels(x, y, StartProgramm.ANT_SIZE, StartProgramm.ANT_COLOR);
                else if (entity instanceof Food) setPixels(x, y, StartProgramm.FOOD_SOURCE_SIZE, StartProgramm.FOOD_SOURCE_COLOR);
                else if (entity instanceof Hive) setPixels(x, y, StartProgramm.COLONY_HOME_SIZE, StartProgramm.COLONY_HOME_COLOR);
                else if (entity instanceof Obstacle) setPixels(x, y, StartProgramm.OBSTACLE_SIZE, StartProgramm.OBSTACLE_COLOR);
                else if (entity instanceof Trail e) setPixels(x, y, StartProgramm.TRAIL_SIZE,
                        //The RBG values must be converted into floats between 0 and 1. This only applies, if there is an Alpha channel in use
                        new Color((float) 169 / 255, (float) 0 / 255, (float) 255 / 255, (float) e.getStrength()));

            }
        }
    }

    /**
     * Reset the canvas to black
     */
    private void resetCanvas() {
        setPixels(width / 2, height / 2, width, Color.BLACK);
    }

    /**
     * Set pixels in a square around the given coordinates
     *
     * @param x     x coordinate of the center. If x is bigger than the width, it will be set to the width.
     * @param y     y coordinate of the center. If y is bigger than the height, it will be set to the height.
     * @param size  size of the square
     * @param color color of the square
     */
    private void setPixels(int x, int y, int size, Color color) {
        if (x < 0) x = 0;
        if (x > width) x = width;
        if (y < 0) y = 0;
        if (y > height) y = height;

        for (int i = x - size / 2; i < x + size / 2; i++) {
            for (int j = y - size / 2; j < y + size / 2; j++) {
                cd.setPixel(i, j, color);
            }
        }
    }
}
