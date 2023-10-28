package controller;

import view.View;

public class GameplayLoop extends Thread {

    /**
     * The view and game state objects
     */
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
        final int FRAMES_TIL_NEXT_TIME = 1000;
        final long OPTIMAL_TIME = 1000000000 / TARGET_FPS;
        int frames = 0;

        while (isRunning) {
            now = System.nanoTime();
            gameState.getNextFrame();
            view.draw(gameState);
            frames++;
            if (frames == FRAMES_TIL_NEXT_TIME) {
                frames = 0;
                gameState.getStatus().nextTime();
            }
//            AtomicInteger count = new AtomicInteger();
//            gameState.getPoints().forEach((position, point) ->
//                    count.addAndGet((int) point.getEntities().stream().filter(entity -> entity instanceof Ant).count())
//            );
//            System.out.println("Ants: " + count.get());

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
     *
     * @param running true if the game loop should run, false if the game loop should stop
     */
    public void setRunning(boolean running) {
        isRunning = running;
    }


}
