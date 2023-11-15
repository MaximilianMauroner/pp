package formicarium;

import java.util.Iterator;


public class StatSet<X extends Rated<?, R>, P, R> implements RatedSet<X, P, R> {
    private MyList<X> xRoot;
    private MyList<P> pRoot;

    class MyList<T> {
        private MyList<T> next;
        private T t;

        public MyList(T t){
            this.t = t;
        }

        public void add(T t){
            MyList<T> last = next;
            MyList<T> curr = next.next;
            while(curr != null){
                last = curr;
                curr = curr.next;
            }
            last.next = new MyList<>(t);
        }

    }

    String statistics() {
        return "";
    }


    boolean equalsObject(Object o) {
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
            private MyList<X> next = StatSet.this.xRoot;

            @Override
            public boolean hasNext() {
                return next.next != null;
            }

            @Override
            public X next() {
                next = next.next;
                return next.t;
            }
        };
    }

    @Override
    public Iterator<X> iterator(P p, R r) {
        return new Iterator<>() {

            private MyList<X> next = StatSet.this.xRoot;

            @Override
            public boolean hasNext() {
                return false;
            }

            @Override
            public X next() {
                return null;
            }
        };
    }

    @Override
    public Iterator<X> iterator(R r) {
        return null;
    }

    @Override
    public Iterator<P> criterions() {
        return new Iterator<>() {
            private MyList<P> next = StatSet.this.pRoot;

            @Override
            public boolean hasNext() {
                return next.next != null;
            }

            @Override
            public P next() {
                next = next.next;
                return next.t;
            }
        };
    }


}
