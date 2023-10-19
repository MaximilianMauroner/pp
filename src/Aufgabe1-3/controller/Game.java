package controller;

import model.Status;
import model.*;
import view.View;

import java.util.concurrent.ConcurrentHashMap;


/**
 * Class for the game
 * It generates the game state and starts the game loop
 */
public class Game {

    /**
     * Objects of the game
     */
    private GameState gameState;
    private View view;
    private PathManager pathManager;

    /**
     * Status of the simulation. Actually accessed like a module containing variables
     */
    private Status status;

    public Game(Status status) {
        this.status = status;
        this.view = new View(status.getWidth(), status.getHeight());
    }

    /**
     * Generates the game state with randomized parameters
     */
    public void generate() {
        Entity ant = new Ant();
        Entity food = new Food();
        Entity obstacle = new Obstacle();
        Entity hive = new Hive();

        this.gameState = new GameState(new ConcurrentHashMap<>(), status);
        this.pathManager = PathManager.getInstance(this.gameState);
        this.pathManager.clear();

        // randomly spawn obstacles
        int obstacleCount = (int) (Math.random() * status.getObstacleCount());
        for (int i = 0; i < obstacleCount; i++) {
            int obstacleX = (int) (Math.random() * status.getWidth());
            int obstacleY = (int) (Math.random() * status.getHeight());
            Position obstaclePosition = new Position(obstacleX, obstacleY);
            ClusterGenerator.advancedObstacleGeneration(obstacle, obstaclePosition, Parameters.OBSTACLE_SIZE, gameState);
        }

        // generate hive position
        int hiveX = (int) (Math.random() * status.getWidth());
        int hiveY = (int) (Math.random() * status.getHeight());
        Position hivePosition = new Position(hiveX, hiveY);
        ClusterGenerator.advancedHiveGeneration(hive, hivePosition, Parameters.HIVE_SIZE, gameState);
        pathManager.addStart(hivePosition);

        // randomly spawn ants around hive
        int spawnRadius = status.getAntSpawnRadius();

        for (int i = 0; i < status.getAntCount(); i++) {
            int antX = calculatePosition(hiveX, spawnRadius);
            int antY = calculatePosition(hiveY, spawnRadius);
            Position antPosition = new Position(antX, antY);
            ClusterGenerator.generate(ant, antPosition, 1, gameState);
        }


        // randomly spawn food
        int hiveDistance = status.getFoodHiveDistance();  // minimum distance between hive and food

        for (int i = 0; i < status.getFoodCount(); i++) {
            int foodX;
            int foodY;
            do {
                foodX = (int) (Math.random() * status.getWidth());
                foodY = (int) (Math.random() * status.getHeight());
            } while (Math.abs(foodX - hiveX) < hiveDistance && Math.abs(foodY - hiveY) < hiveDistance);

            Position foodPosition = new Position(foodX, foodY);
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
    }


    /**
     * Calculates a random position within a radius around a given position
     * @param pos position around which the random position is calculated
     * @param dist distance to the given position (only distance along one axis, not euclidean distance)
     */
    private int calculatePosition(int pos, int dist) {
        return (int) (Math.random() * dist * 2) - dist + pos;
    }

}
