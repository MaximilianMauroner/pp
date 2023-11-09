package model;

import datastore.Simulation;

/**
 * Class for the status of the simulation
 * The status contains all the parameters that can be changed by the user
 * The status is used to create the simulation
 * <p>
 * Modularization Units:
 * - A module for storing, accessing and calculating mutable parameters and information of the simulation
 * <p>
 * Abstraction: Is a simulation of the real worlds (changeable and random) state which is perceivable by its entities
 */
public class Status {
    private final int width, height, scale, simulationTimeLimit, antCount, foodCount, obstacleCount;
    private final double minHiveHealth;
    private int antEmptySteps, antMoveSteps, antWaitSteps, foodHiveDistance, antSpawnRadius, simulationTime;
    private double trailDecay; // (invariant: 0 < trailDecay < 1)
    private double lowTrail;
    private double highTrail;
    private double searchRadiusGrowthFactor;

    public Status(int width, int height, int scale, int simulationTime, int antCount, int antEmptySteps, int antMoveSteps, int antWaitSteps, int foodCount,
                  int obstacleCount, int antSpawnRadius, int foodHiveDistance,
                  double trailDecay, double lowTrail, double highTrail, double searchRadiusFactor, double minHiveHealth) {
        this.width = width;
        this.height = height;
        this.scale = scale;
        this.simulationTimeLimit = simulationTime;
        this.antCount = antCount;
        this.antEmptySteps = antEmptySteps;
        this.antMoveSteps = antMoveSteps;
        this.antWaitSteps = antWaitSteps;
        this.foodCount = foodCount;
        this.obstacleCount = obstacleCount;
        this.antSpawnRadius = antSpawnRadius;
        this.foodHiveDistance = foodHiveDistance;
        this.trailDecay = trailDecay;
        this.lowTrail = lowTrail;
        this.highTrail = highTrail;
        this.searchRadiusGrowthFactor = searchRadiusFactor;
        this.minHiveHealth = minHiveHealth;
    }

    /**
     * @return the width of the simulation window
     */
    public int getWidth() {
        return width;
    }

    /**
     * @return the height of the simulation window
     */
    public int getHeight() {
        return height;
    }

    /**
     * @return the scaling of the simulation window
     */
    public int getScale() {
        return scale;
    }

    /**
     * @return the simulation time limit (e.g. how long the simulation will run in ms)
     */
    public int getSimulationTimeLimit() {
        return simulationTimeLimit;
    }

    /**
     * @return the number of ants in the simulation
     */
    public int getAntCount() {
        return antCount;
    }

    /**
     * @return the number of steps an ant will go on empty points until it changes mode
     */
    public int getAntEmptySteps() {
        return antEmptySteps;
    }

    /**
     * @return the number of steps an ant will move until it stops
     */
    public int getAntMoveSteps() {
        return antMoveSteps;
    }

    /**
     * @return the number of steps an ant will wait until it moves again
     */
    public int getAntWaitSteps() {
        return antWaitSteps;
    }

    /**
     * @return how many food clusters will be generated
     */
    public int getFoodCount() {
        return foodCount;
    }

    /**
     * @return how many obstacles will be generated
     */
    public int getObstacleCount() {
        return obstacleCount;
    }

    /**
     * @return the radius in which ants will spawn around the hive
     */
    public int getAntSpawnRadius() {
        return antSpawnRadius;
    }

    /**
     * @return the distance between food and hive
     */
    public int getFoodHiveDistance() {
        return foodHiveDistance;
    }

    /**
     * @return the decay of the trail
     */
    public double getTrailDecay() {
        return trailDecay;
    }

    /**
     * @return the lower bound of the trail
     */
    public double getLowTrail() {
        return lowTrail;
    }

    /**
     * @return the upper bound of the trail
     */
    public double getHighTrail() {
        return highTrail;
    }

    /**
     * @return the growth factor of the search radius
     */
    public double getSearchRadiusGrowthFactor() {
        return searchRadiusGrowthFactor;
    }

    /**
     * @return the current simulation time
     */
    public int getSimulationTime() {
        return this.simulationTime;
    }

    /**
     * @return the minimum health of the hive
     */
    public double getMinHiveHealth() {
        return minHiveHealth;
    }

    public void resetSimulationTime() {
        this.simulationTime = 0;
    }


    /**
     * Randomizes the values of parameters that affect game behavior
     *
     * @param confidence randomize values within interval of confidence in percent (precondition: confidence >= 0)
     *                   (e.g. if confidence is 0.1, then the value will be randomized within 10% of the original value)
     */
    public void randomize(double confidence) {
        this.antEmptySteps = (int) randomize(antEmptySteps, confidence, 1, 40);
        this.antMoveSteps = (int) randomize(antMoveSteps, confidence, 30, 80);
        this.antWaitSteps = (int) randomize(antWaitSteps, confidence, 10, 30);
        this.antSpawnRadius = (int) randomize(antSpawnRadius, confidence, 1, 50);
        this.foodHiveDistance = (int) randomize(foodHiveDistance, confidence, 1, 200);
        this.trailDecay = randomize(trailDecay, confidence, 0, 1);
        this.lowTrail = randomize(lowTrail, confidence, 0, 1);
        this.highTrail = randomize(highTrail, confidence, 0, 1);
        this.searchRadiusGrowthFactor = randomize(searchRadiusGrowthFactor, confidence, 1, 2);
    }

    /**
     * Exports the random parameters to the simulation. (precondition: simulation != null)
     *
     * @param simulation the current simulation object
     */
    public void exportRandomParameters(Simulation simulation) {
        simulation.addData("antEmptySteps", this.antEmptySteps);
        simulation.addData("antMoveSteps", this.antMoveSteps);
        simulation.addData("antWaitSteps", this.antWaitSteps);
        simulation.addData("antSpawnRadius", this.antSpawnRadius);
        simulation.addData("foodHiveDistance", this.foodHiveDistance);
        simulation.addData("trailDecay", this.trailDecay);
        simulation.addData("lowTrail", this.lowTrail);
        simulation.addData("highTrail", this.highTrail);
        simulation.addData("searchRadiusGrowthFactor", this.searchRadiusGrowthFactor);
    }

    /**
     * Increments the simulation time by one hour
     */
    public void nextTime() {
        System.out.println(this.simulationTime);
        this.simulationTime = (this.simulationTime + 1) % 24;
        System.out.println(this.simulationTime + " after");
    }

    /**
     * Randomizes of a value with a given confidence within a given interval
     *
     * @param value      value to be randomized
     * @param confidence randomize values within interval of confidence in percent (precondition: confidence >= 0)
     * @param lowerLimit lower limit of the randomized value
     * @param upperLimit upper limit of the randomized value
     *                   (precondition: lowerLimit <= upperLimit)
     */
    private double randomize(double value, double confidence, int lowerLimit, int upperLimit) {
        double result = value * (1 + (Math.random() * confidence * 2 - confidence));
        if (result > upperLimit) {
            return this.randomize(value, confidence, lowerLimit, upperLimit);
        } else if (result < lowerLimit) {
            return lowerLimit;
        }
        return result;
    }
}
