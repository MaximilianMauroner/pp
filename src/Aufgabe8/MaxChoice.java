import java.util.List;
import java.util.function.BiFunction;

public class MaxChoice implements BiFunction<Integer, Integer, Double> {

    private final List<Intensity> intensities;
    private final List<Distance> distances;

    public MaxChoice(List<Intensity> intensities, List<Distance> distances) {
        this.intensities = intensities;
        this.distances = distances;
    }

    @Override
    public Double apply(Integer current, Integer neighbor) {
        int i;
        int j;
        if (neighbor < current) {
            i = neighbor;
            j = current;
        } else {
            i = current;
            j = neighbor;
        }

        Intensity tau_ij = intensities.stream()
                .filter(intensity -> intensity.i == i && intensity.j == j)
                .findFirst().orElse(null);
        Distance d_ij = distances.stream()
                .filter(distance -> distance.i == i && distance.j == j)
                .findFirst().orElse(null);

        if (tau_ij == null || d_ij == null) {
            return 0.0;
        }
        return tau_ij.intensity / d_ij.distance;
    }
}
