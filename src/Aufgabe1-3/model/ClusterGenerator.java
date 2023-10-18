package model;

import controller.GameState;

import java.util.Random;

/**
 * Class for generating clusters of entities
 * generate function is accessed like a module
 */
public class ClusterGenerator {

    public static void generate(Entity entity, Position position, int size, GameState gs) {
        for (int i = -(size / 2); i <= size / 2; i++) {
            for (int j = -(size / 2); j <= size / 2; j++) {
                setPosition(position.getX() + i, position.getY() + j, entity, gs);
            }
        }
    }

    public static void advancedObstacleGeneration(Entity entity, Position position, int size, GameState gs) {
        Random random = new Random();
        int numberOfPoints = 1200;

        double stddev = (double) size / random.nextDouble(4,8); // Standard deviation of the distribution

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


    public static void advancedFoodSourceGeneration(Entity entity, Position position, int size, GameState gs) {
        Random random = new Random();
        int numberOfPoints = 5000;

        double stddev = (double) size / random.nextInt(2, 6); // Standard deviation of the distribution

        for (int i = 0; i < numberOfPoints; i++) {
            int x = -1;
            int y;

            int r = random.nextInt(100);
            if (r <= 30) x = (int) Math.round(position.getX() + random.nextGaussian() * stddev * random.nextDouble(1.5));
            else if (r <= 60) x = (int) Math.round(position.getX() + random.nextGaussian() * stddev * random.nextDouble(0.8));
            else x = (int) Math.round(position.getX() + random.nextGaussian() * stddev);


            r = random.nextInt(100);
            if (r <= 30) y = (int) Math.round(position.getY() + random.nextGaussian() * stddev * random.nextDouble(0.8));
            else if (r <= 60) y = (int) Math.round(position.getY() + random.nextGaussian() * stddev * random.nextDouble(1.5));
            else y = (int) Math.round(position.getY() + random.nextGaussian() * stddev);


            setPosition(x, y, entity, gs);
        }
    }


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

    private static void setPosition(int x, int y, Entity entity, GameState gs) {
        Position pos = new Position(x, y, gs.getStatus());
        Point p = gs.getPoint(pos);
        if (p == null) {
            p = new Point(pos, entity.clone());
            gs.setPoint(p);
        } else {
            p.getEntities().clear();
            p.getEntities().add(entity.clone());
        }
    }
}
