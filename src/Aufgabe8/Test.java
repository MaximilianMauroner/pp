import java.sql.SQLOutput;
import java.util.*;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/*
Maximilian Mauroner: Probability, Ant
Lukas Leskovar: Iteration, Ant
Christopher Scherling: Graph
 */

public class Test {
    public static long SEED = new Random().nextLong();
    public static int N = 40;
    public static int ITERATIONS = 100;
    public static int M = 25;
    public static double Q0 = 0.9;
    public static double ALPHA = 1.0;
    public static double BETA = 2.0;
    public static double RHO = 0.1;

    public static boolean LIVE_OUTPUT = true;

    public static void main(String[] args) {
        System.out.println("Seed: " + SEED + " \n" + "N: " + N + " \n"
                + "Iterations: " + ITERATIONS + " \n"
                + "M: " + M + " \n"
                + "Q0: " + Q0 + " \n"
                + "ALPHA: " + ALPHA + " \n"
                + "BETA: " + BETA + " \n"
                + "RHO: " + RHO + " \n"
                + "LIVE_OUTPUT: " + LIVE_OUTPUT);

        // Get start time
        long start = System.currentTimeMillis();

        Graph graph = new Graph(N, new ManhattanMetric());

        List<Ant> ants = Stream.iterate(0, i -> i + 1)
                .limit(M)
                .map(i -> {
                    int node = (int) (Math.random() * graph.nodes.size());
                    return new Ant(node);
                })
                .toList();

        List<Integer> greedy_path = Iteration.calcHeuristic(graph, new GreedyHeuristic());
        double L_greedy = IntStream.range(0, greedy_path.size() - 1)
                .mapToDouble(i -> {
                    int first = greedy_path.get(i);
                    int second = greedy_path.get(i + 1);

                    return graph.distances.stream()
                            .filter(distance -> (distance.i == first || distance.i == second) && (distance.j == first || distance.j == second))
                            .map(distance -> distance.distance)
                            .findFirst().orElse(0.0);
                }).sum();


        double tau_0 = 1 / (graph.nodes.size() * L_greedy);

        List<Intensity> intensities = graph.distances.stream()
                .map(distance -> new Intensity(distance.i, distance.j, L_greedy))
                .toList();

        IterationRecord t0 = new IterationRecord(0, graph, ants, intensities, L_greedy, greedy_path, tau_0, Q0, ALPHA, BETA, RHO);

        // start iterations
        System.out.println("Starting iterations...");

        List<IterationRecord> ts = Stream.iterate(t0, Iteration::apply)
                .limit(ITERATIONS)
                .toList();

        // Get end time
        long end = System.currentTimeMillis();

        if (!LIVE_OUTPUT) {
            ts.forEach(t -> {
                System.out.println("Iteration " + t.iteration + ": " + t.L_global_best);
            });
        }

        System.out.println("Execution time: " + (end - start) + "ms");

        List<Map.Entry<Integer, Integer>> edges = IntStream.range(0, ts.getLast().global_best_path.size() - 1)
                .mapToObj(i -> Map.entry(
                        ts.getLast().global_best_path.get(i),
                        ts.getLast().global_best_path.get(i + 1))
                )
                .toList();

        edges.forEach(edge -> {
            double dist = graph.distances.stream()
                    .filter(distance -> (distance.i == edge.getKey() || distance.i == edge.getValue())
                            && (distance.j == edge.getKey() || distance.j == edge.getValue()))
                    .map(distance -> distance.distance)
                    .findFirst().orElse(0.0);
            System.out.println(edge.getKey() + " -> " + edge.getValue() + ": " + dist);
        });

        System.out.println("L_global_best: " + ts.getLast().L_global_best);


        // Tests
        System.out.println("Testing Graph: ");

        IntStream.range(3, 100).forEach(i -> {
            System.out.println("\nTesting with " + i + " nodes");
            Graph g = new Graph(i, new ManhattanMetric());

            assertHasCycles(g);
            System.out.println("Removing cycles...");
            g.removeCycle();
            assertHasNoCycles(g);
        });


        System.out.println("Testing Metric: ");
        Metric m = new ManhattanMetric();
        testEquals(m.apply(new Node(0, 0), new Node(0, 0)), 0);
        testEquals(m.apply(new Node(0, 0), new Node(1, 0)), 1);
        testEquals(m.apply(new Node(1, 2), new Node(3, 4)), 4);

        System.out.println("Testing GreedyHeuristic: ");
        Test.SEED = 42;
        Graph g = new Graph(5, new ManhattanMetric());
        List<Integer> greedyPath = Iteration.calcHeuristic(g, new GreedyHeuristic());
        testEquals(greedyPath, List.of(0, 2, 3, 1, 4, 0));

        System.out.println("Testing JoinChanges: ");
        List<Intensity> testIntensities = List.of(
                new Intensity(0, 1, 1.0),
                new Intensity(0, 2, 2.0),
                new Intensity(0, 3, 3.0)
        );
        List<Intensity> testChanges = List.of(
                new Intensity(0, 1, 0.0),
                new Intensity(0, 2, 0.0),
                new Intensity(0, 3, 0.0),
                new Intensity(0, 1, 4.0)
        );
        testEquals(
                Iteration.joinChanges(testIntensities, testChanges, new JoinChanges()).stream()
                        .map(intensity -> intensity.intensity)
                        .toList(), List.of(4.0, 0.0, 0.0)
        );

        System.out.println("Testing Probability: ");
        List<Double> probabilities = List.of(0.1, 0.2, 0.3, 0.4);

        List<Integer> samples = IntStream.range(0, 1000)
                .mapToObj(i -> new SampleFromProbabilties().apply(probabilities))
                .toList();
        List<Double> sampleFrequencies = IntStream.range(0, 4)
                .mapToObj(i -> (double) Collections.frequency(samples, i) / 1000)
                .toList();

        IntStream.range(0, 4).forEach(i -> {
            boolean inRange = sampleFrequencies.get(i) >= probabilities.get(i) - 0.1 && sampleFrequencies.get(i) <= probabilities.get(i) + 0.1;
            testEquals(inRange, true);
        });


        System.out.println("Testing Choice: ");
        List<Intensity> testIntensities2 = List.of(
                new Intensity(0, 1, 1.0),
                new Intensity(0, 2, 2.0),
                new Intensity(1, 2, 3.0)
        );

        Test.SEED = 42;
        Graph g2 = new Graph(3, new ManhattanMetric());

        Choice choice = new Choice(testIntensities2, g2, 0, 1, 1);
        testEquals(choice.apply(3), 1.0);

        choice = new Choice(testIntensities2, g2, 0, 2, 2);
        testEquals(choice.apply(1), 0.0625);


        System.out.println("Testing Ant: ");
        Ant ant = new Ant(0);

        List<Intensity> testIntensities3 = List.of(
                new Intensity(0, 1, 1.0),
                new Intensity(0, 2, 2.0),
                new Intensity(0, 3, 3.0),
                new Intensity(0, 4, 4.0),
                new Intensity(1, 2, 5.0),
                new Intensity(1, 3, 6.0),
                new Intensity(1, 4, 7.0),
                new Intensity(2, 3, 8.0),
                new Intensity(2, 4, 9.0),
                new Intensity(3, 4, 10.0)
        );
        IterationRecord t = new IterationRecord(0, g, null, testIntensities3, 0.0, null, 0, 1, ALPHA, BETA, RHO);
        ant.apply(t, new ArrayList<>());

        testEquals(ant.visited.peek(), 3);
    }

    private static void assertHasCycles(Graph g) {
        if (!g.hasCycle()) throw new RuntimeException("Graph has no cycle");
        else System.out.println("Successful test: Graph has at least one cycle");
    }

    private static void assertHasNoCycles(Graph g) {
        if (g.hasCycle()) throw new RuntimeException("Graph has at least one cycle");
        else System.out.println("Successful test: Graph has no cycle");
    }

    public static void testEquals(Object given, Object expected) {
        if (given.equals(expected)) {
            System.out.println("Successful test");
        } else {
            System.out.println("Test NOT successful! Expected value: " + expected.toString() + " / Given " + "value: " + given.toString());
        }
    }

    public static void testEquals(boolean given, boolean expected) {
        if (given == expected) {
            System.out.println("Successful test");
        } else {
            System.out.println("Test NOT successful! Expected value: " + expected + " / Given " + "value: " + given);
        }
    }
}
