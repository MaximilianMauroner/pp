package formicarium;

public class Nest implements Part {
    Numeric antSize() {
        return new Numeric(0);
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
        return null;
    }

    @Override
    public void setCriterion(Part part) {

    }

    @Override
    public Numeric value() {
        return antSize();
    }
}
