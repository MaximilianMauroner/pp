import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;


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
    public final List<Node> nodes;
    public final Map<Node, List<Node>> adjacency;
    public final Distance[] distances;

    public Graph(List<Node> nodes, Map<Node, List<Node>> adjacency, Metric metric) {
        this.nodes = nodes;
        this.adjacency = adjacency;
        this.distances = new Distance[nodes.size() * (nodes.size() - 1) / 2];

        calculateDistances(metric);
    }

    /**
     * The Graph constructor creates a cyclic graph with a specified number of nodes. This method first creates a simple cycle to ensure
     * the graph is cyclic and then adds additional random edges between nodes to increase complexity.<br>
     * It avoids creating duplicate edges or self-loops.
     *
     * @param numNodes the number of nodes in the cycle; must be at least 3.
     * @throws IllegalArgumentException if numNodes is less than 3.
     */
    public Graph(int numNodes, Metric metric) {
        if (numNodes < 3) {
            throw new IllegalArgumentException("Node count must be at least 3 to form a cycle");
        }

        // Initialize nodes

        Random random = new Random(Test.SEED);

        nodes = Stream.iterate(0, i -> i + 1)
                .map(i -> {
                    int x = (int) (random.nextDouble() * numNodes);
                    int y = (int) (random.nextDouble() * numNodes);

                    return new Node(x, y);
                }).distinct()
                .limit(numNodes)
                .toList();

        // Initialize the adjacency list
        adjacency = nodes.stream().collect(
                Collectors.toMap(
                        Function.identity(), node -> new ArrayList<>()
                ));

        // Connect each node to every other node using streams
        IntStream.range(0, numNodes)
                .forEach(i -> {
                    Node currentNode = nodes.get(i);
                    List<Node> currentAdjacencyList = adjacency.get(currentNode);

                    IntStream.range(0, numNodes)
                            .filter(j -> i < j)
                            .mapToObj(nodes::get)
                            .forEach(currentAdjacencyList::add);
                });

        this.distances = new Distance[nodes.size() * (nodes.size() - 1) / 2];
        calculateDistances(metric);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();

        //BiFunction<Node, Node, Double> distanceMetric = (a, b) -> Math.hypot(a.x() - b.x(), a.y() - b.y());

        nodes.forEach((node) -> {
            List<Node> adjacentNodes = adjacency.get(node);
            adjacentNodes.forEach((adjacent) -> {
                double distance = node.distance(adjacent, new ManhattanMetric());
                sb.append(String.format("[%d|%d] - [%d|%d]: %.2f\n",
                        node.x(), node.y(), adjacent.x(), adjacent.y(), distance));
            });
        });

        return sb.toString();
    }

    private void calculateDistances(Metric metric) {
        adjacency.forEach((node, neighbors) -> neighbors.forEach((neighbor) -> {
            int i = nodes.indexOf(node);
            int j = nodes.indexOf(neighbor);
            int edgeIndex = getIndex(i, j, nodes.size());

            Distance dist = new Distance(i, j, node.distance(neighbor, metric));

            distances[edgeIndex] = dist;
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
     * @IMPORTANT: THIS METHOD IS ONLY USED FOR THE TESTCASES
     */
    private boolean hasCycleDFS(Node node, Set<Node> visited, Node parent) {
        visited.add(node);

        return adjacency.get(node).stream().anyMatch(adjacent ->
                !visited.contains(adjacent) ? hasCycleDFS(adjacent, visited, node)
                        : adjacent != parent);
    }

    /**
     * Removes all cycles from the graph.
     * This method converts the graph into a tree by removing edges that
     * lead to already visited nodes.
     * @IMPORTANT: THIS METHOD IS ONLY USED FOR THE TESTCASES
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
     * @IMPORTANT: THIS METHOD IS ONLY USED FOR THE TESTCASES
     */
    private void removeCycleDFS(Node node, Set<Node> visited, Node parent) {
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


    public static int getIndex(int i, int j, int n) {
        int u;
        int v;

        if (i < j) {
            u = i;
            v = j;
        } else {
            u = j;
            v = i;
        }


        return IntStream.rangeClosed(0, u - 1)
                .map(k -> n - k - 1)
                .sum() + v - u - 1;
    }
}
