package formicarium;

public class Nest implements Part {
    double antSize() {
        return 0;
    }

    @Override
    public Quality rated(Part part) {
        return null;
    }

    @Override
    public Quality rated() {
        return null;
    }

    @Override
    public void setCriterium(Part part) {

    }
}
