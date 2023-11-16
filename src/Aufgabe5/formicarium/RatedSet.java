package formicarium;

import java.util.Iterator;

public interface RatedSet<
        X extends Rated<? super P, R>,
        P,
        R extends Calc<R>
        > extends Iterable<X> {
    void add(X x);

    void addCriterion(P p);

    Iterator<X> iterator();

    Iterator<X> iterator(P p, R r);

    Iterator<X> iterator(R r);

    Iterator<P> criterions();

}
