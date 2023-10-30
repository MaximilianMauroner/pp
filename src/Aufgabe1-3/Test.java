import datastore.DataManager;
import controller.Game;
import datastore.Simulation;
import model.*;

import java.util.UUID;

/*
Distribution of work:
- Maximilian: Model(Position, Point, Status, Cluster Generator), Controller(GameState, GameLoop)
- Lukas: Model(Entities), Controller(Ant State Machine, Game generation), Optimal Path, Datastore, Ant Logic additions, Multiple Colony support
- Christopher: View


Features (not done):
- event loop (not needed)
- statistics end screen (low priority)
- food object spawn/de-spawn (low priority)
- trail influence (low priority / never really planned)

Features (done):
- Infinite size grid
- Hive decay
- Multiple colonies
- Simulation Metrics and optimal path calculation
- Overhauled ant logic
- Ants can fight each other & die
- Day/Night cycle
- efficient view
- entity view priority
- ... and more little changes

Structure:
For every simulation run, parameters get randomized and new gamefield is generated.
All objects (Ants, Food, Hive, etc.) are subclasses of Entity and are stored in Points on a sparse grid.
GameState contains all points and the logic for manipulation of the gamefield.
The Gameloop runs in a separate thread and calls the GameState to update which in turn calls the entities run method
(some of which actually interact with other entities while some do nothing).
Besides the main simulation function, a path finding algorithm is implemented as a benchmark for the ants random path finding.
The results of the simulation are stored in a Simulation object which is then stored in a DataManager object, to compare multiple simulation runs.
 */

public class Test {
    /**
     * Aufgabenverteilung:
     * ### Maxi
     * - Infinite Positions
     * - Beschränkung im Point entfernen
     * - Hive decay
     * - nach bestimmter Anzahl an Ameisen
     * - wenn Ameisen Futter bringen wird neues Hive Objekt generiert
     * - new Trails Logic
     * - trail for colony and stuff
     * - handle multiple colonies for trail
     * - Fix bau nicht essen
     * - Ants vereinigen fix Maxi
     * - wenn 2 Ameisen aufeinander treffen, werden sie zu einer
     * <p>
     * ### Lukas
     * - Simulation Metrik
     * - Optimale Pfade berechnen A*
     * - Abweichung von Pfaden berechnen
     * - Speichern von Daten
     * - Ant movement
     * - Ants move for a few steps
     * - then check for odour
     * - then move again
     * - needs view of ant
     * - add entity priorities
     * - maybe needs compareTo() function
     * - Ants fight each other (maybe); call antDies() in View
     * - wenn zwei von unterschiedlichen Kolonien aufeinander treffen, kämpfen sie
     * - Ant Objekt entfernen Corpse spawnen
     * - View hat Observer
     * - Test Cases (simulation)
     * - Paradigmen erläutern (min 5x)
     * - comment explaining the entire process
     * <p>
     * ### Chris
     * - Tageszeit simulation~~
     * - Game-State Clock abfragen "gameState.getTime();"
     * - Hintergrund auf Basis der Zeit verändern
     * - Statistik End Screen
     * - Effiziente Darstellung in View
     * - Entity View Priorities
     * - Verschiedene Entities haben verschiedene Priorities
     * - Mit höherer Priority werden vor niedriger Priority dargestellt
     * - Explosion when ant dies
     * - Class Corpse implements Entity
     * - generation organisch
     * - Generate Function ändern
     */
    public static void main(String[] args) {
        Status s = new Status(Parameters.WIDTH, Parameters.HEIGHT, Parameters.SCALE_BY, Parameters.EVALUATION_TIME,
                Parameters.INITIAL_ANT_COUNT, 20, 50, 10, Parameters.INITIAL_FOOD_COUNT,
                Parameters.INITIAL_OBSTACLE_COUNT, 50, 100, 0.98, 0.2, 0.7, 0.3, 0.1);
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
