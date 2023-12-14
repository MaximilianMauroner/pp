import java.util.List;

public class ProbabilityRecord {

    public final Intensity edgeIJ_PheromoneStrength;
    public final Distance cityIJDistanceInfluence;
    public final double pheromoneInfluenceOnNextCitySelection; //static var??
    public final double cityDistanceInfluence;//static var??

    public final List<Intensity> edgeIL_PheromoneStrength;
    public final List<Distance> cityILDistanceInfluence;

    public final Node nextNode;
    public final double probability;


    public ProbabilityRecord(Intensity edgeIJ_PheromoneStrength,
                             Distance cityIJDistanceInfluence,
                             double pheromoneInfluenceOnNextCitySelection,
                             double cityDistanceInfluence,
                             List<Intensity> edgeIL_PheromoneStrength,
                             List<Distance> cityILDistanceInfluence,
                             Node nextNode
    ) {
        this.edgeIJ_PheromoneStrength = edgeIJ_PheromoneStrength;
        this.cityIJDistanceInfluence = cityIJDistanceInfluence;
        this.pheromoneInfluenceOnNextCitySelection = pheromoneInfluenceOnNextCitySelection;
        this.cityDistanceInfluence = cityDistanceInfluence;
        this.edgeIL_PheromoneStrength = edgeIL_PheromoneStrength;
        this.cityILDistanceInfluence = cityILDistanceInfluence;
        this.nextNode = nextNode;
        this.probability = 0.0;
    }


    public ProbabilityRecord(Intensity edgeIJ_PheromoneStrength,
                             Distance cityIJDistanceInfluence,
                             double pheromoneInfluenceOnNextCitySelection,
                             double cityDistanceInfluence,
                             List<Intensity> edgeIL_PheromoneStrength,
                             List<Distance> cityILDistanceInfluence,
                             Node nextNode,
                             double probability
    ) {
        this.edgeIJ_PheromoneStrength = edgeIJ_PheromoneStrength;
        this.cityIJDistanceInfluence = cityIJDistanceInfluence;
        this.pheromoneInfluenceOnNextCitySelection = pheromoneInfluenceOnNextCitySelection;
        this.cityDistanceInfluence = cityDistanceInfluence;
        this.edgeIL_PheromoneStrength = edgeIL_PheromoneStrength;
        this.cityILDistanceInfluence = cityILDistanceInfluence;
        this.nextNode = nextNode;
        this.probability = probability;
    }

    ;
}
