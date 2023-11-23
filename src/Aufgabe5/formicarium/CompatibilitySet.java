package formicarium;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class CompatibilitySet<X extends Rated<? super X, R>, R extends Calc<R>> extends StatSet<X, X, R> {

    // Pre: -
    // Post: returns true if this and o are equal (i.e. contain the same elements no matter in which order or list they are stored)
    public boolean equals(Object other) {
        addStatistic("equals");
        if (other == null) {
            return false;
        }

        if (!(other instanceof StatSet)) {
            return false;
        }

        CompatibilitySet<X, R> o = (CompatibilitySet<X, R>) other;

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

    // Pre: x != null
    // Post: returns true if this contains x (either as a criterion or as an element)
    private boolean contains(X x) {
        return contains(x, x);
    }

    // Pre: -
    // Post: returns an iterator over all elements of this that are also in criterions
    public Iterator<X> identical() {
        addStatistic("identical");
        return new Iterator<>() {
            Iterator<X> iter = CompatibilitySet.this.iterator();
            Iterator<X> criterionIter = CompatibilitySet.this.criterions();
            X next = null;

            // Pre: -
            // Post: returns true if the iteration has more elements
            @Override
            public boolean hasNext() {
                addStatistic("identical.hasNext");
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

            // Pre: -
            // Post: returns the next element in the iteration
            @Override
            public X next() {
                addStatistic("identical.next");
                if (next == null) {
                    throw new NoSuchElementException("No more elements");
                }
                return next;
            }
        };
    }
}
