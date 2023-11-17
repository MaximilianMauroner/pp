package formicarium;

public class Arena implements Part {
    Numeric volume() {
        return new Numeric(0);
    }

    @Override
    public Quality rated(Part p) {
        return null;
    }

    @Override
    public Quality rated() {
        return null;
    }

    @Override
    public void setCriterion(Part p) {

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
