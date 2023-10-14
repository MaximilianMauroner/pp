package src.view;

import codedraw.CodeDraw;
import src.Test;
import src.controller.GameState;
import src.model.*;
import src.model.Point;

import java.awt.*;
import java.util.HashMap;

public class View {
    private final int width, height;
    private final CodeDraw cd;

    public View(int width, int height) {
        this.width = width * Test.SCALE_BY;
        this.height = height * Test.SCALE_BY;
        cd = new CodeDraw(this.width, this.height);
        cd.setTitle("Ants colony simulation");
    }

    /**
     * Draw the given gameState on the canvas. The settings can be found in the Test class.
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
     * drawElements is the actual methode to draw the given gameState on the canvas. The settings can be found in the Test class.
     * The canvas should be cleared before drawing again. Otherwise, the old elements will still be visible.
     *
     * @param gameState gameState to draw on the canvas
     */
    private void drawElements(GameState gameState) {

        HashMap<Position, Point> points = gameState.getPoints();
        for (Point point : points.values()) {
            int x = point.getPosition().getX() * Test.SCALE_BY;
            int y = point.getPosition().getY() * Test.SCALE_BY;

            for (Entity entity : point.getEntities()) {
                if (entity instanceof Trail e) setPixels(x, y, Test.SCALE_BY, new Color(
                        (int) (Test.TRAIL_COLOR.getRed() * e.getStrength()),
                        (int) (Test.TRAIL_COLOR.getGreen() * e.getStrength()),
                        (int) (Test.TRAIL_COLOR.getBlue() * e.getStrength())));
                if (entity instanceof Food) setPixels(x, y, Test.SCALE_BY, Test.FOOD_SOURCE_COLOR);
                if (entity instanceof Hive) setPixels(x, y, Test.SCALE_BY, Test.COLONY_HOME_COLOR);
                if (entity instanceof Obstacle) setPixels(x, y, Test.SCALE_BY, Test.OBSTACLE_COLOR);
                if (entity instanceof Ant) setPixels(x, y, Test.SCALE_BY, Test.ANT_COLOR);

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

        for (int i = x - size / 2; i <= x + size / 2; i++) {
            for (int j = y - size / 2; j <= y + size / 2; j++) {
                cd.setPixel(i, j, color);
            }
        }
    }
}
