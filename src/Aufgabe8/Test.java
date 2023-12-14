import java.util.*;

public class Test {
    public static long SEED = new Random().nextLong();
    public static int M = 25;
    public static double Q0 = 0.9;
    public static double ALPHA = 1.0;
    public static double BETA = 2.0;
    public static double RHO = 0.1;

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
                nodes.get(4), List.of()
        );
        // (0,1) (1, 2) (2,3)
        //(0, (1, 2, 3))

        // kleinerer Index zuerst
        // i = 0, j = 1
        // i -> j (i, j)
        // i = 0, j = 1
        // j -> i (i, j)

        List<Intensity> ints = List.of(
                new Intensity(0, 1, 0.0),
                new Intensity(0, 2, 0.0),
                new Intensity(1, 2, 0.0),
                new Intensity(1, 3, 0.0),
                new Intensity(2, 3, 0.0),
                new Intensity(2, 4, 0.0),
                new Intensity(3, 4, 0.0)
        );

        List<Intensity> changes = List.of(
                new Intensity(0, 1, 1),
                new Intensity(1, 2, 1),
                new Intensity(0, 1, 2)
        );

        List<Intensity> result = new JoinChanges().apply(ints, changes);

        Graph graph = new Graph(nodes, adjacency);

        Double test = new GreedyHeuristic().apply(graph, null);

        Iteration t = new Iteration(graph, 2, new GreedyHeuristic());
        Iteration t_next = t.get();


        // initialize distance list
        List<Distance> distances = new ArrayList<>();

        // initialize intensity list
        List<Intensity> intensities = new ArrayList<>();

        adjacency.forEach((node, neighbors) -> {
            neighbors.forEach((neighbor) -> {
                int i = adjacency.keySet().stream().toList().indexOf(node);
                int j = adjacency.keySet().stream().toList().indexOf(neighbor);

                Distance dist = new Distance(i, j, node.distance(neighbor, new ManhattanMetric()));
                distances.add(dist);
                intensities.add(new Intensity(i, j, 0));
            });
        });

//        List<Iteration> iterations;
//        Stream.iterate(0, i -> i + 1)
//                .limit(10)
//                .map(i -> {
//
//                    Iteration next = iterations.get(i).next();
//                    iterations.add(next);
//                })
//                .toList();



//        positions p = ants.foreach(ant
//                ant.move()
//                return ant.positon()
//        ).collect()
//
//        List<Ant> a0;
//        List<Ant> a1 = a0.foreach(ant -> Movement.move(ant) ant.move()).collect();
//        ant.move(Movement)
//        ants
//
//        adjacency.


/**/

        Test.SEED = 42;
        Graph g = new Graph(5);
        assertBool(g.hasCycle(), true);


        Graph g2 = new Graph(50);
        assertBool(g2.hasCycle(), true);

        IntStream.range(3, 100).forEach(i -> {
            System.out.println("\nTesting with " + i + " nodes");
            Graph g = new Graph(i);

            assertHasCycles(g);
            System.out.println("Removing cycles...");
            g.removeCycle();
            assertHasNoCycles(g);
        });
    }

    private static void assertHasCycles(Graph g) {
        if (!g.hasCycle()) throw new RuntimeException("Graph has no cycle");
        else System.out.println("Successful test: Graph has at least one cycle");
    }

    private static void assertHasNoCycles(Graph g) {
        if (g.hasCycle()) throw new RuntimeException("Graph has at least one cycle");
        else System.out.println("Successful test: Graph has no cycle");
    }
}
