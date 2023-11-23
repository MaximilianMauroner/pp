package formicarium;

import java.util.Iterator;

/*
 * The StatSet class implements the RatedSet interface. The type parameters are the same as in RatedSet.
 * In addition to the methods of RatedSet, there is a parameterless method 'statistics' that returns information about the number of all 
 * previous calls to all methods in this object as a string (each method listed individually), including the calls to the methods in the associated iterators.
 * Two objects of StatSet are considered equal (equals) if they contain identical entries of any kind, regardless of the order.
 */
public class StatSet<X extends Rated<? super P, R>, P, R extends Calc<R>> implements RatedSet<X, P, R> {
    protected MyList<X> xRoot;
    protected MyList<P> pRoot;
    private MyStatisticsList<String> statisticsList = new MyStatisticsList<>();
    private int calls = 0;

    private class MyList<T> {
        private MyList<T> next;
        private T value;

        public MyList(T value) {
            this.value = value;
        }

        public void add(T value) {
            if (identical(value)) {
                return;
            }

            if (this.value == null) {
                this.value = value;
                return;
            }

            MyList<T> curr = this;
            MyList<T> next = this.next;
            while (next != null) {
                curr = next;
                next = next.next;
            }
            curr.next = new MyList<>(value);
        }

        public boolean contains(T value) {
            MyList<T> curr = this;
            while (curr != null) {
                if (curr.value != null && curr.value.equals(value)) {
                    return true;
                }
                curr = curr.next;
            }
            return false;
        }

        public boolean identical(T value) {
            MyList<T> curr = this;
            while (curr != null) {
                if (curr.value != null && curr.value == value) {
                    return true;
                }
                curr = curr.next;
            }
            return false;
        }

        public boolean remove(T value) {
            MyList<T> curr = this;
            MyList<T> next = this.next;
            while (next != null) {
                if (curr.value != null && curr.value.equals(value)) {
                    curr.value = next.value;
                    curr.next = next.next;
                    return true;
                }
                curr = next;
                next = next.next;
            }
            return false;
        }
    }

    private class MyStatisticsList<T> {
        private MyStatisticsList<T> next;
        private String value;

        public MyStatisticsList(T value) {
            this.value = value.toString();
            this.next = null;
        }

        public MyStatisticsList() {
            this.next = null;
            this.value = null;
        }


        public void add(T value) {
            if (this.value == null) {
                this.value = value.toString();
            } else if (next == null) {
                // If the list is empty, add the new value as the next node
                next = new MyStatisticsList<>(value);
            } else {
                MyStatisticsList<T> last = this;
                MyStatisticsList<T> curr = next;
                while (curr != null) {
                    last = curr;
                    curr = curr.next;
                }
                last.next = new MyStatisticsList<>(value);
            }
        }
    }

    // Post: Returns information about the number of all previous calls to all methods in this object as a string (each method listed individually), 
    // including the calls to the methods in the associated iterators.
    public String statistics() {
        statisticsList.add("statistics");
        StringBuilder val = new StringBuilder();
        MyStatisticsList<String> temp = this.statisticsList;
        while (temp != null && temp.value != null) {
            val.append(temp.value);
            if (temp.next != null) {
                val.append("\n");
            }
            temp = temp.next;
        }
        return val.toString();
    }


    public boolean equals(Object other) {
        statisticsList.add("equals");
        if (other == null) {
            return false;
        }

        if (other instanceof StatSet<?, ?, ?>) {
            StatSet<?, ?, ?> o = (StatSet<?, ?, ?>) other;

            for (Object x : this) {
                boolean contained = false;
                for (Object y : o) {
                    if (x == y) {
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
                Iterator<?> oPIterator = o.criterions();
                while (oPIterator.hasNext()) {
                    Object oP = oPIterator.next();
                    if (p == oP) {
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

    // Pre: x is a non-null object of type X.
    // Post: Ensures that x is an entry in the container. If an identical object already exists in the container, it is not inserted again.
    @Override
    public void add(X x) {
        statisticsList.add("add X");

        if (xRoot == null) {
            xRoot = new MyList<>(x);
            return;
        }
        this.xRoot.add(x);
    }

    // Pre: p is a non-null object of type P.
    // Post: Works like add, but deals with entries of type P. Identical objects inserted using addCriterion are not allowed to occur multiple times.
    @Override
    public void addCriterion(P p) {
        statisticsList.add("addCriterion P");
        if (pRoot == null) {
            pRoot = new MyList<>(p);
            return;
        }
        this.pRoot.add(p);
    }

    // Post: Returns an iterator that runs in any order over all entries in the container that were inserted using add.
    @Override
    public Iterator<X> iterator() {
        statisticsList.add("iterator X");
        return new Iterator<>() {
            private MyList<X> current = StatSet.this.xRoot;
            private MyList<X> last = null;

            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public X next() throws IllegalStateException {
                if (!hasNext()) {
                    throw new IllegalStateException("No more elements");
                }
                last = current;
                current = current.next;
                return last.value;
            }

            public void remove() {
                if (last != null && StatSet.this.xRoot.remove(last.value)) {
                    last = null;
                }
            }
        };
    }

    // Pre: p is a non-null object of type P and r is a non-null object of type R.
    // Post: Returns an iterator that runs in any order over all entries x in the container that were 
    // inserted using add and for which x.rated(p) delivers a result that is greater than or equal to r.
    @Override
    public Iterator<X> iterator(P p, R r) {
        statisticsList.add("iterator P R");
        return new Iterator<>() {

            {
                for (X x : StatSet.this) {
                    if (ratedHelper(x)) {
                        if (this.root == null) {
                            this.root = new MyList<>(x);
                        } else if (!this.root.contains(x)) {
                            this.root.add(x);
                        }
                    }
                }
            }

            private MyList<X> root;
            private MyList<X> current = root;
            private MyList<X> last = null;

            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public X next() {
                if (!hasNext()) {
                    throw new IllegalStateException("No more elements");
                }

                last = current;
                current = current.next;
                return last.value;
            }

            // Pre: current != null
            // Post: true, if the rating of value is at least r. false otherwise
            private boolean ratedHelper(X value) {
                return value.rated(p).atleast(r);
            }

            public void remove() {
                if (last != null && StatSet.this.xRoot.remove(last.value)) {
                    last = null;
                }
            }
        };
    }

    // Pre: r is a non-null object of type R.
    // Post: Returns an iterator that runs in any order over all entries x in the container that were 
    // inserted using add and for which the average of all values determined by x.rated(p) 
    // (for all entries p in the container inserted using addCriterion) is greater than or equal to r.
    @Override
    public Iterator<X> iterator(R r) {
        statisticsList.add("iterator R");
        return new Iterator<>() {

            {
                for (X x : StatSet.this) {
                    R average = null;
                    int count = 0;
                    for (Iterator<P> it = StatSet.this.criterions(); it.hasNext(); ) {
                        P p = it.next();
                        if (average == null) {
                            average = x.rated(p);
                        } else {
                            average = average.sum(x.rated(p));
                        }
                        count++;
                    }


                    if (average != null && average.ratio(count).atleast(r)) {
                        if (this.root == null) {
                            this.root = new MyList<>(x);
                        } else {
                            this.root.add(x);
                        }
                    }
                }
            }

            private MyList<X> root;
            private MyList<X> current = root;
            private MyList<X> last = null;

            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public X next() {
                if (!hasNext()) {
                    throw new IllegalStateException("No more elements");
                }

                last = current;
                current = current.next;
                return last.value;
            }

            public void remove() {
                if (last != null && StatSet.this.xRoot.remove(last.value)) {
                    last = null;
                }

            }
        };
    }

    // Post: Returns an iterator that runs in any order over all entries in the container that were inserted using addCriterion.
    @Override
    public Iterator<P> criterions() {
        statisticsList.add("criterions P");

        return new Iterator<>() {
            private MyList<P> current = StatSet.this.pRoot;
            private MyList<P> last = null;

            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public P next() throws IllegalStateException {
                if (!hasNext()) {
                    throw new IllegalStateException("No more elements");
                }
                last = current;
                current = current.next;
                return last.value;
            }

            public void remove() {
                if (last != null && StatSet.this.pRoot.remove(last.value)) {
                    last = null;
                }
            }
        };
    }


    // Pre: x != null && p != null
    // Post: true, if x or p is contained in this. false if neither x nor p is contained in this
    protected boolean contains(X x, P p) {
        statisticsList.add("contains X P");

        return this.xRoot.contains(x) || this.pRoot.contains(p);
    }

    protected void addStatistic(String message) {
        statisticsList.add(message);
    }
}
