import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Graph {
    public final List<Node> nodes;
    public final Map<Node, List<Node>> adjacency;
    public final List<Distance> distances;

    public Graph(List<Node> nodes, Map<Node, List<Node>> adjacency) {
        this.nodes = nodes;
        this.adjacency = adjacency;
        this.distances = new ArrayList<>();

        adjacency.forEach((node, neighbors) -> {
            neighbors.forEach((neighbor) -> {
                int i = adjacency.keySet().stream().toList().indexOf(node);
                int j = adjacency.keySet().stream().toList().indexOf(neighbor);

                Distance dist = new Distance(i, j, node.distance(neighbor, new ManhattanMetric()));

                distances.add(dist);
            });
        });
    }
}
