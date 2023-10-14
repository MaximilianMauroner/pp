package src.controller;

import src.model.*;
import src.view.View;

import java.util.HashMap;

public class Game {
    private GameState gameState;
    private View view;
    private Status status;

    public Game(Status status) {
        this.status = status;
        this.view = new View(status.getWidth(), status.getHeight());
    }

    public void generate() {
        Entity ant = new Ant();
        Entity food = new Food();
        Entity obstacle = new Obstacle();
        Entity hive = new Hive();

        this.gameState = new GameState(new HashMap<>(), status);

        // generate hive position
        int hiveX = (int) (Math.random() * status.getWidth());
        int hiveY = (int) (Math.random() * status.getHeight());
        Position hivePosition = new Position(hiveX, hiveY, status);
        ClusterGenerator.generate(hive, hivePosition, status.getHiveSize(), gameState);

        // randomly spawn ants around hive
        int spawnRadius = status.getAntSpawnRadius();

        for (int i = 0; i < status.getAntCount(); i++) {
            int antX = calculatePosition(hiveX, spawnRadius);
            int antY = calculatePosition(hiveY, spawnRadius);
            Position antPosition = new Position(antX, antY, status);
            ClusterGenerator.generate(ant, antPosition, 1, gameState);
        }

        // randomly spawn food
        int hiveDistance = status.getFoodHiveDistance();  // minimum distance between hive and food

        for (int i = 0; i < status.getFoodCount(); i++) {
            int foodX = (int) (Math.random() * status.getWidth());
            int foodY = (int) (Math.random() * status.getHeight());
            Position foodPosition = new Position(foodX, foodY, status);
            ClusterGenerator.generate(food, foodPosition, status.getFoodSize(), gameState);
        }

        // randomly spawn obstacles
        int obstacleCount = (int) (Math.random() * status.getObstacleCount());
        for (int i = 0; i < obstacleCount; i++) {
            int obstacleX = (int) (Math.random() * status.getWidth());
            int obstacleY = (int) (Math.random() * status.getHeight());
            Position obstaclePosition = new Position(obstacleX, obstacleY, status);
            ClusterGenerator.generate(obstacle, obstaclePosition, status.getObstacleSize(), gameState);
        }
    }

    public void start() {
        GameplayLoop gameplayLoop = new GameplayLoop(view, gameState);
        gameplayLoop.start();
    }

    public void start(int duration) {

    }

    private int calculatePosition(int pos, int radius) {
        return (int) (Math.random() * radius * 2) - radius + pos;
    }

}
