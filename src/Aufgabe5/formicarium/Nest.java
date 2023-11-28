package formicarium;

public class Nest implements Part {
    private double antSize;
    private String usage;
    private Part ratingCriterion;

    // Pre: usage is one of "professional", "semi-professional", "hobby"
    // Post: initializes this object with the given parameters
    public Nest(double antSize, String usage) {
        this.antSize = antSize;
        this.usage = switch(usage) {
            case "professional" -> "professional";
            case "semi-professional" -> "semi-professional";
            default -> "hobby";
        };
    }

    // Pre: -
    // Post: returns a Quality object
    @Override
    public Quality rated(Part part) {
        if (part instanceof Nest) {
            return new Quality("not applicable");
        }
        return part.rated(this);
    }

    // Pre: -
    // Post: sets the rating criterion in this to p
    @Override
    public void setCriterion(Part part) {
        ratingCriterion = part;
    }

    // Pre: -
    // Post: returns a Quality object with the rating of this and the rating criterion (if set, otherwise "not applicable")
    @Override
    public Quality rated() {
        if (ratingCriterion == null) {
            return new Quality("not applicable");
        }
        return rated(ratingCriterion);
    }

    // Pre: -
    // Post: returns the usage of this
    @Override
    public String usage() {
        return this.usage;
    }

    // Pre: -
    // Post: returns the antSize of this
    public double antSize() {
        return antSize;
    }

    // Pre: -
    // Post: returns specification of this
    @Override
    public String toString() {
        return "Nest";
    }
}
