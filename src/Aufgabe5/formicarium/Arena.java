package formicarium;

public class Arena implements Part {
    private double volume;
    private String usage;
    private Part ratingCriterion;

    public Arena(double volume, String usage) {
        this.volume = volume;

        this.usage = switch(usage) {
            case "professional" -> "professional";
            case "semi-professional" -> "semi-professional";
            default -> "hobby";
        };
    }

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

    @Override
    public void setCriterion(Part p) {
        ratingCriterion = p;
    }

    @Override
    public Quality rated() {
        if (ratingCriterion == null) {
            return new Quality("not applicable");
        }
        return rated(ratingCriterion);
    }

    @Override
    public String usage() {
        return this.usage;
    }

    public double volume() {
        return volume;
    }

    @Override
    public String toString() {
        return "Arena";
    }

    private int getRanking(String quality) {
        return switch(quality) {
            case "professional" -> 3;
            case "semi-professional" -> 2;
            default -> 1;
        };
    }

}
