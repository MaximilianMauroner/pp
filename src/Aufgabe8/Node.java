import java.util.function.BiFunction;
import java.util.function.Function;

public record Node(int x, int y) {
    public Integer distance(Node other, Metric metric) {
        return metric.apply(this, other);
    }
}
