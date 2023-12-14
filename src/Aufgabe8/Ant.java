import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

public class Ant {
    public Ant(int ID, int node) {
        this.ID = ID;
        this.visited.push(node);
    }

    public final int ID;
    public final Stack<Integer> visited = new Stack<>();

    public Double move(Iteration iter, BiFunction<Iteration, Ant, Integer> selector) {

        Integer current = visited.peek();




        List<Distance> neighborDist = iter.graph.distances.stream()
                .filter(distance -> distance.i == current || distance.j == current)
                .filter(distance -> !visited.contains(distance.i) || !visited.contains(distance.j))
                .toList();

        List<Intensity> intensities = new JoinChanges().apply(iter.intensities, iter.changeBuffer);
        List<Intensity> neighborIntensity = intensities.stream()
                .filter(intensity -> intensity.i == current || intensity.j == current)
                .filter(intensity -> !visited.contains(intensity.i) || !visited.contains(intensity.j))
                .toList();




        neighborIntensity.forEach(intensity -> {
            ProbabilityRecord pr = new ProbabilityRecord(
                    intensities.stream()
                            .filter(intensity::equals)
                            .findFirst().orElse(null),
                    neighborDist.stream()
                            .filter(item -> item.i == intensity.i && item.j == intensity.j)
                            .findFirst().orElse(null),
                    Test.ALPHA,
                    Test.BETA,
                    intensities.stream()
                            .filter(item -> !intensity.equals(item))
                            .toList(),
                    neighborDist.stream()
                            .filter(item -> item.i != intensity.i && item.j != intensity.j)
                            .collect(Collectors.toList())
            );
        });


        List<ProbabilityRecord> list = new ArrayList<>();

//
//        int nextNode = selector.apply(iter, this);
//        node.push(nextNode);
        return 1.0;
    }
}
