import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Iteration implements Supplier<Iteration> {

    public Iteration(Graph graph, int antCount) {
        this.graph = graph;
        this.ants = Stream.iterate(0, i -> i + 1)
                .limit(antCount)
                .map(i -> {
                    int node = (int) (Math.random() * graph.nodes.size());
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
    public List<Integer> global_best_path;
    public final Double L_greedy;

    @Override
    public Iteration get() {
//        Stream.iterate(0, i -> i + 1)
//                .limit(graph.adjacency.size())
//                .forEach(i -> {
//                    List<Double> stepLengths = ants.stream()
//                            .map(ant -> ant.move(this, new NodeSelector()))
//                            .toList();
//
//                    ants.forEach(
//                            ant -> ant.move(this, new NodeSelector())
//                    );
//                });
        List<Double> L_locals = IntStream.range(0, 0)
                .mapToObj(i ->
                    // for all ants the distance they moved in this step
                     ants.stream()
                            .map(ant -> ant.move(this, new NodeSelector()))
                            .toList()
                ).toList()
                // for all ants sum up the distance they moved in this iteration
                .stream().reduce((List<Double> a, List<Double> b) -> IntStream.range(0, ants.size())
                        .mapToObj(i -> a.get(i) + b.get(i)).toList()
                ).orElse(
                        Arrays.asList(new Double[ants.size()])
        );

        Double L_gb_next = L_locals.stream().min(Double::compareTo).orElse(L_global_best);
        List<Integer> path = ants.stream()
                .filter(ant -> ant.L_local.equals(L_gb_next))
                .map(ant -> ant.node.stream().toList())
                .findFirst().orElse(global_best_path);
        
        List<Intensity> updatedIntensities = intensitiesStack.peek().stream()
                .map(intensity -> {
                    double delta_tau_ij = path.contains(intensity.i) ? 1 / L_gb_next : 0;
                    return new Intensity(intensity.i, intensity.j, (1 - Test.RHO) * intensity.intensity + Test.RHO * delta_tau_ij);
                })
                .toList();
        
        
        List<Ant> ants_next = ants.stream().map(ant -> new Ant(ant.ID, ant.node.peek())).toList();
        Stack<List<Intensity>> intensitiesStack_next = new Stack<>();
        intensitiesStack_next.push(updatedIntensities);

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
