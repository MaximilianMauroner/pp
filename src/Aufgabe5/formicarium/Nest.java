package formicarium;

public class Nest implements Part {
    private double antSize;
    private Part ratingCriterion;

    public Nest(double antSize) {
        this.antSize = antSize;
    }

    Numeric antSize() {
        return new Numeric(antSize);
    }

    @Override
    public Quality rated(Part part) {
        if (part instanceof Nest) {
            return new Quality("not applicable");
        }
        return part.rated(this);
    }

    @Override
    public Quality rated() {
        if (ratingCriterion == null) {
            return new Quality("not applicable");
        }
        return rated(ratingCriterion);
    }

    @Override
    public void setCriterion(Part part) {
        ratingCriterion = part;
    }

    @Override
    public Numeric value() {
        return antSize();
    }
}
