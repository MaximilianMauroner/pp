package formicarium;

public interface Rated<P, R extends Calc<R>> {
    R rated(P p);

    void setCriterion(P p);

    R rated();
}
