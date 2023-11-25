package controller;

import codedraw.CodeDraw;
import model.Entity.Entity;
import model.Point;
import view.View;

import java.awt.*;
import java.util.Iterator;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

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
public class GameplayLoop implements Runnable {

    /**
     * The view and game state objects
     */
    private final GameState gameState;
    private boolean isRunning = false;

    private BlockingQueue<BufferElement> queue;
    private CodeDraw cd;

    /**
     * Creates a new GameplayLoop object.
     *
     * @param gameState the game state object (not null)
     * @param queue     the queue that is used to communicate changes to the view (not null)
     * @param cd        the CodeDraw object that is used to draw the game (not null)
     */
    public GameplayLoop(GameState gameState, BlockingQueue<BufferElement> queue, CodeDraw cd) {
        this.gameState = gameState;
        this.queue = queue;
        this.cd = cd;
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
            View.changeTime(cd, gameState);
            gameState.getNextFrame(queue);
            cd.show();


            frames++;
            if (frames == FRAMES_TIL_NEXT_TIME) {
                frames = 0;
                gameState.getStatus().nextTime();
            }

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
