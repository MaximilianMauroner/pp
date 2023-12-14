import java.util.stream.IntStream;

public class Test {
    public static void main(String[] args) {
/*
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
        // (0,1) (1, 2) (2,3)
        //(0, (1, 2, 3))

        // kleinerer Index zuerst
        // i = 0, j = 1
        // i -> j (i, j)
        // i = 0, j = 1
        // j -> i (i, j)

        Graph graph = new Graph(nodes, adjacency);

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

        IntStream.range(3, 100).forEach(i -> {
            System.out.println("\nTesting with " + i + " nodes");
            Graph g = new Graph(i);
            assertBool(g.hasCycle(), true);
            g.removeCycle();
            assertHasNoCycles(g);
            assertBool(g.hasCycle(), false); //Check if the algorithm for removing and detecting is correct
        });
    }

    private static void assertBool(boolean b, boolean expected) {
        if (b != expected) throw new RuntimeException("Expected false, but was true");
        else System.out.println("Successful test: assert bool");
    }

    private static void assertHasNoCycles(Graph g) {
        if (g.hasCycle()) throw new RuntimeException("Graph has a cycle");
        else System.out.println("Successful test: has no cycles");
    }
}
