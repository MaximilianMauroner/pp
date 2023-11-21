package formicarium;

import java.util.Iterator;

public class CompatibilitySet<X extends Rated<? super X, R>, R extends Calc<R>> extends StatSet<X, X, R> {

    public boolean equals(CompatibilitySet<X, R> o) {
        Iterator<X> xIterator = o.iterator();
        Iterator<X> pIterator = o.criterions();
        while (xIterator.hasNext()) {
            X x = xIterator.next();
            if (!contains(x)) {
                return false;
            }
        }
        while (pIterator.hasNext()) {
            X x = pIterator.next();
            if (!contains(x)) {
                return false;
            }
        }
        return true;
    }

    private boolean contains(X x) {
        return super.contains(x, x);
    }

    public Iterator<X> identical() {
        return new Iterator<>() {
            Iterator<X> iter = CompatibilitySet.this.iterator();
            Iterator<X> criterionIter = CompatibilitySet.this.criterions();
            X next = null;

            @Override
            public boolean hasNext() {
                while (iter.hasNext()) {
                    X x = iter.next();
                    while (criterionIter.hasNext()) {
                        X criterion = criterionIter.next();
                        if (x.equals(criterion)) {
                            next = x;
                            return true;
                        }
                    }
                }
                return false;
            }

            @Override
            public X next() {
                return next;
            }
        };
    }
}
