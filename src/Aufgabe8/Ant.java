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

        List<Intensity> intensities = Iteration.joinChanges(iter.intensities, changeBuffer, new JoinChanges());

        int myRandomItem;

        if (visited.size() == iter.graph.nodes.size()) {
            myRandomItem = visited.getFirst();
        } else if (Math.random() < iter.Q0) {
            Choice choice = new Choice(intensities, iter.graph.distances, current, 1, 1);

            myRandomItem = IntStream.range(0, iter.graph.nodes.size())
                    .filter(a -> !visited.contains(a))
                    .reduce((a, b) -> choice.apply(a) > choice.apply(b) ? a : b)
                    .orElse(visited.getFirst());
        } else {
            Choice choice = new Choice(intensities, iter.graph.distances, current, iter.ALPHA, iter.BETA);

            double sumOfAll = IntStream.range(0, iter.graph.nodes.size())
                    .filter(i -> !visited.contains(i))
                    .mapToDouble(choice::apply).sum();

            List<Double> probabilities = IntStream.range(0, iter.graph.nodes.size())
                    .mapToObj(i -> {
                        if (visited.contains(i)) {
                            return 0.0;
                        }

                        return choice.apply(i) / sumOfAll;
                    }).toList();

            myRandomItem = new SampleFromProbabilties().apply(probabilities);
        }
        visited.push(myRandomItem);

        int edgeIndex = intensities.stream()
                .filter(intensity -> (intensity.i == current || intensity.j == current)
                        && (intensity.i == myRandomItem || intensity.j == myRandomItem))
                .map(intensities::indexOf)
                .findFirst().orElse(-1);

        if (edgeIndex != -1) {
            changeBuffer.add(
                    new Intensity(current, myRandomItem,
                            (1 - iter.RHO) * intensities.get(edgeIndex).intensity + iter.RHO * iter.tau_0
                    )
            );

            return iter.graph.distances.get(edgeIndex).distance;
        }

        return 0.0;
    }
}
