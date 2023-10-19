import controller.Game;
import model.*;

/*
Distribution of work:
- Maximilian: Model(Position, Point, Status, Cluster Generator), Controller(GameState, GameLoop)
- Lukas: Model(Entities), Controller(Ant State Machine, Game generation)
- Christopher: View
 */

public class Test {

    public static void main(String[] args) {
        Status s = new Status(Parameters.WIDTH, Parameters.HEIGHT, Parameters.SCALE_BY, 6000,
                100, 20, 7,
                30, 50, 100, 0.9, 0.2, 0.7);
        Game game = new Game(s);

        for (int i = 0; i < Parameters.SIMULATION_RUNS; i++) {
            System.out.println("Game " + i + " starting");
            game.generate();
            game.start(s.getSimulationTimeLimit());

            System.out.println("Game " + i + " finished");
            s.randomize(0.3);
        }
    }
}


