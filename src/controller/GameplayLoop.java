package src.controller;

import src.view.View;

public class GameplayLoop extends Thread {

    private final View view;
    private final GameState gameState;

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

        final int TARGET_FPS = 30;
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

    public void setRunning(boolean running) {
        isRunning = running;
    }
}
