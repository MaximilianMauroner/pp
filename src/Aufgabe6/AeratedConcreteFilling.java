public class AeratedConcreteFilling implements Filling {
    private final double width;
    private final double height;

    public AeratedConcreteFilling(double width, double height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public double weight() {
        return 0;
    }

    @Override
    public double width() {
        return width;
    }

    @Override
    public double height() {
        return height;
    }
}
