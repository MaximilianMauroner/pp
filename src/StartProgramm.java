package src;

import src.controller.GameState;
import src.controller.GameplayLoop;
import src.model.*;
import src.model.Point;
import src.view.View;

import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StartProgramm {


    //The following settings are graphical ONLY, so that the individual entities are easier to see.
    //They do not affect the simulation.
    public static final int ANT_SIZE = 1;
    public static final int FOOD_SOURCE_SIZE = 1;
    public static final int COLONY_HOME_SIZE = 1;
    public static final int OBSTACLE_SIZE = 1;
    public static final int TRAIL_SIZE = 1;
    public static final int SCALE_BY = 1;

    public static final int WIDTH = 225 * SCALE_BY;
    public static final int HEIGHT = 225 * SCALE_BY;


    //Set color for entities
    public static final Color ANT_COLOR = Color.RED;
    public static final Color FOOD_SOURCE_COLOR = Color.GREEN;
    public static final Color COLONY_HOME_COLOR = new Color(184, 156, 80);
    public static final Color OBSTACLE_COLOR = Color.WHITE;
    public static final Color TRAIL_COLOR = Color.BLUE;

    public static void main(String[] args) {
        Status s = new Status(WIDTH, HEIGHT, 10, 10,10, 100, 0.99, 0.2, 0.7);
        View view = new View(s.getWidth(), s.getHeight());


        Entity ant1 = new Ant();
        Entity ant2 = new Ant();
        Entity ant3 = new Ant();
        Entity ant4 = new Ant();

        Entity food = new Food();
        Entity obstacle = new Obstacle();
        Entity colony = new Hive();

        Entity trail1 = new Trail();

        Position position1 = new Position(10, 10, s);
        Position position2 = new Position(20, 20, s);
        Position position3 = new Position(30, 30, s);
        Position position4 = new Position(140, 140, s);
        Position position5 = new Position(100, 200, s);
        Position position6 = new Position(195, 30, s);
        Position position7 = new Position(220, 220, s);
        Position position8 = new Position(141, 139, s);


        GameState gs = new GameState(
                new ArrayList<>(List.of(
                        new Point(position1, new ArrayList<>(List.of(ant1))),
                        new Point(position2, new ArrayList<>(List.of(ant2))),
                        new Point(position3, new ArrayList<>(List.of(ant3))),
                        new Point(position8, new ArrayList<>(List.of(ant4))),
                        new Point(position4, new ArrayList<>(List.of(food))),
                        new Point(position5, new ArrayList<>(List.of(obstacle))),
                        new Point(position6, new ArrayList<>(List.of(trail1))),
                        new Point(position7, new ArrayList<>(List.of(colony)))
                )), s
        );

        GameplayLoop gameplayLoop = new GameplayLoop(view, gs);
        gameplayLoop.start();
    }
}


