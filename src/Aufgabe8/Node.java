import java.util.function.BiFunction;
import java.util.function.Function;

public record Node(int x, int y) {
    public <T> T distance(Node other, BiFunction<Node, Node, T> metric) {
        return metric.apply(this, other);
    }
}
