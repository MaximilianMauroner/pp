package formicarium;

import java.util.Iterator;

public class StatSet<X, P, R> implements RatedSet {

    String statistics() {
        return "";
    }

    boolean equalsObject(Object o) {
        return false;
    }



    @Override
    public void add(Object o) {
        return;
    }

    @Override
    public void addCriterion(Object o) {
        return;
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
