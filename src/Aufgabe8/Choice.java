import java.util.List;
import java.util.function.Function;

public class Choice implements Function<Integer, Double> {

    private final List<Intensity> intensities;
    private final Graph graph;
    private final Integer current;
    private final double alpha;
    private final double beta;

    public Choice(List<Intensity> intensities, Graph graph, Integer current, double alpha, double beta) {
        this.intensities = intensities;
        this.graph = graph;
        this.current = current;
        this.alpha = alpha;
        this.beta = beta;
    }

    @Override
    public Double apply(Integer neighbor) {
        int i;
        int j;
        if (neighbor < current) {
            i = neighbor;
            j = current;
        } else {
            i = current;
            j = neighbor;
        }

        int edgeIndex = Graph.getIndex(i, j, graph.nodes.size());

        Intensity neighborIntensity = intensities.get(edgeIndex);
        Distance neighborDist = graph.distances.get(edgeIndex);

//        Intensity neighborIntensity = intensities.stream()
//                .filter(intensity -> intensity.i == i && intensity.j == j)
//                .findFirst().orElse(null);
//        Distance neighborDist = distances.stream()
//                .filter(distance -> distance.i == i && distance.j == j)
//                .findFirst().orElse(null);

        if (neighborIntensity == null || neighborDist == null) {
            return 0.0;
        }

        double tau_ij = neighborIntensity.intensity;
        double d_ij = neighborDist.distance;

        return Math.pow(tau_ij, alpha) * Math.pow(1 / d_ij, beta);
    }
}
