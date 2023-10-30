package controller;

import model.Entity.*;
import model.*;
import view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;


/**
 * Class for the game
 * It generates the game state and starts the game loop
 * <p>
 * Modularization Units:
 * - Objects of the game, view, etc.
 * - Module for generating the game state
 * <p>
 * Abstraction: A simulation of the abstract concept of a game, which is a collection of entities that interact with each other
 */
public class Game {


    /**
     * Objects of the game
     */
    private GameState gameState;
    private final View view;
    private PathManager pathManager;

    /**
     * Status of the simulation. Actually accessed like a module containing variables
     */
    private final Status status;

    public Game(Status status) {
        this.status = status;
        this.view = new View(status.getWidth(), status.getHeight());
    }

    /**
     * Generates the game state with randomized parameters
     */
    public void generate() {
        Entity food = new Food();
        Entity obstacle = new Obstacle();
        List<Colony> colonies = new ArrayList<>();

        this.gameState = new GameState(new ConcurrentHashMap<>(), status);
        this.pathManager = new PathManager(gameState);
        Colony c = new Colony(this.gameState);

        // randomly spawn obstacles
        int obstacleCount = (int) (Math.random() * status.getObstacleCount());
        for (int i = 0; i < obstacleCount; i++) {
            int obstacleX = (int) (Math.random() * status.getWidth());
            int obstacleY = (int) (Math.random() * status.getHeight());
            Position obstaclePosition = new Position(obstacleX, obstacleY);
            ClusterGenerator.advancedObstacleGeneration(obstacle, obstaclePosition, Parameters.OBSTACLE_SIZE, gameState);
        }


        // STYLE 1/2: this uses a bit of applicative programming in the condition of the while loop
        // generate hive position
        List<Position> hivePositions = new ArrayList<>();

        for (int i = 0; i < Parameters.HIVE_COUNT; i++) {
            // loop over generated hive positions until there is no matching hive within distance
            var ref = new Object() {
                int hiveX;
                int hiveY;
            };

            do {
                ref.hiveX = (int) (Math.random() * status.getWidth());
                ref.hiveY = (int) (Math.random() * status.getHeight());
            } while (!hivePositions.isEmpty() &&
                    hivePositions.stream().anyMatch(
                            hivePosition -> Math.abs(hivePosition.getX() - ref.hiveX) < 2 * Parameters.HIVE_SIZE
                                    && Math.abs(hivePosition.getY() - ref.hiveY) < 2 * Parameters.HIVE_SIZE
                    )
            );
            Position hivePosition = new Position(ref.hiveX, ref.hiveY);
            Colony colony = new Colony(this.gameState);
            colonies.add(colony);
            Hive hive = new Hive(colony, hivePosition);
            hivePositions.add(hivePosition);
            ClusterGenerator.advancedHiveGeneration(hive, hivePosition, Parameters.HIVE_SIZE, gameState);
            pathManager.addStart(hivePosition);
        }


        // randomly spawn ants around hive
        int spawnRadius = status.getAntSpawnRadius();

        for (int i = 0; i < Parameters.HIVE_COUNT; i++) {
            for (int j = 0; j < status.getAntCount() / Parameters.HIVE_COUNT; j++) {
                int antX;
                int antY;
                Point point;
                do {
                    antY = calculatePosition(hivePositions.get(i).getX(), spawnRadius);
                    antX = calculatePosition(hivePositions.get(i).getX(), spawnRadius);
                    point = this.gameState.getPoint(new Position(antX, antY));
                } while (point != null && point.hasObstacle());
                Position antPosition = new Position(antX, antY);
                Entity ant = new Ant(AntState.EXPLORE, this.gameState, this.status, colonies.get(i), antPosition);
                ClusterGenerator.generate(ant, antPosition, 1, gameState);
            }
        }


        // STYLE 2/2: here too

        // randomly spawn food
        int hiveDistance = status.getFoodHiveDistance();  // minimum distance between hive and food

        for (int i = 0; i < status.getFoodCount(); i++) {
            // loop over generated food positions until there is no matching hive within distance
            var ref = new Object() {
                int foodX;
                int foodY;
            };
            do {
                ref.foodX = (int) (Math.random() * status.getWidth());
                ref.foodY = (int) (Math.random() * status.getHeight());
            } while (
                    hivePositions.stream().anyMatch(
                            hivePosition -> hivePosition.withinRadius(new Position(ref.foodX, ref.foodY), hiveDistance)
                    )
            );

            Position foodPosition = new Position(ref.foodX, ref.foodY);
            ClusterGenerator.advancedFoodSourceGeneration(food, foodPosition, Parameters.FOOD_SIZE, gameState);

            pathManager.registerNewPaths(foodPosition);
        }
    }

    /**
     * Starts the game without duration limit
     */
    public void start() {
        GameplayLoop gameplayLoop = new GameplayLoop(view, gameState);
        gameplayLoop.start();
    }


    /**
     * Starts the game with duration limit
     *
     * @param duration duration of the game in milliseconds
     */
    public void start(int duration) {
        GameplayLoop gameplayLoop = new GameplayLoop(view, gameState);
        gameplayLoop.start();

        // wait for duration
        try {
            Thread.sleep(duration);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        gameplayLoop.setRunning(false);
        pathManager.calculatePaths();
    }


    /**
     * Calculates a random position within a radius around a given position
     *
     * @param pos  position around which the random position is calculated
     * @param dist distance to the given position (only distance along one axis, not Euclidean distance)
     */
    private int calculatePosition(int pos, int dist) {
        return (int) (Math.random() * dist * 2) - dist + pos;
    }

}
