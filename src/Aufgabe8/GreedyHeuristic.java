import java.util.Comparator;
import java.util.List;
import java.util.function.BiFunction;
import java.util.stream.IntStream;

public class GreedyHeuristic implements BiFunction<Graph, GreedyHeuristic, Double> {
    private List<Integer> visitedNodes;
    private Double distance;

    @Override
    public Double apply(Graph graph, GreedyHeuristic greedyNodeSelector) {
        // ToDo: fix

        if (greedyNodeSelector == null) {
            visitedNodes = List.of(0);
            distance = 0.0;
        } else {
            List<Integer> prevVisitedNodes = greedyNodeSelector.visitedNodes;
            int currentNode = prevVisitedNodes.get(prevVisitedNodes.size() - 1);

            Distance minDistance = graph.distances.stream()
                    .filter(distance -> distance.i == currentNode || distance.j == currentNode)
                    .filter(distance -> !prevVisitedNodes.contains(distance.i) || !prevVisitedNodes.contains(distance.j))
                    .min(Comparator.comparingDouble(distance2 -> distance2.distance))
                    .orElse(null);

            if (minDistance == null) {
                return greedyNodeSelector.distance + graph.distances.stream()
                        .filter(distance -> distance.i == 0 && distance.j == currentNode)
                        .map(distance -> distance.distance)
                        .findFirst().orElse(0);
            }

            int nextNode = minDistance.i == currentNode ? minDistance.j : minDistance.i;
            visitedNodes = IntStream.range(0, prevVisitedNodes.size() + 1)
                    .mapToObj(i -> {
                        if (i == prevVisitedNodes.size()) {
                            return nextNode;
                        }
                        return prevVisitedNodes.get(i);
                    }).toList();

            distance = greedyNodeSelector.distance + minDistance.distance;
        }

        return new GreedyHeuristic().apply(graph, this);
    }
}
