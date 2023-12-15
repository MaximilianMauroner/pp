import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.IntStream;

public class SampleFromProbabilties implements Function<List<Double>, Integer> {
    @Override
    public Integer apply(List<Double> probabilities) {
        List<Double> cumulativeProbabilities = new ArrayList<>();

        // Calculate cumulative probabilities using streams
        probabilities.stream()
                .reduce(0.0, (sum, probability) -> {
                    cumulativeProbabilities.add(sum + probability);
                    return sum + probability;
                });

        double randomValue = Math.random();

        // Find the index corresponding to the random number in cumulative probabilities using streams
        return IntStream.range(0, cumulativeProbabilities.size())
                .filter(i -> randomValue <= cumulativeProbabilities.get(i))
                .findFirst()
                .orElse(-1);
    }
}
