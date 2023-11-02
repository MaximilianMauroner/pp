package controller;

import model.Entity.*;
import model.*;
import view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;


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
        // STYLE: Here we use streams to generate random positions for the entities.
        // (I know minimum should be 100 lines, but they just shorten the code so much)

        Entity food = new Food();
        Entity obstacle = new Obstacle();
        List<Colony> colonies = new ArrayList<>();

        this.gameState = new GameState(new ConcurrentHashMap<>(), status);
        this.pathManager = new PathManager(gameState);
        Colony c = new Colony(this.gameState);

        // randomly spawn obstacles
        int obstacleCount = (int) (Math.random() * status.getObstacleCount());

        Stream.generate(() -> new Position((int) (Math.random() * status.getWidth()), (int) (Math.random() * status.getHeight())))
                .filter(position -> gameState.getPoint(position) == null || !gameState.getPoint(position).hasObstacle())
                .limit(status.getObstacleCount())
                .forEach(position -> ClusterGenerator.advancedObstacleGeneration(obstacle, position, Parameters.OBSTACLE_SIZE, gameState));


        // generate hive position
        List<Position> hivePositions = new ArrayList<>();

        for (int i = 0; i < Parameters.HIVE_COUNT; i++) {
            Stream.generate(() -> new Position((int) (Math.random() * status.getWidth()), (int) (Math.random() * status.getHeight())))
                .filter(hivePosition -> hivePositions.stream().noneMatch(
                        existingHivePosition -> Math.abs(existingHivePosition.getX() - hivePosition.getX()) < 2 * Parameters.HIVE_SIZE
                                && Math.abs(existingHivePosition.getY() - hivePosition.getY()) < 2 * Parameters.HIVE_SIZE
                ))
                .findFirst()
                .ifPresent(hivePosition -> {
                    Colony colony = new Colony(this.gameState);
                    colonies.add(colony);
                    Hive hive = new Hive(colony, hivePosition);
                    hivePositions.add(hivePosition);
                    ClusterGenerator.advancedHiveGeneration(hive, hivePosition, Parameters.HIVE_SIZE, gameState);
                    pathManager.addStart(hivePosition);
                });
        }


        // randomly spawn ants around hive
        int spawnRadius = status.getAntSpawnRadius();

        hivePositions.forEach(
            hivePosition -> {
                Stream.generate(() -> new Position(
                        calculatePosition(hivePosition.getX(), spawnRadius),
                        calculatePosition(hivePosition.getY(), spawnRadius)
                ))
                        .filter(position -> gameState.getPoint(position) == null || !gameState.getPoint(position).hasObstacle())
                        .limit(status.getAntCount() / Parameters.HIVE_COUNT)
                        .forEach(position -> {
                            Entity ant = new Ant(AntState.EXPLORE, this.gameState, this.status, colonies.get(hivePositions.indexOf(hivePosition)), position);
                            ClusterGenerator.generate(ant, position, 1, gameState);
                        });
            }
        );


        // randomly spawn food
        int hiveDistance = status.getFoodHiveDistance();  // minimum distance between hive and food

        Stream.generate(() -> new Position((int) (Math.random() * status.getWidth()), (int) (Math.random() * status.getHeight())))
                .filter(foodPosition -> hivePositions.stream().noneMatch(
                        hivePosition -> hivePosition.withinRadius(foodPosition, hiveDistance)
                ))
                .limit(status.getFoodCount())
                .forEach(foodPosition -> {
                    ClusterGenerator.advancedFoodSourceGeneration(food, foodPosition, Parameters.FOOD_SIZE, gameState);
                    pathManager.registerNewPaths(foodPosition);
                });
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
