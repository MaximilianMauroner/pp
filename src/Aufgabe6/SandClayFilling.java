import Annotations.Author;
import Annotations.PostCondition;
import Annotations.PreCondition;

@Author(name = "Lukas Leskovar")
public class SandClayFilling implements Filling {
    private final double weight;

    @PreCondition(condition = "weight > 0")
    @PostCondition(condition = "this.weight() == weight")
    @Author(name = "Lukas Leskovar")
    public SandClayFilling(double weight) {
        this.weight = weight;
    }

    @PostCondition(condition = "returns weight of the filling since it is sand-clay")
    @Author(name = "Lukas Leskovar")
    @Override
    public double weight() {
        return weight;
    }

    @PostCondition(condition = "returns 0 because its not concrete")
    @Author(name = "Lukas Leskovar")
    @Override
    public double width() {
        return 0;
    }

    @PostCondition(condition = "returns 0 because its not concrete")
    @Author(name = "Lukas Leskovar")
    @Override
    public double height() {
        return 0;
    }

    @Override
    public String toString() {
        return "SandClayFilling{" +
                "weight=" + weight +
                '}';
    }
}
