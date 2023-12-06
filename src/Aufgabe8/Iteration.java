import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public record Iteration(Map<Node, List<Node>> adjacency) {

    public final List<Distance> distances;

    private <T> List<T> fill() {
        List<T> list = new ArrayList<>();
        adjacency.forEach((node, neighbors) -> {
            neighbors.forEach((neighbor) -> {
                int i = adjacency.keySet().stream().toList().indexOf(node);
                int j = adjacency.keySet().stream().toList().indexOf(neighbor);

                // do something
            });
        });

        return list;
    }
}
