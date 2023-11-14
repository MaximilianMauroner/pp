package formicarium;

import java.util.Iterator;

public interface RatedSet<X, P, R> extends Iterable {
    void add(X x);

    void addCriterion(P p);

    Iterator<X> iterator();

    Iterator<X> iterator(P p, R r);

    Iterator<X> iterator(R r);

    Iterator<X> criterions();

}
