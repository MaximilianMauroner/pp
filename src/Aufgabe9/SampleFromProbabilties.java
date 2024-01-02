import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.IntStream;

public class SampleFromProbabilties implements Function<List<Double>, Integer> {
    @Override
    public Integer apply(List<Double> probabilities) {
        List<Double> cumulativeProbabilities = new ArrayList<>();

        // Calculate cumulative probabilities
        probabilities.stream()
                .filter(i -> i > 0)
                .reduce(0.0, (sum, probability) -> {
                    cumulativeProbabilities.add(sum + probability);
                    return sum + probability;
                });
        int size = cumulativeProbabilities.size();
        if (size == 0) return -1;
        double randomValue = Math.random() * cumulativeProbabilities.get(size - 1);

        // Find the index corresponding to the random number in cumulative probabilities
        return IntStream.range(0, cumulativeProbabilities.size())
                .filter(i -> randomValue <= cumulativeProbabilities.get(i))
                .findFirst()
                .orElse(-1);
    }
}
