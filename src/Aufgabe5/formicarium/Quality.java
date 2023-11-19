package formicarium;

public class Quality implements Calc<Quality> {
    private final String quality;

    public Quality(String quality) {
        this.quality = switch(quality) {
            case "professional" -> "professional";
            case "semi-professional" -> "semi-professional";
            case "hobby" -> "hobby";
            default -> "not applicable";
        };
    }

    @Override
    public Quality sum(Quality r) {
        int thisRanking = getRanking(this.quality);
        int otherRanking = getRanking(r.quality);
        return thisRanking > otherRanking ? r : this;
    }

    @Override
    public Quality ratio(int i) {
        return this;
    }

    @Override
    public boolean atleast(Quality r) {
        int thisRanking = getRanking(this.quality);
        int otherRanking = getRanking(r.quality);
        return thisRanking >= otherRanking;
    }

    @Override
    public String toString() {
        return this.quality;
    }

    private int getRanking(String quality) {
        return switch(quality) {
            case "professional" -> 3;
            case "semi-professional" -> 2;
            case "hobby" -> 1;
            default -> 0;
        };
    }
}
