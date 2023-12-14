import java.util.function.Function;

public class Probability implements Function<ProbabilityRecord, Double> {

    @Override
    public Double apply(ProbabilityRecord probabilityRecord) {
        double edgeVal = Math.pow(probabilityRecord.edgeIJ_PheromoneStrength.intensity,
                probabilityRecord.pheromoneInfluenceOnNextCitySelection);
        double cityVal =  Math.pow(
                (1/(double)probabilityRecord.cityIJDistanceInfluence.distance),
                probabilityRecord.cityDistanceInfluence);


        double restEdgeVal = probabilityRecord
                .edgeIL_PheromoneStrength
                .stream()
                .mapToDouble(val -> Math.pow(val.intensity,
                        probabilityRecord.pheromoneInfluenceOnNextCitySelection)).sum();

        double restCityVal = probabilityRecord
                .cityILDistanceInfluence
                .stream()
                .mapToDouble(val -> Math.pow((1/(double)val.distance),
                        probabilityRecord.cityDistanceInfluence)).sum();

        return  (edgeVal * cityVal)/(restEdgeVal * restCityVal);
    }
}