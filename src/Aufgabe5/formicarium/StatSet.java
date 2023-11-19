package formicarium;

import java.util.Iterator;


public class StatSet<
        X extends Rated<? super P, R>,
        P,
        R extends Calc<R>
        > implements RatedSet<X, P, R> {
    private MyList<X> xRoot;
    private MyList<P> pRoot;
    private MyStatisticsList<String> statisticsList;
    private int calls = 0;

    private class MyList<T> {
        private MyList<T> next;
        private T value;

        public MyList(T value){
            this.value = value;
        }

        public void add(T value){
            if (identical(value)) {
                return;
            }

            if (this.value == null) {
                this.value = value;
                return;
            }

            MyList<T> curr = this;
            MyList<T> next = this.next;
            while(next != null){
                curr = next;
                next = next.next;
            }
            curr.next = new MyList<>(value);
        }

        public boolean contains(T value){
            MyList<T> curr = this;
            while(curr != null){
                if(curr.value != null && curr.value.equals(value)){
                    return true;
                }
                curr = curr.next;
            }
            return false;
        }

        public boolean identical(T value){
            MyList<T> curr = this;
            while(curr != null){
                if(curr.value != null && curr.value == value){
                    return true;
                }
                curr = curr.next;
            }
            return false;
        }

        public boolean remove(T value) {
            MyList<T> curr = this;
            MyList<T> next = this.next;
            while(next != null){
                if(curr.value != null && curr.value.equals(value)){
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
//
//        public MyStatisticsList(T value){
//            this.value = value;
//        }
//
//        public void add(T value){
//            MyStatisticsList<T> last = next;
//            MyStatisticsList<T> curr = next.next;
//            while(curr != null){
//                last = curr;
//                curr = curr.next;
//            }
//            last.next = new MyStatisticsList<>(value.toString());
//        }
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
        if (xRoot == null) {
            xRoot = new MyList<>(x);
            return;
        }
        this.xRoot.add(x);
    }

    @Override
    public void addCriterion(P p) {
        if (pRoot == null) {
            pRoot = new MyList<>(p);
            return;
        }
        this.pRoot.add(p);
    }

    @Override
    public Iterator<X> iterator() {

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

    @Override
    public Iterator<X> iterator(P p, R r) {
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

    @Override
    public Iterator<X> iterator(R r) {
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

    @Override
    public Iterator<P> criterions() {
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
        return this.xRoot.contains(x) || this.pRoot.contains(p);
    }
}
