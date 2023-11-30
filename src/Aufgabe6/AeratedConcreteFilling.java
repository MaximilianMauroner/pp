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

    @Override
    @PostCondition(condition = "returns weight, always 0")
    @Author(name = "Lukas Leskovar")
    public double weight() {
        return 0;
    }

    @Override
    @PostCondition(condition = "returns width")
    @Author(name = "Lukas Leskovar")
    public double width() {
        return width;
    }

    @Override
    @PostCondition(condition = "returns height")
    @Author(name = "Lukas Leskovar")
    public double height() {
        return height;
    }

    @Override
    @PostCondition(condition = "return != null, returns the string representation of the filling")
    @Author(name = "Christopher Scherling")
    public String toString() {
        return "AeratedConcreteFilling{" + "width=" + width + ", height=" + height + '}';
    }
}
