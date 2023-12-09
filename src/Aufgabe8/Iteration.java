import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Iteration implements Supplier<Iteration> {

    public Iteration(Graph graph, int antCount) {
        this.graph = graph;
        this.ants = Stream.iterate(0, i -> i + 1)
                .limit(antCount)
                .map(i -> {
                    int node = (int) (Math.random() * graph.adjacency.size());
                    return new Ant(i, node);
                })
                .toList();

        intensitiesStack.push(
                graph.distances.stream()
                        .map(distance -> new Intensity(distance.i, distance.j, 0))
                        .toList()
        );

        L_greedy = 100.0; // TODO
    }

    public Iteration(Iteration iteration, List<Intensity> intensities) {
        this.graph = iteration.graph;
        this.ants = iteration.ants;
        this.intensitiesStack.push(intensities);
        this.L_greedy = iteration.L_greedy;
    }

    public final Graph graph;
    public final List<Ant> ants;
    public final Stack<List<Intensity>> intensitiesStack = new Stack<>();
    public Double L_global_best;
    public final Double L_greedy;

    @Override
    public Iteration get() {
        Stream.iterate(0, i -> i + 1)
                .limit(graph.adjacency.size())
                .forEach(i -> {
                    ants.forEach(ant -> ant.move(this, new NodeSelector()));
                });

        L_global_best = ants.stream()
                .mapToDouble(ant -> ant.L_local)
                .min().orElse(L_greedy);

        return new Iteration(this,
                intensitiesStack.peek().stream()
                        .map(new IntensityUpdater())
                        .toList()
        );
    }

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
