import java.util.List;
import java.util.Objects;
import java.util.Stack;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Ant {
    public Ant(int ID, int node) {
        this.ID = ID;
        this.visited.push(node);
    }

    public final int ID;
    public final Stack<Integer> visited = new Stack<>();

    public Double move(Iteration iter) {

        Integer current = visited.peek();


        List<Distance> neighborDist = Iteration.graph.distances.stream()
                .filter(distance -> distance.i == current || distance.j == current)
                .filter(distance -> !visited.contains(distance.i) || !visited.contains(distance.j))
                .toList();

        List<Intensity> intensities = new JoinChanges().apply(iter.intensities, iter.changeBuffer);
        List<Intensity> neighborIntensity = intensities.stream()
                .filter(intensity -> intensity.i == current || intensity.j == current)
                .filter(intensity -> !visited.contains(intensity.i) || !visited.contains(intensity.j))
                .toList();

        int myRandomItem;

        if (visited.size() == Iteration.graph.nodes.size()) {
            myRandomItem = visited.getFirst();
        } else if (Math.random() < Test.Q0) {
            MaxChoice maxChoice = new MaxChoice(intensities, Iteration.graph.distances);

            myRandomItem = IntStream.range(0, Iteration.graph.nodes.size())
                    .filter(a -> !visited.contains(a))
                    .reduce((a, b) -> maxChoice.apply(current, a) > maxChoice.apply(current, b) ? a : b)
                    .orElse(visited.getFirst());
        } else {
            double sumOfAll = IntStream.range(0, neighborIntensity.size())
                    .mapToDouble(i -> {
                        double intensity = neighborIntensity.get(i).intensity;
                        double distance = neighborDist.get(i).distance;

                        return Math.pow(intensity, Test.ALPHA) * Math.pow(1 / distance, Test.BETA);
                    }).sum();

            List<Double> probabilities = IntStream.range(0, Iteration.graph.nodes.size())
                    .mapToObj(i -> {
                        if (visited.contains(i)) {
                            return 0.0;
                        }

                        int neighborIndex = IntStream.range(0, neighborIntensity.size())
                                .filter(idx -> neighborIntensity.get(idx).i == i || neighborIntensity.get(idx).j == i)
                                .findFirst().orElse(-1);

                        double intensity = neighborIntensity.get(neighborIndex).intensity;
                        double distance = neighborDist.get(neighborIndex).distance;

                        return Math.pow(intensity, Test.ALPHA) * Math.pow(1 / distance, Test.BETA) / sumOfAll;
                    }).toList();

            myRandomItem = new SampleFromProbabilties().apply(probabilities);


            /*
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
            myRandomItem = Iteration.graph.nodes.indexOf(probs.get(idx.get()).nextNode);
            */
        }
        visited.push(myRandomItem);

        Intensity oldIntensity = intensities.stream()
                .filter(intensity -> (intensity.i == current || intensity.j == current)
                        && (intensity.i == myRandomItem || intensity.j == myRandomItem))
                .findFirst().orElse(null);

        if (oldIntensity != null) {
            Intensity intensity = new Intensity(current, myRandomItem,
                    (1 - Test.RHO) * oldIntensity.intensity + Test.RHO * Iteration.tau_0);
            iter.changeBuffer.add(intensity);
        }

        return (double) Iteration.graph.distances.get(myRandomItem).distance;
    }
}
