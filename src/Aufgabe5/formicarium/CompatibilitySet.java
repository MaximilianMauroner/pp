package formicarium;

import java.util.Iterator;

public class CompatibilitySet<
        X extends Rated<? super X, R>,
        R extends Calc<R>
        > implements RatedSet<X, X, R> {
    String statistics() {
        return "";
    }

    Iterator<X> identical() {
        return null;
    }


    @Override
    public void add(X x) {

    }

    @Override
    public void addCriterion(X x) {

    }

    @Override
    public Iterator<X> iterator() {
        return null;
    }

    @Override
    public Iterator<X> iterator(X x, R r) {
        return null;
    }

    @Override
    public Iterator<X> iterator(R r) {
        return null;
    }

    @Override
    public Iterator<X> criterions() {
        return null;
    }
}
