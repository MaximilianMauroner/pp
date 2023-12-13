import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


/**
 * Generates a cyclic graph with a specified number of nodes.
 * This method first creates a simple cycle to ensure the graph is cyclic and then adds
 * additional random edges between nodes to increase complexity.
 * It avoids creating duplicate edges or self-loops.
 * <p>
 * The nodes are created with sequential (x, y) coordinates, where both x and y are set to the index of the node.
 * The additional edges are added randomly, and their number is also determined randomly but does not
 * exceed half of the possible maximum number of edges in a complete graph
 * for the given number of nodes.
 * <p>
 */
public class Graph {
    public static void main(String[] args) {
        Graph graph = new Graph(15);
        System.out.println(graph);
    }


    public final List<Node> nodes;
    public final Map<Node, List<Node>> adjacency;
    public final List<Distance> distances = new ArrayList<>();

    public Graph(List<Node> nodes, Map<Node, List<Node>> adjacency) {
        this.nodes = nodes;
        this.adjacency = adjacency;

        calculateDistances();
    }

    /**
     * The Graph constructor creates a cyclic graph with a specified number of nodes. This method first creates a simple cycle to ensure
     * the graph is cyclic and then adds additional random edges between nodes to increase complexity.<br>
     * It avoids creating duplicate edges or self-loops.
     *
     * @param cycleLength the number of nodes in the cycle; must be at least 3.
     * @throws IllegalArgumentException if cycleLength is less than 3.
     */
    public Graph(int cycleLength) {
        Random random = new Random(Test.SEED);
        if (cycleLength < 3) {
            throw new IllegalArgumentException("Node count must be at least 3 to form a cycle");
        }

        // Initialize nodes
        nodes = IntStream.range(0, cycleLength)
                .mapToObj(i -> new Node(i, i))
                .collect(Collectors.toList());

        // Initialize adjacency list
        adjacency = nodes.stream().collect(
                Collectors.toMap(
                        Function.identity(), node -> new ArrayList<>()
                ));

        // Form a basic cycle
        IntStream.range(0, cycleLength)
                .forEach(i -> adjacency.get(nodes.get(i)).add(nodes.get((i + 1) % cycleLength)));


        // Randomly add additional edges
        int additionalEdges = random.nextInt(cycleLength * (cycleLength - 1) / 2); // This is a mathematical formula used to calculate the maximum number of edges in a complete graph
        IntStream.range(0, additionalEdges).forEach(i -> {
            int fromIndex = random.nextInt(cycleLength);
            int toIndex = random.nextInt(cycleLength);
            Node from = nodes.get(fromIndex);
            Node to = nodes.get(toIndex);

            if (from != to && !adjacency.get(from).contains(to)) {
                adjacency.get(from).add(to);
            }
        });


        calculateDistances();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();

        BiFunction<Node, Node, Double> distanceMetric = (a, b) -> Math.hypot(a.x() - b.x(), a.y() - b.y());

        nodes.forEach((node) -> {
            List<Node> adjacentNodes = adjacency.get(node);
            adjacentNodes.forEach((adjacent) -> {
                double distance = node.distance(adjacent, distanceMetric);
                sb.append(String.format("[%d|%d] - [%d|%d]: %.2f\n",
                        node.x(), node.y(), adjacent.x(), adjacent.y(), distance));
            });
        });

        return sb.toString();
    }

    private void calculateDistances() {
        adjacency.forEach((node, neighbors) -> neighbors.forEach((neighbor) -> {
            int i = nodes.indexOf(node);
            int j = nodes.indexOf(neighbor);

            Distance dist = new Distance(i, j, node.distance(neighbor, new ManhattanMetric()));

            distances.add(dist);
        }));
    }

    /**
     * Checks if the graph contains a cycle.
     * This method utilizes depth-first search (DFS) to traverse the graph and detect cycles.
     *
     * @return {@code true} if the graph contains at least one cycle, {@code false} otherwise.
     * @IMPORTANT: THIS METHOD IS ONLY USED FOR THE TESTCASES
     */
    public boolean hasCycle() {
        //Old Code: TODO: Remove once the new code is tested
//        Set<Node> visited = new HashSet<>();
//
//        for (Node node : nodes) {
//            if (!visited.contains(node)) {
//                if (hasCycleDFS(node, visited, null)) {
//                    return true;
//                }
//            }
//        }
//
//        return false;

        Set<Node> visited = new HashSet<>();
        return nodes.stream().anyMatch(node -> !visited.contains(node) && hasCycleDFS(node, visited, null));

    }

    /**
     * Helper method to perform a depth-first search (DFS) from a given node to detect a cycle.
     *
     * @param node    the current node to start DFS from.
     * @param visited a set of nodes that have been visited in the DFS.
     * @param parent  the parent node of the current node in the DFS tree.
     * @return {@code true} if a cycle is found in the graph, {@code false} otherwise.
     */
    private boolean hasCycleDFS(Node node, Set<Node> visited, Node parent) {
        //Old Code: TODO: Remove once the new code is tested

//        visited.add(node);
//
//        for (Node adjacent : adjacency.get(node)) {
//            if (!visited.contains(adjacent)) {
//                if (hasCycleDFS(adjacent, visited, node)) {
//                    return true;
//                }
//            } else if (adjacent != parent) {
//                // If an adjacent is visited and not parent of current vertex,
//                // then there is a cycle.
//                return true;
//            }
//        }
//
//        return false;

        visited.add(node);

        return adjacency.get(node).stream().anyMatch(adjacent ->
                !visited.contains(adjacent) ? hasCycleDFS(adjacent, visited, node)
                        : adjacent != parent);
    }

    /**
     * Removes all cycles from the graph.
     * This method converts the graph into a tree by removing edges that
     * lead to already visited nodes.
     */
    public void removeCycle() {
        Set<Node> visited = new HashSet<>();
        nodes.forEach(node -> removeCycleDFS(node, visited, null));
    }

    /**
     * Recursive helper method to perform DFS and remove edges leading to visited nodes.
     *
     * @param node    the current node in the DFS.
     * @param visited a set of nodes that have been visited in the DFS.
     * @param parent  the parent node of the current node in the DFS tree.
     */
    private void removeCycleDFS(Node node, Set<Node> visited, Node parent) {
        //Old Code: TODO: Remove once the new code is tested
//        visited.add(node);
//
//        Iterator<Node> iterator = adjacency.get(node).iterator();
//
//        while (iterator.hasNext()) {
//            Node adjacent = iterator.next();
//            if (!visited.contains(adjacent)) {
//                removeCycleDFS(adjacent, visited, node);
//            } else if (adjacent != parent) {
//                // If an adjacent is visited and not parent of current vertex,
//                // remove the edge to break the cycle.
//                iterator.remove();
//            }
//        }

        visited.add(node);

        List<Node> adjacentNodes = new ArrayList<>(adjacency.get(node));
        adjacentNodes.stream()
                .filter(adjacent -> !visited.contains(adjacent))
                .forEach(adjacent -> removeCycleDFS(adjacent, visited, node));

        // Retain only those edges that do not lead to a cycle
        adjacency.put(node, adjacentNodes.stream()
                .filter(adjacent -> !visited.contains(adjacent) || adjacent == parent)
                .collect(Collectors.toList()));
    }
}
