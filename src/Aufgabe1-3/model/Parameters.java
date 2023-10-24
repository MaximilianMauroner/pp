package model;

import java.awt.*;

public class Parameters {
    public static final int SCALE_BY = 4;

    public static final int WIDTH = 225;
    public static final int HEIGHT = 225;

//    public static final int SIMULATION_RUNS = 5;
    public static final int SIMULATION_RUNS = 1; // ToDo: change back to 5

    //Set color for entities
    public static final Color ANT_DEFAULT_COLOR = Color.RED;
    public static final Color ANT_SEARCH_COLOR = Color.YELLOW;
    public static final Color ANT_RETRIVE_COLOR = Color.WHITE;
    public static final Color FOOD_SOURCE_COLOR = Color.GREEN;
    public static final Color COLONY_HOME_COLOR = new Color(184, 156, 80);
    public static final Color OBSTACLE_COLOR = new Color(114, 110, 110);
    public static final Color TRAIL_COLOR = new Color(28, 63, 255);
    public static final Color OPTIMAL_PATH_COLOR = new Color(255, 255, 100);
    public static final Color CORPSE_COLOR = Color.RED;

    //set priority for entities
    public static final int ANT_PRIORITY = 100;
    public static final int HIVE_PRIORITY = 90;
    public static final int FOOD_PRIORITY = 80;
    public static final int OBSTACLE_PRIORITY = 70;
    public static final int TRAIL_PRIORITY = 60;
    public static final int CORPSE_PRIORITY = 50;
    public static final int OPTIMAL_PATH_PRIORITY = 40;


    // Set size for entities
    public static final int FOOD_SIZE = 10;
    public static final int HIVE_SIZE = 20;
    public static final int OBSTACLE_SIZE = 8;
    public static final int TRAIL_SIZE = 2; // aka how much do you want to kill your pc

    // amount of entities
    public static final int HIVE_COUNT = 2;
    public static final int INITIAL_FOOD_COUNT = 7;
//    public static final int INITIAL_ANT_COUNT = 100;
    public static final int INITIAL_ANT_COUNT = 100; // ToDo: change back to 100
    public static final int INITIAL_OBSTACLE_COUNT = 30;


    // Evaluation Parameters
    public static final int EVALUATION_TIME = 60000;

    // Ant Parameters
    public static final int ANT_VIEW_DISTANCE = 20;
}
