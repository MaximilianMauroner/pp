import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;

public class JoinChanges implements BiFunction<Intensity[], List<Intensity>, Intensity[]> {
    @Override
    public Intensity[] apply(Intensity[] intensities, List<Intensity> changeList) {
        return Arrays.stream(intensities).parallel()
                .map(intensity -> changeList.stream()
                        .filter(change -> change.i == intensity.i && change.j == intensity.j)
                        .reduce((a, b) -> b).orElse(intensity)
        ).toArray(Intensity[]::new);
    }
}
