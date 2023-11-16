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
        private MyList<T> next;
        private T value;

        public MyList(T value){
            this.value = value;
        }

        public void add(T value){
            MyList<T> last = next;
            MyList<T> curr = next.next;
            while(curr != null){
                last = curr;
                curr = curr.next;
            }
            last.next = new MyList<>(value);
        }

    }

    String statistics() {
        // ToDo: Implement this method
        return "";
    }


    boolean equalsObject(StatSet<X, P, R> o) {
        return false;
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
                current = current.next;
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
                    current = current.next;
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
                current = current.next;
                return current.value;
            }

            public void remove() {
                // ToDo: Implement this method
            }
        };
    }


}
