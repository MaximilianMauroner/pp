import java.util.List;

public class IterationRecord {
    public final int iteration;
    public final Graph graph;
    public final List<Ant> ants;
    public final List<Intensity> intensities;

    public final double L_global_best;
    public final List<Integer> global_best_path;

    public final double tau_0;
    public final double Q0;
    public final double ALPHA;
    public final double BETA;
    public final double RHO;

    public IterationRecord(int iteration, Graph graph, List<Ant> ants, List<Intensity> intensities,
                           double L_global_best, List<Integer> global_best_path, double tau_0,
                           double Q0, double ALPHA, double BETA, double RHO) {
        this.iteration = iteration;
        this.graph = graph;
        this.ants = ants;
        this.intensities = intensities;
        this.L_global_best = L_global_best;
        this.global_best_path = global_best_path;
        this.tau_0 = tau_0;
        this.Q0 = Q0;
        this.ALPHA = ALPHA;
        this.BETA = BETA;
        this.RHO = RHO;
    }
}
