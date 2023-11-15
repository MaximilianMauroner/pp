package formicarium;

public class Arena implements Part {
    double volume() {
        return 0;
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
    public String toString() {
        return "Arena";
    }
}
