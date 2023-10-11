package src;

import src.controller.GameState;
import src.controller.GameplayLoop;
import src.model.*;
import src.model.Point;
import src.view.View;

import java.awt.*;
import java.util.List;

public class StartProgramm {
    public static final int WIDTH = 1000;
    public static final int HEIGHT = 1000;

    //The following settings are graphical ONLY, so that the individual entities are easier to see.
    //They do not affect the simulation.
    public static final int ANT_SIZE = 10;
    public static final int FOOD_SOURCE_SIZE = 10;
    public static final int COLONY_HOME_SIZE = 5;
    public static final int OBSTACLE_SIZE = 5;
    public static final int SMELL_SIZE = 5;

    //Set color for entities
    public static final Color ANT_COLOR = Color.RED;
    public static final Color FOOD_SOURCE_COLOR = Color.GREEN;
    public static final Color COLONY_HOME_COLOR = new Color(184, 156, 80);
    public static final Color OBSTACLE_COLOR = Color.WHITE;


    public static void main(String[] args) {
        System.out.println("Hello World!");
        Status s = new Status(1000, 1000, 100, 10, 100, 0.1);
        View view = new View(s.getWidth(), s.getHeight());



        Entity ant1 = new Ant();
        Entity ant2 = new Ant();
        Entity ant3 = new Ant();
        Entity ant4 = new Ant();

        Position position1 = new Position(10, 10);
        Position position2 = new Position(20, 20);
        Position position3 = new Position(300, 30);
        Position position4 = new Position(400, 400);


        GameState gs = new GameState(List.of(
                new Point(position1, new Entity[]{ant1}),
                new Point(position2, new Entity[]{ant2}),
                new Point(position3, new Entity[]{ant3}),
                new Point(position4, new Entity[]{ant4})
        ), s);

        GameplayLoop gameplayLoop = new GameplayLoop(view, gs);
        gameplayLoop.start();
    }
}


