package formicarium;

public class Arena implements Part {
    private double volume;
    private String usage;
    private Part ratingCriterion;

    // Pre: usage is one of "professional", "semi-professional", "hobby"
    // Post: initializes this object with the given parameters
    public Arena(double volume, String usage) {
        this.volume = volume;

        this.usage = switch(usage) {
            case "professional" -> "professional";
            case "semi-professional" -> "semi-professional";
            default -> "hobby";
        };
    }


    // Pre: -
    // Post: returns a Quality object
    @Override
    public Quality rated(Part p) {
        int thisRanking = getRanking(this.usage);
        int otherRanking = getRanking(p.usage());

        if (thisRanking <= otherRanking) {
            return new Quality(this.usage);
        } else {
            return new Quality(p.usage());
        }
    }

    // Pre: -
    // Post: sets the rating criterion in this to p
    @Override
    public void setCriterion(Part p) {
        ratingCriterion = p;
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
    // Post: returns the volume of this
    public double volume() {
        return volume;
    }

    // Pre: -
    // Post: returns specification of this
    @Override
    public String toString() {
        return "Arena";
    }


    // Pre: quality is one of "professional", "semi-professional", "hobby"
    // Post: returns the numeric ranking of the given quality
    private int getRanking(String quality) {
        return switch(quality) {
            case "professional" -> 3;
            case "semi-professional" -> 2;
            default -> 1;
        };
    }

}
