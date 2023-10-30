package controller;

import view.View;

/**
 * The central element for running the simulation
 * <p>
 * Modularization Units:
 * - Objects for the game, view, etc.
 * - Component that runs the game logic and informs the view about changes
 * <p>
 * Abstraction: A simulation of the abstract concept of a game, (e.g. the collection of entities on a map, that interact with each other over time)
 */

// STYLE: this uses a bit of concurrent programming as the game loop runs in a separate thread.
// This has implications on the way some data is accessed, e.g. Points in GameState or Entities in Points are accessed in a thread-safe way.
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
        final int FRAMES_TIL_NEXT_TIME = 100;
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
