import java.util.function.BiFunction;

public class Metric implements BiFunction<Node, Node, Integer> {
    @Override
    public Integer apply(Node a, Node b) {
        return Math.abs(a.x() - b.x()) + Math.abs(a.y() - b.y());
    }
}
