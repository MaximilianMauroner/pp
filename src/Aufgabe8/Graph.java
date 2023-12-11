import java.util.*;
import java.util.function.BiFunction;


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
    public final List<Distance> distances;

    public Graph(List<Node> nodes, Map<Node, List<Node>> adjacency) {
        this.nodes = nodes;
        this.adjacency = adjacency;
        this.distances = new ArrayList<>();

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

        // Initialize nodes and adjacency list
        nodes = new ArrayList<>(cycleLength);
        adjacency = new HashMap<>();
        distances = new ArrayList<>();

        // Create nodes
        for (int i = 0; i < cycleLength; i++) {
            Node node = new Node(i, i); // or any other logic to create nodes
            nodes.add(node);
            adjacency.put(node, new ArrayList<>());
        }

        // Form a basic cycle
        for (int i = 0; i < cycleLength; i++) {
            Node current = nodes.get(i);
            Node next = nodes.get((i + 1) % cycleLength);
            adjacency.get(current).add(next);
        }

        // Randomly add additional edges
        int additionalEdges = random.nextInt(cycleLength * (cycleLength - 1) / 2); // This is a mathematical formula used to calculate the maximum number of edges in a complete graph
        for (int i = 0; i < additionalEdges; i++) {
            int fromIndex = random.nextInt(cycleLength);
            int toIndex = random.nextInt(cycleLength);
            Node from = nodes.get(fromIndex);
            Node to = nodes.get(toIndex);

            // Avoid loops and duplicate edges
            if (from != to && !adjacency.get(from).contains(to)) {
                adjacency.get(from).add(to);
            }
        }


        calculateDistances();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();

        BiFunction<Node, Node, Double> distanceMetric = (a, b) -> Math.hypot(a.x() - b.x(), a.y() - b.y());

        for (Node node : nodes) {
            List<Node> adjacentNodes = adjacency.get(node);
            for (Node adjacent : adjacentNodes) {
                double distance = node.distance(adjacent, distanceMetric);
                sb.append(String.format("[%d|%d] - [%d|%d]: %.2f\n",
                        node.x(), node.y(), adjacent.x(), adjacent.y(), distance));
            }
        }

        return sb.toString();
    }

    private void calculateDistances() {
        adjacency.forEach((node, neighbors) -> neighbors.forEach((neighbor) -> {
            int i = adjacency.keySet().stream().toList().indexOf(node);
            int j = adjacency.keySet().stream().toList().indexOf(neighbor);

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
        Set<Node> visited = new HashSet<>();

        for (Node node : nodes) {
            if (!visited.contains(node)) {
                if (hasCycleDFS(node, visited, null)) {
                    return true;
                }
            }
        }

        return false;
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
        visited.add(node);

        for (Node adjacent : adjacency.get(node)) {
            if (!visited.contains(adjacent)) {
                if (hasCycleDFS(adjacent, visited, node)) {
                    return true;
                }
            } else if (adjacent != parent) {
                // If an adjacent is visited and not parent of current vertex,
                // then there is a cycle.
                return true;
            }
        }

        return false;
    }
}
