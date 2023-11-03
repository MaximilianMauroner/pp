package controller;

import model.Entity.Ant;
import model.Entity.Entity;
import model.Entity.Hive;
import model.Point;
import model.Position;

import java.util.List;
import java.util.Random;

// BAD (object oriented): Here we could've used dynamic binding to have a single method for advanced generation of all entities.

/**
 * Class for generating clusters of entities
 * generate function is accessed like a module
 * <p>
 * Modularization Units:
 * - Module for methods that generate clusters of entities
 * <p>
 * Abstraction: Not really in the slides but it just is a utility class for convenience during the generation of the simulation
 */
public class ClusterGenerator {

    /**
     * Generates a cluster of entities
     *
     * @param entity   the entity to be generated
     * @param position the position of the cluster
     * @param size     the size of the cluster
     * @param gs       the game state
     */
    public static void generate(Entity entity, Position position, int size, GameState gs) {
        for (int i = -(size / 2); i <= size / 2; i++) {
            for (int j = -(size / 2); j <= size / 2; j++) {
                setPosition(position.getX() + i, position.getY() + j, entity, gs);
            }
        }
    }


    // NOTE: the following methods implement an organic generation of entities

    /**
     * Generates a cluster of obstacles in a organic way
     * @param entity the entity to be generated (precondition: entity is an obstacle, entity != null)
     * @param position the center of the cluster (precondition: position != null)
     * @param size the size of the cluster
     * @param gs the game state
     */
    public static void advancedObstacleGeneration(Entity entity, Position position, int size, GameState gs) {
        Random random = new Random();
        int numberOfPoints = 1200;

        double stddev = (double) size / random.nextDouble(4, 8); // Standard deviation of the distribution

        if (random.nextBoolean()) {
            for (int i = 0; i < numberOfPoints; i++) {
                int x = -1;

                int r = random.nextInt(100);
                if (r <= 50) x = (int) Math.round(position.getX() - random.nextExponential() * stddev);
                if (r > 50) x = (int) Math.round(position.getX() + random.nextExponential() * stddev);

                int y = Math.round(position.getY() + random.nextFloat(-5, 5) * size / random.nextInt(6, 8));

                setPosition(x, y, entity, gs);
            }
        } else {
            for (int i = 0; i < numberOfPoints; i++) {
                int y = -1;

                int x = Math.round(position.getX() + random.nextFloat(-5, 5) * size / random.nextInt(6, 8));

                int r = random.nextInt(100);
                if (r <= 50) y = (int) Math.round(position.getY() - random.nextExponential() * stddev);
                if (r > 50) y = (int) Math.round(position.getY() + random.nextExponential() * stddev);


                setPosition(x, y, entity, gs);
            }
        }
    }


    /**
     * Generates a cluster of food sources in an organic way
     * @param entity the entity to be generated (precondition: entity is food, entity != null)
     * @param position the center of the cluster (precondition: position != null)
     * @param size the size of the cluster
     * @param gs the game state
     */
    public static void advancedFoodSourceGeneration(Entity entity, Position position, int size, GameState gs) {
        Random random = new Random();
        int numberOfPoints = 5000;

        double stddev = (double) size / random.nextInt(2, 6); // Standard deviation of the distribution

        for (int i = 0; i < numberOfPoints; i++) {
            int x;
            int y;

            int r = random.nextInt(100);
            if (r <= 30)
                x = (int) Math.round(position.getX() + random.nextGaussian() * stddev * random.nextDouble(1.5));
            else if (r <= 60)
                x = (int) Math.round(position.getX() + random.nextGaussian() * stddev * random.nextDouble(0.8));
            else x = (int) Math.round(position.getX() + random.nextGaussian() * stddev);


            r = random.nextInt(100);
            if (r <= 30)
                y = (int) Math.round(position.getY() + random.nextGaussian() * stddev * random.nextDouble(0.8));
            else if (r <= 60)
                y = (int) Math.round(position.getY() + random.nextGaussian() * stddev * random.nextDouble(1.5));
            else y = (int) Math.round(position.getY() + random.nextGaussian() * stddev);


            setPosition(x, y, entity, gs);
        }
    }


    /**
     * Generates a cluster of hives in an organic way
     * @param entity the entity to be generated (precondition: entity is a hive, entity != null)
     * @param position the center of the cluster (precondition: position != null)
     * @param size the size of the cluster
     * @param gs the game state
     */
    public static void advancedHiveGeneration(Entity entity, Position position, int size, GameState gs) {
        Random random = new Random();
        int numberOfPoints = 1200;

        double stddev = (double) size / 4; // Standard deviation of the distribution

        for (int i = 0; i < numberOfPoints; i++) {
            int x = (int) Math.round(position.getX() + random.nextGaussian() * stddev);
            int y = (int) Math.round(position.getY() + random.nextGaussian() * stddev);

            setPosition(x, y, entity, gs);
        }
    }

    /**
     * Writes an entity to a position in the game state
     * @param x x-coordinate of the position
     * @param y y-coordinate of the position
     * @param entity the entity to be written (precondition: entity != null)
     * @param gs the game state
     */
    private static void setPosition(int x, int y, Entity entity, GameState gs) {
        Position pos = new Position(x, y);
        Entity newE = entity.clone();
        Point p = gs.getPoint(pos);
        if (p == null) {
            p = new Point(pos, newE);
            gs.setPoint(p);
        } else {
            List<Entity> entityList = p.getEntities();
            for (Entity e : entityList) {
                if (e.getClass() == Ant.class) {
                    ((Ant) e).getColony().getAnts().remove(((Ant) e).getId());
                } else if (e.getClass() == Hive.class) {
                    ((Hive) e).getColony().getHives().remove(((Hive) e).getId());
                }
            }
            entityList.clear();
            p.getEntities().add(newE);
        }
        newE.setPosition(p.getPosition());
    }
}
