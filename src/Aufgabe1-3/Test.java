import controller.GameState;
import controller.GameplayLoop;
import datastore.DataManager;
import controller.Game;
import datastore.Simulation;
import model.*;
import view.View;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/*
Distribution of work:
- Maximilian: Model(Position, Point, Status, Cluster Generator), Controller(GameState, GameLoop)
- Lukas: Model(Entities), Controller(Ant State Machine, Game generation), Optimal Path
- Christopher: View
 */

public class Test {

    public static void main(String[] args) {
        Status s = new Status(Parameters.WIDTH, Parameters.HEIGHT, Parameters.SCALE_BY, Parameters.EVALUATION_TIME,
                Parameters.INITIAL_ANT_COUNT, 20, 50, 10, Parameters.INITIAL_FOOD_COUNT,
                Parameters.INITIAL_OBSTACLE_COUNT, 50, 100, 0.98, 0.2, 0.7, 0.3);
        Game game = new Game(s);
        DataManager dataManager;

        for (int i = 0; i < Parameters.SIMULATION_RUNS; i++) {
            Simulation sim = new Simulation(UUID.randomUUID().toString(), "Simulation " + i);
            s.exportRandomParameters(sim);
            dataManager = DataManager.getInstance(sim);

            System.out.println("Game " + i + " starting");
            game.generate();
            game.start(Parameters.EVALUATION_TIME);

            System.out.println("Game " + i + " finished");
            dataManager.saveSimulation();
            s.randomize(0.3);
        }

        System.out.println("All games finished");
        System.out.println(DataManager.getInstance().toString());

    }
}


