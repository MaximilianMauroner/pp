public class SandClayFilling implements Filling {
    private final double weight;

    public SandClayFilling(double weight) {
        this.weight = weight;
    }

    @Override
    public double weight() {
        return weight;
    }

    @Override
    public double width() {
        return 0;
    }

    @Override
    public double height() {
        return 0;
    }
}
