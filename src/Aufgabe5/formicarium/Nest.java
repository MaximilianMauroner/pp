package formicarium;

public class Nest implements Part {
    private double antSize;
    private String usage;
    private Part ratingCriterion;

    public Nest(double antSize, String usage) {
        this.antSize = antSize;
        this.usage = switch(usage) {
            case "professional" -> "professional";
            case "semi-professional" -> "semi-professional";
            default -> "hobby";
        };
    }

    double antSize() {
        return antSize;
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
    public String usage() {
        return this.usage;
    }

    @Override
    public double value() {
        return antSize();
    }
}