package src.controller;

import src.view.View;

public class GameplayLoop extends Thread {

    private final View view;
    private final GameState gameState;
    private boolean isRunning = false;
    public GameplayLoop(View view, GameState gameState) {
        this.view = view;
        this.gameState = gameState;
    }

    /**
     * Starts the game loop. The game loop will run until isRunning is set to false.
     */
    public void run() {
        isRunning = true;
        long now;
        long updateTime;
        long wait;

        final int TARGET_FPS = 60;
        final long OPTIMAL_TIME = 1000000000 / TARGET_FPS;

        while (isRunning) {
            now = System.nanoTime();
            gameState.getNextFrame();
            view.draw(gameState);

            updateTime = System.nanoTime() - now;
            wait = (OPTIMAL_TIME - updateTime) / 1000000;

            try {
                if (wait > 0) {
                    Thread.sleep(wait);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * Sets the running state of the game loop.
     * @param running true if the game loop should run, false if the game loop should stop
     */
    public void setRunning(boolean running) {
        isRunning = running;
    }


}
