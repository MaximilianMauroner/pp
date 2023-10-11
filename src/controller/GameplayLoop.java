package src.controller;

import src.view.View;

public class GameplayLoop extends Thread {

    private View view;
    private GameState gameState;

    public GameplayLoop(View view, GameState gameState) {
        this.view = view;
        this.gameState = gameState;
    }

    private boolean isRunning = false;

    public void run() {
        isRunning = true;
        long now;
        long updateTime;
        long wait;

        final int TARGET_FPS = 6;
        final long OPTIMAL_TIME = 1000000000 / TARGET_FPS;

        while (isRunning) {
            now = System.nanoTime();
            GameState oldGameState = gameState.clone();
            gameState.getNextFrame();
            view.draw(oldGameState, gameState);

            updateTime = System.nanoTime() - now;
            wait = (OPTIMAL_TIME - updateTime) / 1000000;

            try {
                Thread.sleep(wait);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
