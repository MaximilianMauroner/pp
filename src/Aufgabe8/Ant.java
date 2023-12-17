import java.util.List;
import java.util.Stack;
import java.util.function.BiFunction;
import java.util.stream.IntStream;

public class Ant implements BiFunction<IterationRecord, List<Intensity>, Double> {
    public Ant(int node) {
        this.visited.push(node);
    }

    public final Stack<Integer> visited = new Stack<>();

    public Double apply(IterationRecord iter, List<Intensity> changeBuffer) {

        Integer current = visited.peek();

//        List<Distance> neighborDist = iter.graph.distances.stream()
//                .filter(distance -> distance.i == current || distance.j == current)
//                .filter(distance -> !visited.contains(distance.i) || !visited.contains(distance.j))
//                .toList();

        List<Intensity> intensities = Iteration.joinChanges(iter.intensities, changeBuffer, new JoinChanges());
//        List<Intensity> neighborIntensity = intensities.stream()
//                .filter(intensity -> intensity.i == current || intensity.j == current)
//                .filter(intensity -> !visited.contains(intensity.i) || !visited.contains(intensity.j))
//                .toList();


        int myRandomItem;

        if (visited.size() == iter.graph.nodes.size()) {
            myRandomItem = visited.getFirst();
        } else if (Math.random() < Test.Q0) {
            Choice choice = new Choice(intensities, iter.graph.distances, current, 1, 1);

            myRandomItem = IntStream.range(0, iter.graph.nodes.size())
                    .filter(a -> !visited.contains(a))
                    .reduce((a, b) -> choice.apply(a) > choice.apply(b) ? a : b)
                    .orElse(visited.getFirst());
        } else {
            Choice choice = new Choice(intensities, iter.graph.distances, current, iter.ALPHA, iter.BETA);

            double sumOfAll = IntStream.range(0, iter.graph.nodes.size())
                    .mapToDouble(i -> {
                        if (visited.contains(i)) {
                            return 0.0;
                        }

                        return choice.apply(i);
                    }).sum();

            List<Double> probabilities = IntStream.range(0, iter.graph.nodes.size())
                    .mapToObj(i -> {
                        if (visited.contains(i)) {
                            return 0.0;
                        }

                        return choice.apply(i) / sumOfAll;
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
                    (1 - iter.RHO) * oldIntensity.intensity + iter.RHO * iter.tau_0);
            changeBuffer.add(intensity);
        }

        return (double) iter.graph.distances.stream()
                .filter(distance -> (distance.i == current || distance.j == current)
                        && (distance.i == myRandomItem || distance.j == myRandomItem))
                .map(distance -> distance.distance)
                .findFirst().orElse(0.0);
    }
}
