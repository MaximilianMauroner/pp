package formicarium;

import java.util.Iterator;

public class CompatibilitySet<X, R> implements RatedSet<X, X, R> {
    String statistics() {
        return "";
    }

    Iterator<X> identical() {
        return null;
    }


    @Override
    public void add(Object o) {

    }

    @Override
    public void addCriterion(Object o) {

    }

    @Override
    public Iterator iterator() {
        return null;
    }

    @Override
    public Iterator iterator(Object o, Object o2) {
        return null;
    }

    @Override
    public Iterator iterator(Object o) {
        return null;
    }

    @Override
    public Iterator criterions() {
        return null;
    }
}
