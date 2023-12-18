import java.util.List;
import java.util.function.BiFunction;

public class JoinChanges implements BiFunction<List<Intensity>, List<Intensity>, List<Intensity>> {
    @Override
    public List<Intensity> apply(List<Intensity> intensities, List<Intensity> changeList) {
        return intensities.stream().parallel()
                .map(intensity -> changeList.stream()
                        .filter(change -> change.i == intensity.i && change.j == intensity.j)
                        .reduce((a, b) -> b).orElse(intensity)
        ).toList();
    }
}
