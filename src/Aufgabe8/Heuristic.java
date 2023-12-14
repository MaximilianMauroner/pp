import java.util.List;
import java.util.function.BiFunction;

public interface Heuristic extends BiFunction<Graph, List<Integer>, Double> {
}
