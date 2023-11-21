public class AirConditionedNest implements Nest {
    private Filling filling;
    private final int id;
    private final double width;
    private final double height;
    private final double tankVolume;


    public AirConditionedNest(Filling filling, double width, double height, double tankVolume) {
        this.id = calcID();
        this.filling = filling;
        this.width = width;
        this.height = height;
        this.tankVolume = tankVolume;
    }

    @Override
    public int id() {
        return this.id;
    }

    @Override
    public double width() {
        return this.width;
    }

    @Override
    public double height() {
        return this.height;
    }

    public int getPower() {
        return 0;
    }

    public double getTankVolume() {
        return this.tankVolume;
    }

    @Override
    public Filling getFilling() {
        return this.filling;
    }

    @Override
    public void setFilling(Filling filling) {
        this.filling = filling;
    }

}
