package src;

import src.controller.Game;
import src.model.*;

import java.awt.*;

public class StartProgramm {


    //The following settings are graphical ONLY, so that the individual entities are easier to see.
    //They do not affect the simulation.
    public static final int ANT_SIZE = 5;
    public static final int FOOD_SOURCE_SIZE = 9;
    public static final int COLONY_HOME_SIZE = 15;
    public static final int OBSTACLE_SIZE = 9;
    public static final int TRAIL_SIZE = 1;
    public static final int SCALE_BY = 1;

    public static final int WIDTH = 225 * SCALE_BY;
    public static final int HEIGHT = 225 * SCALE_BY;


    //Set color for entities
    public static final Color ANT_COLOR = Color.RED;
    public static final Color FOOD_SOURCE_COLOR = Color.GREEN;
    public static final Color COLONY_HOME_COLOR = new Color(184, 156, 80);
    public static final Color OBSTACLE_COLOR = Color.GRAY;
    public static final Color TRAIL_COLOR = Color.BLUE;

    public static void main(String[] args) {
        Status s = new Status(WIDTH, HEIGHT, SCALE_BY, 60000,
                9, 20, 10,
                5, 50, 100,
                20, 30, 10, 0.99, 0.2, 0.7);
//        View view = new View(s.getWidth(), s.getHeight());
//        Entity ant1 = new Ant();
//        Entity food = new Food();
//        Entity obstacle = new Obstacle();
//        Entity colony = new Hive();
//
//        GameState gs =  new GameState(new HashMap<>(), s);
//        ClusterGenerator.generate(ant1, new Position(100, 100, s), 5, gs);
//        ClusterGenerator.generate(food, new Position(150, 150, s), 3, gs);
//        ClusterGenerator.generate(obstacle, new Position(10, 10, s), 5, gs);
//        ClusterGenerator.generate(colony, new Position(50, 50, s), 10, gs);
//        System.out.println(gs.getPoints().size());
//
//
//        GameplayLoop gameplayLoop = new GameplayLoop(view, gs);
//        gameplayLoop.start();

        Game game = new Game(s);

        for (int i = 0; i < 3; i++) {
            System.out.println("Game " + i + " starting");
            game.generate();
            game.start(s.getSimulationTime());
            s.randomize(0.2);
        }
    }
}


