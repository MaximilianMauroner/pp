import java.util.function.Function;

public class Probability implements Function<ProbabilityRecord, ProbabilityRecord> {

    @Override
    public ProbabilityRecord apply(ProbabilityRecord probabilityRecord) {
        double edgeVal = Math.pow(probabilityRecord.edgeIJ_PheromoneStrength.intensity,
                probabilityRecord.pheromoneInfluenceOnNextCitySelection);
        double cityVal = Math.pow(
                (1 / (double) probabilityRecord.cityIJDistanceInfluence.distance),
                probabilityRecord.cityDistanceInfluence);


        double restEdgeVal = probabilityRecord
                .edgeIL_PheromoneStrength
                .stream()
                .mapToDouble(val -> Math.pow(val.intensity,
                        probabilityRecord.pheromoneInfluenceOnNextCitySelection)).sum();

        double restCityVal = probabilityRecord
                .cityILDistanceInfluence
                .stream()
                .mapToDouble(val -> Math.pow((1 / (double) val.distance),
                        probabilityRecord.cityDistanceInfluence)).sum();


        double probability = (edgeVal * cityVal) / (restEdgeVal * restCityVal);
        return new ProbabilityRecord(
                probabilityRecord.edgeIJ_PheromoneStrength,
                probabilityRecord.cityIJDistanceInfluence,
                probabilityRecord.pheromoneInfluenceOnNextCitySelection,
                probabilityRecord.cityDistanceInfluence,
                probabilityRecord.edgeIL_PheromoneStrength,
                probabilityRecord.cityILDistanceInfluence,
                probabilityRecord.nextNode,
                probability
        );
    }
}