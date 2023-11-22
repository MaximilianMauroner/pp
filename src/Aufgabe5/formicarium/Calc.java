package formicarium;

public interface Calc<R extends Calc<R>> {
    // Pre: r != null
    // Post: returns a new instance of R with the sum of this and r (not necessarily numeric addition)
    R sum(R r);

    // Pre: i != 0
    // Post: returns a new instance of R with the division this with i (not necessarily numeric division)
    R ratio(int i);

    // Pre: r != null
    // Post: returns true if this is at least as big as r (not necessarily numeric comparison)
    boolean atleast(R r);
}
