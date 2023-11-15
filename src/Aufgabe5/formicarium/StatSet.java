package formicarium;

import java.util.Iterator;

public class StatSet<X extends Rated<P, R>, P, R> implements RatedSet<X, P, R> {

    String statistics() {
        return "";
    }

    boolean equalsObject(Object o) {
        return false;
    }



    @Override
    public void add(X x) {
        return;
    }

    @Override
    public void addCriterion(P p) {
        return;
    }

    @Override
    public Iterator<X> iterator() {

        return null;
    }

    @Override
    public Iterator<X> iterator(P p, R r) {
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
