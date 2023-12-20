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

        Intensity[] intensities = Iteration.joinChanges(iter.intensities, changeBuffer, new JoinChanges());

        int myRandomItem;

        if (visited.size() == iter.graph.nodes.size()) {
            myRandomItem = visited.getFirst();
        } else if (Math.random() < iter.Q_0) {
            Choice choice = new Choice(intensities, iter.graph, current, 1, 1);

            myRandomItem = IntStream.range(0, iter.graph.nodes.size())
                    .filter(a -> !visited.contains(a))
                    .reduce((a, b) -> choice.apply(a) > choice.apply(b) ? a : b)
                    .orElse(visited.getFirst());

        } else {
            Choice choice = new Choice(intensities, iter.graph, current, iter.ALPHA, iter.BETA);

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


        int edgeIndex = Graph.getIndex(current, myRandomItem, iter.graph.nodes.size());

        if (edgeIndex >= 0 && edgeIndex < intensities.length) {
            changeBuffer.add(
                    new Intensity(current, myRandomItem,
                            (1 - iter.RHO) * intensities[edgeIndex].intensity + iter.RHO * iter.tau_0
                    )
            );

            return iter.graph.distances[edgeIndex].distance;
        }

        return 0.0;
    }
}
