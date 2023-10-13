package src.model;

public class Status {
    private final int width, height, antCount, antViewDistance, foodCount;
    private final double traildecay, highTrail;

    public Status(int width, int height, int antCount, int antViewDistance, int foodCount, double traildecay, double highTrail) {
        this.width = width;
        this.height = height;
        this.antCount = antCount;
        this.antViewDistance = antViewDistance;
        this.foodCount = foodCount;
        this.traildecay = traildecay;
        this.highTrail = highTrail;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getAntCount() {
        return antCount;
    }

    public int getAntViewDistance() {
        return antViewDistance;
    }

    public int getFoodCount() {
        return foodCount;
    }

    public double getTrailDecay() {
        return traildecay;
    }

    public double getHighTrail() {
        return highTrail;
    }

}
