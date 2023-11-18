package formicarium;

public class Arena implements Part {
    private double volume;
    private Part ratingCriterion;

    public Arena(double volume) {
        this.volume = volume;
    }

    Numeric volume() {
        return new Numeric(volume);
    }

    @Override
    public Quality rated(Part p) {
        return null;
    }

    @Override
    public Quality rated() {
        if (ratingCriterion == null) {
            return new Quality("not applicable");
        }
        return rated(ratingCriterion);
    }

    @Override
    public void setCriterion(Part p) {
        ratingCriterion = p;
    }

    @Override
    public Numeric value() {
        return volume();
    }

    @Override
    public String toString() {
        return "Arena";
    }
}
