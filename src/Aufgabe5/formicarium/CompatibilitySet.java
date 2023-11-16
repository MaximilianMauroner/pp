package formicarium;

import java.util.Iterator;

public class CompatibilitySet<
        X extends Rated<? super X, R>,
        R extends Calc<R>
        > implements RatedSet<X, X, R> {

    String statistics() {
        // ToDo: Implement this method
        return "";
    }

    Iterator<X> identical() {
        // ToDo: Implement this method
        return null;
    }


    @Override
    public void add(X x) {
        // ToDo: Implement this method
    }

    @Override
    public void addCriterion(X x) {
        add(x);
    }

    @Override
    public Iterator<X> iterator() {
        // ToDo: Implement this method
        return null;
    }

    @Override
    public Iterator<X> iterator(X x, R r) {
        // ToDo: Implement this method
        return null;
    }

    @Override
    public Iterator<X> iterator(R r) {
        // ToDo: Implement this method
        return null;
    }

    @Override
    public Iterator<X> criterions() {
        // ToDo: Implement this method
        return null;
    }
}
