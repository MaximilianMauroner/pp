import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Test {
    public static void main(String[] args) {
        // initialize node list
        List<Node> nodes = new ArrayList<>();
        nodes.add(new Node(1, 2));
        nodes.add(new Node(3, 4));
        nodes.add(new Node(5, 6));
        nodes.add(new Node(7, 8));
        nodes.add(new Node(9, 10));

        // set adjacency (basically set a directed acyclic graph)
        Map<Node, List<Node>> adjacency = Map.of(
            nodes.get(0), List.of(nodes.get(1), nodes.get(2)),
            nodes.get(1), List.of(nodes.get(2), nodes.get(3)),
            nodes.get(2), List.of(nodes.get(3), nodes.get(4)),
            nodes.get(3), List.of(nodes.get(4)),
            nodes.get(4), null
        );

        // initialize distance list
        List<Distance> distances = new ArrayList<>();

        // initialize intensity list
        List<Intensity> intensities = new ArrayList<>();

        adjacency.forEach((node, neighbors) -> {
            neighbors.forEach((neighbor) -> {
                int i = adjacency.keySet().stream().toList().indexOf(node);
                int j = adjacency.keySet().stream().toList().indexOf(neighbor);

                Distance dist = new Distance(i, j, node.distance(neighbor, (Node a, Node b) ->
                        Math.abs(a.x() - b.x()) + Math.abs(a.y() - b.y())
                ));
                distances.add(dist);
                intensities.add(new Intensity(i, j, 0));
            });
        });

    }
}
