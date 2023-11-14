package formicarium;

public interface Rated<P, R> {
    R rated(P p);

    void setCriterium(P p);

    R rated();
}
