package formicarium;

import java.util.Iterator;


public class StatSet<
        X extends Rated<? super P, R>,
        P,
        R extends Calc<R>
        > implements RatedSet<X, P, R> {
    private MyList<X> xRoot;
    private MyList<P> pRoot;
    private int calls = 0;

    class MyList<T> {
        private MyList<T> root;
        private T value;

        public MyList(T value){
            this.value = value;
        }

        public void add(T value){
            MyList<T> last = root;
            MyList<T> curr = root.root;
            while(curr != null){
                last = curr;
                curr = curr.root;
            }
            last.root = new MyList<>(value);
        }

        public boolean contains(T value){
            MyList<T> curr = root;
            while(curr != null){
                if(curr.value.equals(value)){
                    return true;
                }
                curr = curr.root;
            }
            return false;
        }
    }

    public String statistics() {
        // ToDo: Implement this method
        return "";
    }


    public boolean equals(StatSet<X, P, R> o) {
        Iterator<X> xIterator = o.iterator();
        Iterator<P> pIterator = o.criterions();
        while (xIterator.hasNext()) {
            X x = xIterator.next();
            if (!this.xRoot.contains(x)) {
                return false;
            }
        }
        while (pIterator.hasNext()) {
            P p = pIterator.next();
            if (!this.pRoot.contains(p)) {
                return false;
            }
        }
        return true;
    }


    @Override
    public void add(X x) {
        this.xRoot.add(x);
    }

    @Override
    public void addCriterion(P p) {
        this.pRoot.add(p);
    }

    @Override
    public Iterator<X> iterator() {

        return new Iterator<>() {
            private MyList<X> current = StatSet.this.xRoot;

            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public X next() throws IllegalStateException {
                if (!hasNext()) {
                    throw new IllegalStateException("No more elements");
                }
                X t = current.value;
                current = current.root;
                return t;
            }

            public void remove() {
                // ToDo: Implement this method
            }
        };
    }

    @Override
    public Iterator<X> iterator(P p, R r) {
        return new Iterator<>() {

            private MyList<X> current = StatSet.this.xRoot;

            @Override
            public boolean hasNext() {
                while (current != null && !ratedHelper()) {
                    current = current.root;
                }
                return current != null;
            }

            @Override
            public X next() {
                if (!hasNext()) {
                    throw new IllegalStateException("No more elements");
                }
                return current.value;
            }

            // Pre: current != null
            // Post: true, if the rating of value is at least r. false otherwise
            private boolean ratedHelper() {
                return current.value.rated(p).atleast(r);
            }

            public void remove() {
                // ToDo: Implement this method
            }
        };
    }

    @Override
    public Iterator<X> iterator(R r) {
        return new Iterator<X>() {
            @Override
            public boolean hasNext() {
                return false;
            }

            @Override
            public X next() {
                return null;
            }

            public void remove() {
                // ToDo: Implement this method
            }
        };
    }

    @Override
    public Iterator<P> criterions() {
        return new Iterator<>() {
            private MyList<P> current = StatSet.this.pRoot;

            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public P next() throws IllegalStateException {
                if (!hasNext()) {
                    throw new IllegalStateException("No more elements");
                }
                current = current.root;
                return current.value;
            }

            public void remove() {
                // ToDo: Implement this method
            }
        };
    }

    // Pre: x != null && p != null
    // Post: true, if x or p is contained in this. false if neither x nor p is contained in this
    protected boolean contains(X x, P p) {
        return this.xRoot.contains(x) || this.pRoot.contains(p);
    }
}
