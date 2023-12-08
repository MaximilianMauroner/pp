import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class Iteration {

    public Iteration(Map<Node, List<Node>> adjacency, int antCount) {
        this.adjacency = adjacency;
        this.distances = new ArrayList<>();
        this.intensities = new ArrayList<>();

        adjacency.forEach((node, neighbors) -> {
            neighbors.forEach((neighbor) -> {
                int i = adjacency.keySet().stream().toList().indexOf(node);
                int j = adjacency.keySet().stream().toList().indexOf(neighbor);

                Distance dist = new Distance(i, j, node.distance(neighbor, new Metric()));
                distances.add(dist);
                intensities.add(new Intensity(i, j, 0));
            });
        });

        this.ants = Stream.iterate(0, i -> i + 1)
                .limit(antCount)
                .map(i -> {
                    int node = (int) (Math.random() * adjacency.size());
                    return new Ant(i, node);
                })
                .toList();
    }

    public final Map<Node, List<Node>> adjacency;
    public final List<Distance> distances;
    public final List<Intensity> intensities;
    public final List<Ant> ants;

//    private <T> List<T> fill() {
//        List<T> list = new ArrayList<>();
//        adjacency.forEach((node, neighbors) -> {
//            neighbors.forEach((neighbor) -> {
//                int i = adjacency.keySet().stream().toList().indexOf(node);
//                int j = adjacency.keySet().stream().toList().indexOf(neighbor);
//
//                // do something
//            });
//        });
//
//        return list;
//    }
}
