public class HeatedNest implements Nest {

    private Filling filling;
    private final int id;
    private final double width;
    private final double height;
    private final int power;

    public HeatedNest(int id, double width, double height, int power) {
        if (!checkID(id)) {
            throw new IllegalArgumentException("ID already exists");
        }
        this.id = id;
        this.width = width;
        this.height = height;
        this.power = power;
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
        return this.power;
    }

    @Override
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

    @Override
    public String toString() {
        return "HeatedNest{" +
                "filling=" + filling +
                ", id=" + id +
                ", width=" + width +
                ", height=" + height +
                ", depth=" + depth +
                ", tankVolume=0" +
                ", power=" + power +
                '}';
    }
}
