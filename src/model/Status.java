package src.model;

public class Status {
    private final int width, height, antCount, antEmptySteps, antViewDistance, foodCount;
    private final double traildecay, lowTrail, highTrail;

    public Status(int width, int height, int antCount, int antEmptySteps, int antViewDistance, int foodCount, double traildecay,
                  double lowTrail, double highTrail) {
        this.width = width;
        this.height = height;
        this.antCount = antCount;
        this.antEmptySteps = antEmptySteps;
        this.antViewDistance = antViewDistance;
        this.foodCount = foodCount;
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

    public int getAntCount() {
        return antCount;
    }

    public int getAntEmptySteps() {
        return antEmptySteps;
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

    public double getLowTrail() {
        return lowTrail;
    }

    public double getHighTrail() {
        return highTrail;
    }

}
