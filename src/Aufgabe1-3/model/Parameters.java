package model;

import java.awt.*;

public class Parameters {
    public static final int SCALE_BY = 4;

    public static final int WIDTH = 225;
    public static final int HEIGHT = 225;


    //Set color for entities
    public static final Color ANT_DEFAULT_COLOR = Color.RED;
    public static final Color ANT_SEARCH_COLOR = Color.YELLOW;
    public static final Color ANT_RETRIVE_COLOR = Color.WHITE;
    public static final Color FOOD_SOURCE_COLOR = Color.GREEN;
    public static final Color COLONY_HOME_COLOR = new Color(184, 156, 80);
    public static final Color OBSTACLE_COLOR = new Color(114, 110, 110);
    public static final Color TRAIL_COLOR = new Color(28, 63, 255);


    //set priority for entities
    public static final int ANT_PRIORITY = 100;
    public static final int HIVE_PRIORITY = 90;
    public static final int FOOD_PRIORITY = 80;
    public static final int OBSTACLE_PRIORITY = 70;
    public static final int TRAIL_PRIORITY = 60;
}
