package formicarium;

public class Part implements Rated<Part, Quality> {

    @Override
    public String toString() {
        return "";
    }

    @Override
    public Quality rated(Part part) {
        return null;
    }

    @Override
    public void setCriterium(Part part) {

    }

    @Override
    public Quality rated() {
        return null;
    }
}
