import java.util.function.BiFunction;

public interface Metric<T> extends BiFunction<Node, Node, T> {
}
