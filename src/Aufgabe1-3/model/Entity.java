package model;

import controller.GameState;

/**
 * Interface for the entities of the game
 * Entities are the objects that are placed on the grid
 * They can be ants, food, trails, etc.
 */
public interface Entity {

    /**
     * Runs the entity's logic
     *
     * @param gameState the game state of the game
     * @param status    the status of the game
     * @param point     the point where the entity is located
     */
    void run(GameState gameState, Status status, Point point);


    Entity clone();

    int getPriority();
}
