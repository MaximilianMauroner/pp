import Annotations.Author;
import Annotations.PostCondition;
import Annotations.PreCondition;


@Author(name = "Lukas Leskovar")
public class AeratedConcreteFilling implements Filling {
    private final double width;
    private final double height;

    @PreCondition(condition = "width > 0 && height > 0")
    @PostCondition(condition = "this.width() == width && this.height() == height")
    @Author(name = "Lukas Leskovar")
    public AeratedConcreteFilling(double width, double height) {
        this.width = width;
        this.height = height;
    }

    @PostCondition(condition = "returns 0 since its not sand-clay")
    @Author(name = "Lukas Leskovar")
    @Override
    public double weight() {
        return 0;
    }

    @PostCondition(condition = "returns width of the filling since it is concrete")
    @Author(name = "Lukas Leskovar")
    @Override
    public double width() {
        return width;
    }

    @PostCondition(condition = "returns height of the filling since it is concrete")
    @Author(name = "Lukas Leskovar")
    @Override
    public double height() {
        return height;
    }

    @PostCondition(condition = "returns the string representation of the filling")
    @Author(name = "Christopher Scherling")
    @Override
    public String toString() {
        return "AeratedConcreteFilling{" + "width=" + width + ", height=" + height + '}';
    }
}
