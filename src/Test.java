package src;

import src.controller.Game;
import src.controller.GameState;
import src.model.*;
import src.model.Point;

import java.awt.*;

/*
Distribution of work:
- Maximilian: Model(Position, Point, Status, Cluster Generator), Controller(GameState, GameLoop)
- Lukas: Model(Entities), Controller(Ant State Machine, Game generation)
- Christopher: View
 */

public class Test {
    public static final int SCALE_BY = 4;

    public static final int WIDTH = 225;
    public static final int HEIGHT = 225;


    //Set color for entities
    public static final Color ANT_DEFAULT_COLOR = Color.RED;
    public static final Color ANT_SEARCH_COLOR = Color.YELLOW;
    public static final Color ANT_RETRIVE_COLOR = Color.WHITE;
    public static final Color FOOD_SOURCE_COLOR = Color.GREEN;
    public static final Color COLONY_HOME_COLOR = new Color(184, 156, 80);
    public static final Color OBSTACLE_COLOR = Color.GRAY;
    public static final Color TRAIL_COLOR = Color.BLUE;

    public static void main(String[] args) {
        Status s = new Status(WIDTH, HEIGHT, SCALE_BY, 60000,
                100, 20, 7,
                30, 50, 100,
                10, 20, 8, 0.9, 0.2, 0.7);
        Game game = new Game(s);

        for (int i = 0; i < 3; i++) {
            System.out.println("Game " + i + " starting");
            game.generate();
            game.start(s.getSimulationTime());

            System.out.println("Game " + i + " finished");
            s.randomize(0.3);
        }
    }
}


