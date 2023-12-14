import java.util.Comparator;
import java.util.function.BiFunction;
import java.util.List;
import java.util.stream.IntStream;

public class GreedyHeuristic implements Heuristic {

    @Override
    public Double apply(Graph graph, List<Integer> visitedNodes) {
        if (visitedNodes == null) {
            return apply(graph, List.of(0));
        }

        int currentNode = visitedNodes.getLast();

        if (visitedNodes.size() == graph.nodes.size()) {
            return graph.distances.stream()
                    .filter(distance -> distance.i == 0 && distance.j == currentNode)
                    .mapToDouble(distance -> distance.distance)
                    .findFirst().orElse(0);
        } else {
            Distance minDistance = graph.distances.stream()
                    .filter(distance -> distance.i == currentNode || distance.j == currentNode)
                    .filter(distance -> !visitedNodes.contains(distance.i) || !visitedNodes.contains(distance.j))
                    .min(Comparator.comparingDouble(distance -> distance.distance))
                    .orElse(null);

            if (minDistance == null) {
                return apply(graph, visitedNodes);
            }

            int nextNode = minDistance.i == currentNode ? minDistance.j : minDistance.i;

            List<Integer> newVisitedNodes = IntStream.range(0, visitedNodes.size() + 1)
                    .mapToObj(i -> {
                        if (i == visitedNodes.size()) {
                            return nextNode;
                        }
                        return visitedNodes.get(i);
                    }).toList();

            return minDistance.distance + apply(graph, newVisitedNodes);
        }

    }
}
