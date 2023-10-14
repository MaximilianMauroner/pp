package src.model;

public class Status {
    private final int width, height, scale, simulationTime, antCount, antEmptySteps,
            foodCount, obstacleCount, antSpawnRadius, foodHiveDistance, foodSize, hiveSize, obstacleSize;

    private final double traildecay, lowTrail, highTrail;

    public Status(int width, int height, int scale, int simulationTime, int antCount, int antEmptySteps, int foodCount,
                  int obstacleCount, int antSpawnRadius, int foodHiveDistance, int foodSize, int hiveSize, int obstacleSize,
                  double traildecay, double lowTrail, double highTrail) {
        this.width = width;
        this.height = height;
        this.scale = scale;
        this.simulationTime = simulationTime;
        this.antCount = antCount;
        this.antEmptySteps = antEmptySteps;
        this.foodCount = foodCount;
        this.obstacleCount = obstacleCount;
        this.antSpawnRadius = antSpawnRadius;
        this.foodHiveDistance = foodHiveDistance;
        this.foodSize = foodSize;
        this.hiveSize = hiveSize;
        this.obstacleSize = obstacleSize;
        this.traildecay = traildecay;
        this.lowTrail = lowTrail;
        this.highTrail = highTrail;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getScale() {
        return scale;
    }

    public int getSimulationTime() {
        return simulationTime;
    }

    public int getAntCount() {
        return antCount;
    }

    public int getAntEmptySteps() {
        return antEmptySteps;
    }

    public int getFoodCount() {
        return foodCount;
    }

    public int getObstacleCount() {
        return obstacleCount;
    }

    public int getAntSpawnRadius() {
        return antSpawnRadius;
    }

    public int getFoodHiveDistance() {
        return foodHiveDistance;
    }

    public int getFoodSize() {
        return foodSize;
    }

    public int getHiveSize() {
        return hiveSize;
    }

    public int getObstacleSize() {
        return obstacleSize;
    }

    public double getTrailDecay() {
        return traildecay;
    }

    public double getLowTrail() {
        return lowTrail;
    }

    public double getHighTrail() {
        return highTrail;
    }

}
