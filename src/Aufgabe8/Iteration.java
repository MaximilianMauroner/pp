import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Iteration {

    public static IterationRecord apply(IterationRecord t) {
        List<Intensity> changeBuffer = new ArrayList<>();

        List<Double> L_locals = IntStream.range(0, t.graph.nodes.size())
                .mapToObj(i ->
                    // for all ants the distance they moved in this step
                     t.ants.stream()
                            .map(ant -> ant.apply(t, changeBuffer)
                            ).toList()
                )
                // for all ants sum up the distance they moved in this iteration
                .reduce((List<Double> a, List<Double> b) -> IntStream.range(0, t.ants.size())
                        .mapToObj(i -> a.get(i) + b.get(i)).toList()
                ).orElse(
                        Arrays.asList(new Double[t.ants.size()])
        );

        Double L_gb_next = L_locals.stream()
                .filter(l -> l < t.L_global_best)
                .min(Double::compareTo)
                .orElse(t.L_global_best);

        if (Test.LIVE_OUTPUT)
            System.out.println("Iteration: " + t.iteration + ": " + L_gb_next);

        List<Integer> path = IntStream.range(0, t.ants.size())
                .filter(i -> i == L_locals.indexOf(L_gb_next))
                .mapToObj(i -> t.ants.get(i).visited.stream().toList())
                .findFirst().orElse(t.global_best_path);

        Map<Integer, Integer> edges = IntStream.range(0, path.size() - 1)
                .mapToObj(i -> Map.entry(path.get(i), path.get(i + 1)))
                .collect(HashMap::new, (m, v) -> m.put(v.getKey(), v.getValue()), HashMap::putAll);


        List<Intensity> updatedIntensities = joinChanges(t.intensities, changeBuffer, new JoinChanges()).stream()
                .map(intensity -> {
                    boolean contains = edges.get(intensity.i) == intensity.j || edges.get(intensity.j) == intensity.i;
                    double delta_tau_ij = contains ? 1 / L_gb_next : 0;
                    return new Intensity(intensity.i, intensity.j, (1 - t.RHO) * intensity.intensity + t.RHO * delta_tau_ij);
                })
                .toList();
        
        
        List<Ant> ants_next = t.ants.stream().map(ant -> new Ant(ant.visited.peek())).toList();

        return new IterationRecord(t.iteration + 1,
                t.graph, ants_next,
                updatedIntensities, L_gb_next, path, t.tau_0, t.Q0, t.ALPHA, t.BETA, t.RHO);
    }

    static List<Intensity> joinChanges(List<Intensity> intensities, List<Intensity> changeBuffer, JoinChanges joinChanges) {
        return joinChanges.apply(intensities, changeBuffer);
    }

    static List<Integer> calcHeuristic(Graph graph, Heuristic heuristic) {
        return heuristic.apply(graph, null);
    }
}
