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
        if (other == this) {
            return true;
        }

        // this would work even if the other object is a StatSet<X, X, R>
        // but the results of equals would depend on which object the method is called on
        if (other instanceof CompatibilitySet<?, ?> o) {

            // if the added elements and the criterions are the same (just like in StatSet)
            if (super.equals(o)) {
                return true;
            }

            // if they are mixed up (e.g. not added using the same method)
            for (Object x : this) {
                boolean contained = false;

                Iterator<?> opIterator = o.criterions();
                while (opIterator.hasNext()) {
                    Object op = opIterator.next();
                    if (x == op) {
                        contained = true;
                        break;
                    }
                }

                if (!contained) {
                    return false;
                }
            }

            Iterator<?> pIterator = this.criterions();
            while (pIterator.hasNext()) {
                Object p = pIterator.next();
                boolean contained = false;

                for (Object ox : o) {
                    if (p == ox) {
                        contained = true;
                        break;
                    }
                }

                if (!contained) {
                    return false;
                }
            }
            return true;
        }
        return false;
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
