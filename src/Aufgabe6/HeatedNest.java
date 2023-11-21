public class HeatedNest implements Nest {

    private Filling filling;
    private final int id;
    private final double width;
    private final double height;
    private final int power;

    public HeatedNest(Filling filling, double width, double height, int power) {
        this.id = calcID();
        this.filling = filling;
        this.width = width;
        this.height = height;
        this.power = power;
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
        return this.power;
    }

    public double getTankVolume() {
        return 0;
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
