import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.concurrent.atomic.AtomicInteger;
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

        int myRandomItem;

        if (Math.random() > Test.Q0) {
            int maxIndex = 0;
            /*int maxIndex = neighborIntensity.stream()
                    .mapToDouble(intensity -> {
                        Distance distance = neighborDist.stream().filter(dist -> dist.j == intensity.j).findFirst().orElse(null);
                        return intensity.intensity / distance.distance;
                    }).
            */

            myRandomItem = maxIndex;
        } else {

            Probability probability = new Probability();
            List<ProbabilityRecord> probabilityList = neighborIntensity.stream().map(intensity -> {
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
                                .collect(Collectors.toList()),
                        Iteration.graph.nodes.get(intensity.j)
                );
                return probability.apply(pr);
            }).toList();

            List<ProbabilityRecord> probs = probabilityList.stream().toList();


            double totalWeight = probs.stream().mapToDouble(rec -> rec.probability).reduce(Double::sum).orElse(0.0);

            // Note: this part is procedural
            var idx = new AtomicInteger();

            for (double r = Math.random() * totalWeight; idx.get() < probs.size() - 1; idx.incrementAndGet()) {
                r -= probs.get(idx.get()).probability;
                if (r <= 0.0) break;
            }
            // Note: end of procedural
            myRandomItem = idx.get();
        }
        visited.push(myRandomItem);

        return (double) Iteration.graph.distances.get(myRandomItem).distance;
    }
}
