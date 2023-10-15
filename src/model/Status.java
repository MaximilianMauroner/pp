package src.model;

public class Status {
    private final int width, height, scale, simulationTime, antCount, foodCount, obstacleCount, foodSize, hiveSize, obstacleSize;
    private int antEmptySteps,foodHiveDistance, antSpawnRadius;
    private double trailDecay, lowTrail, highTrail;

    public Status(int width, int height, int scale, int simulationTime, int antCount, int antEmptySteps, int foodCount,
                  int obstacleCount, int antSpawnRadius, int foodHiveDistance, int foodSize, int hiveSize, int obstacleSize,
                  double trailDecay, double lowTrail, double highTrail) {
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
        this.trailDecay = trailDecay;
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
        return trailDecay;
    }

    public double getLowTrail() {
        return lowTrail;
    }

    public double getHighTrail() {
        return highTrail;
    }


    public void randomize(double confidence) {
        // randomize values within interval of confidence in percent
        // e.g. if confidence is 0.1, then the value will be randomized within 10% of the original value
        this.antEmptySteps = (int) randomize(antEmptySteps, confidence,1,40);
        this.antSpawnRadius = (int) randomize(antSpawnRadius, confidence, 1,50);
        this.foodHiveDistance = (int) randomize(foodHiveDistance, confidence,1,200);
        this.trailDecay = randomize(trailDecay, confidence, 0,1);
        this.lowTrail = randomize(lowTrail, confidence,0,1);
        this.highTrail = randomize(highTrail, confidence,0,1);
    }

    private double randomize(double value, double confidence, int lowerLimit, int upperLimit) {
        double result =value * (1 + (Math.random() * confidence * 2 - confidence));
        if(result > upperLimit){
            this.randomize(value, confidence, lowerLimit, upperLimit);
        }else if(result < lowerLimit){
            result = lowerLimit;
        }
        return result;
    }
}
