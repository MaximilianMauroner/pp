public class AirConditionedNest implements Nest {
    private Filling filling;
    private final int id;
    private final double width;
    private final double height;
    private final double tankVolume;


    public AirConditionedNest(int id, double width, double height, double tankVolume) {
        if (!checkID(id)) {
            throw new IllegalArgumentException("ID already exists");
        }
        this.id = id;
        this.width = width;
        this.height = height;
        this.tankVolume = tankVolume;
        Nest.nests.add(this);
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

    @Override
    public int getPower() {
        return 0;
    }

    @Override
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

    @Override
    public String toString() {
        return "AirConditionedNest{" +
                "filling=" + filling +
                ", id=" + id +
                ", width=" + width +
                ", height=" + height +
                ", depth=" + depth +
                ", tankVolume=" + tankVolume +
                '}';
    }
}
