package view;

import codedraw.CodeDraw;
import controller.GameState;
import model.*;

import java.awt.Color;
import java.util.concurrent.ConcurrentHashMap;

public class View {
    private final int width, height;
    private final CodeDraw cd;
    private Color BACKGROUND_COLOR = Color.BLACK;

    public View(int width, int height) {
        this.width = width * Parameters.SCALE_BY;
        this.height = height * Parameters.SCALE_BY;
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
        // ToDo: remove this is just a fix to stop overlaps between simulations
        cd.clear(BACKGROUND_COLOR);

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
        changeTime(gameState);

        ConcurrentHashMap<Position, Point> points = gameState.getPoints();
        for (Point point : points.values()) {
            int x = point.getPosition().getX() * Parameters.SCALE_BY;
            int y = point.getPosition().getY() * Parameters.SCALE_BY;

            for (Entity entity : point.getEntities()) {
                if (entity instanceof OptimalPath) setPixels(x, y, Parameters.SCALE_BY, Parameters.OPTIMAL_PATH_COLOR);
                if (entity instanceof Trail e)
                    setPixels(x, y, Parameters.SCALE_BY, mixColors(Parameters.TRAIL_COLOR, BACKGROUND_COLOR, e.getStrength() <= 0.15 ? 0 : (float) e.getStrength()));
                if (entity instanceof Food) setPixels(x, y, Parameters.SCALE_BY, Parameters.FOOD_SOURCE_COLOR);
                if (entity instanceof Hive) setPixels(x, y, Parameters.SCALE_BY, Parameters.COLONY_HOME_COLOR);
                if (entity instanceof Obstacle) setPixels(x, y, Parameters.SCALE_BY, Parameters.OBSTACLE_COLOR);
                if (entity instanceof Ant) {
                    switch (((Ant) entity).getState()) {
                        case EXPLORE -> setPixels(x, y, Parameters.SCALE_BY, Parameters.ANT_DEFAULT_COLOR);
                        case FOODSEARCH -> setPixels(x, y, Parameters.SCALE_BY, Parameters.ANT_SEARCH_COLOR);
                        case FOODRETRIEVE -> setPixels(x, y, Parameters.SCALE_BY, Parameters.ANT_RETRIVE_COLOR);
                    }
                }
            }
        }
    }

    /**
     * mixColors mixes two colors with the given strength. The strength must be between 0 and 1.
     * The strength is the percentage of the color. The rest is the percentage of the backgroundColor.
     * If the strength is 0, the backgroundColor will be returned. If the strength is 1, the color will be returned.
     * If the strength is between 0 and 1, the color will be mixed with the backgroundColor.
     *
     * @param color           color to mix with the backgroundColor
     * @param backgroundColor Background color. Color to mix with the foreground color
     * @param strength        strength of the color. Must be between 0 and 1
     * @return Returnes the mixed color of the two colors
     */
    private Color mixColors(Color color, Color backgroundColor, float strength) {
        assert strength >= 0 && strength <= 1;

        try {
            return new Color(
                    (int) (color.getRed() * strength + ((1 - strength) * backgroundColor.getRed())),
                    (int) (color.getGreen() * strength + ((1 - strength) * backgroundColor.getGreen())),
                    (int) (color.getBlue() * strength + ((1 - strength) * backgroundColor.getBlue())));
        } catch (Exception e) {
            System.out.println("r:" + (int) (color.getRed() * strength + ((1 - strength) * backgroundColor.getRed())));
            System.out.println("g:" + (int) (color.getGreen() * strength + ((1 - strength) * backgroundColor.getGreen())));
            System.out.println("b:" + (int) (color.getBlue() * strength + ((1 - strength) * backgroundColor.getBlue())));
            System.out.println();
            e.printStackTrace();
        }
        return null;
    }

    /**
     * changeTime: Change the time of the day. The background color will be changed.
     * The background color will be changed from black to white and from white to black.
     * A daylight cycle will be simulated.
     * <p>
     * Starts with Daytime by default.
     * If it is desired to start with NightTime, then replace the < with >=
     */
    private void changeTime(GameState gameState) {
        int hourOfTheDay = gameState.getStatus().getSimulationTime();
        Color oldBackgroundColor = BACKGROUND_COLOR;

        if (hourOfTheDay % 24 < 12) {
            BACKGROUND_COLOR = mixColors(Color.BLACK, Color.WHITE, (float) (hourOfTheDay % 12) / 12);
        } else {
            BACKGROUND_COLOR = mixColors(Color.WHITE, Color.BLACK, (float) (hourOfTheDay % 12) / 12);
        }
        if (!oldBackgroundColor.equals(BACKGROUND_COLOR)) changeBackgroundColor();
    }

    /**
     * changeBackgroundColor: Change the background color of the canvas to whatever is set in the BACKGROUND_COLOR variable at the moment.
     */
    private void changeBackgroundColor() {
        setPixels(width / 2, height / 2, width, BACKGROUND_COLOR);
    }

    /**
     * Set pixels in a square around the given coordinates
     * In essence this is a square with the size "size" around the coordinates (x,y)
     *
     * @param x     x coordinate of the center. If x is bigger than the width, it will be set to the width.
     * @param y     y coordinate of the center. If y is bigger than the height, it will be set to the height.
     * @param size  size of the square
     * @param color color of the square
     */
    private void setPixels(int x, int y, int size, Color color) {
        if (x + size < 0 || x - size > width || y + size < 0 || y - size > height) return;

        //go size/2 times to the left, right, up and down from the center and fill every pixel with the given color
        for (int i = x - size / 2; i <= x + size / 2; i++) {
            for (int j = y - size / 2; j <= y + size / 2; j++) {
                if (i >= 0 && i <= width && j >= 0 && j <= height) cd.setPixel(i, j, color);
            }
        }
    }
}
