package src;

import src.controller.GameState;
import src.controller.GameplayLoop;
import src.model.*;
import src.model.Point;
import src.view.View;

import java.awt.*;
import java.util.List;

public class StartProgramm {
    public static final int SCALE_BY = 5;
    public static final int WIDTH = 225 * SCALE_BY;
    public static final int HEIGHT = 225 * SCALE_BY;

    //The following settings are graphical ONLY, so that the individual entities are easier to see.
    //They do not affect the simulation.
//    public static final int ANT_SIZE = 7;
//    public static final int FOOD_SOURCE_SIZE = 20;
//    public static final int COLONY_HOME_SIZE = 50;
//    public static final int OBSTACLE_SIZE = 5;
//    public static final int TRAIL_SIZE = 5;

    //Set color for entities
    public static final Color ANT_COLOR = Color.RED;
    public static final Color FOOD_SOURCE_COLOR = Color.GREEN;
    public static final Color COLONY_HOME_COLOR = new Color(184, 156, 80);
    public static final Color OBSTACLE_COLOR = Color.WHITE;


    public static void main(String[] args) {
        Status s = new Status(WIDTH, HEIGHT, 10, 10, 100, 0.1);
        View view = new View(s.getWidth(), s.getHeight());


        Entity ant1 = new Ant();
        Entity ant2 = new Ant();
        Entity ant3 = new Ant();

        Entity food = new Food();
        Entity obstacle = new Obstacle();
        Entity colony = new Hive();

        Entity trail1 = new Trail();

        Position position1 = new Position(10, 10, s);
        Position position2 = new Position(20, 20, s);
        Position position3 = new Position(30, 30, s);
        Position position4 = new Position(40, 40, s);
        Position position5 = new Position(30, 30, s);
        Position position6 = new Position(25, 30, s);
        Position position7 = new Position(100, 100, s);


        GameState gs = new GameState(List.of(
                new Point(position1, new Entity[]{ant1}),
                new Point(position2, new Entity[]{ant2}),
                new Point(position3, new Entity[]{ant3}),
                new Point(position4, new Entity[]{food}),
                new Point(position5, new Entity[]{obstacle}),
                new Point(position6, new Entity[]{trail1}),
                new Point(position7, new Entity[]{colony})
        ), s);

        GameplayLoop gameplayLoop = new GameplayLoop(view, gs);
        gameplayLoop.start();
    }
}


