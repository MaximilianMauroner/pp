package formicarium;

/*
 * The Quality class represents a quality level for a certain part. It implements the Calc interface with Quality as the type parameter.
 * Each Quality object has a quality string that can be "professional", "semi-professional", "hobby", or "not applicable".
 * The Quality class provides methods to compare and manipulate Quality objects.
 */
public class Quality implements Calc<Quality> {
    private final String quality;

    // Pre: quality is a non-null string.
    // Post: Initializes a new Quality object with the given quality string. If the quality string does not match any of the predefined qualities, it is set to "not applicable".
    public Quality(String quality) {
        this.quality = switch(quality) {
            case "professional" -> "professional";
            case "semi-professional" -> "semi-professional";
            case "hobby" -> "hobby";
            default -> "not applicable";
        };
    }

    // Pre: -
    // Post: Returns a string representation of the Quality object. The returned string is never null or empty.
    @Override
    public String toString() {
        return this.quality;
    }

    // Pre: The Quality r is initialized.
    // Post: Returns the Quality object with the higher ranking. If the rankings are equal, returns the parameter r.
    @Override
    public Quality sum(Quality r) {
        int thisRanking = getRanking(this.quality);
        int otherRanking = getRanking(r.quality);
        return thisRanking > otherRanking ? r : this;
    }

    // Pre: i is a non-negative integer.
    // Post: Returns the current Quality object. The parameter i is ignored.
    @Override
    public Quality ratio(int i) {
        return this;
    }

    // Pre: parameter r is initialized.
    // Post: Returns true if the ranking of the current Quality object is greater than or equal to the ranking of r. Otherwise, returns false.
    @Override
    public boolean atleast(Quality r) {
        int thisRanking = getRanking(this.quality);
        int otherRanking = getRanking(r.quality);
        return thisRanking >= otherRanking;
    }

    // Pre: quality is a non-null string.
    // Post: Returns the ranking of the quality string. If the quality string does not match any of the predefined qualities, returns 0.
    private int getRanking(String quality) {
        return switch(quality) {
            case "professional" -> 3;
            case "semi-professional" -> 2;
            case "hobby" -> 1;
            default -> 0;
        };
    }
}