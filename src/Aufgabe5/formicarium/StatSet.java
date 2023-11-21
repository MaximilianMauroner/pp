package formicarium;

import java.util.Iterator;


public class StatSet<X extends Rated<? super P, R>, P, R extends Calc<R>> implements RatedSet<X, P, R> {
    private MyList<X> xRoot;
    private MyList<P> pRoot;
    private MyStatisticsList<String> statisticsList = new MyStatisticsList<>();
    private int calls = 0;

    class MyList<T> {
        private MyList<T> next;
        private T value;

        public MyList(T value) {
            this.value = value;
        }

        public void add(T value) {
            if (identical(value)) {
                return;
            }

            MyList<T> last = next;
            MyList<T> curr = next.next;
            while (curr != null) {
                last = curr;
                curr = curr.next;
            }
            last.next = new MyList<>(value);
        }

        public boolean contains(T value) {
            MyList<T> curr = next;
            while (curr != null) {
                if (curr.value.equals(value)) {
                    return true;
                }
                curr = curr.next;
            }
            return false;
        }

        public boolean identical(T value) {
            MyList<T> curr = next;
            while (curr != null) {
                if (curr.value == value) {
                    return true;
                }
                curr = curr.next;
            }
            return false;
        }

        public boolean remove(T value) {
            MyList<T> curr = next;
            MyList<T> last = next;
            while (curr != null) {
                if (curr.value.equals(value)) {
                    last.next = curr.next;
                    return true;
                }
                last = curr;
                curr = curr.next;
            }

            return false;
        }
    }

    class MyStatisticsList<T> {
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


    public boolean equals(StatSet<X, P, R> o) {
        statisticsList.add("equals");
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
        statisticsList.add("add X");

        this.xRoot.add(x);
    }

    @Override
    public void addCriterion(P p) {
        statisticsList.add("addCriterion P");
        this.pRoot.add(p);
    }

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
                X t = current.value;
                last = current;
                current = current.next;
                return t;
            }

            public void remove() {
                if (last == null) {
                    throw new IllegalStateException("No element to remove");
                }
                if (StatSet.this.xRoot.remove(last.value)) {
                    last = null;
                }
            }
        };
    }

    @Override
    public Iterator<X> iterator(P p, R r) {
        statisticsList.add("iterator P R");
        return new Iterator<>() {

            {
                for (X x : StatSet.this) {
                    if (ratedHelper()) {
                        if (this.root == null) {
                            this.root = new MyList<>(x);
                        } else if (!this.root.contains(x)) {
                            this.root.add(x);
                        }
                    }
                }
            }

            private MyList<X> root;
            private MyList<X> current = StatSet.this.xRoot;
            private MyList<X> last = null;

            @Override
            public boolean hasNext() {
                last = current;
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
                if (last == null) {
                    throw new IllegalStateException("No element to remove");
                }

                if (StatSet.this.xRoot.remove(last.value)) {
                    last = null;
                }
            }
        };
    }

    @Override
    public Iterator<X> iterator(R r) {
        statisticsList.add("iterator R");
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
        statisticsList.add("criterions P");

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

    // Pre: x != null && p != null
    // Post: true, if x or p is contained in this. false if neither x nor p is contained in this
    protected boolean contains(X x, P p) {
        statisticsList.add("contains X P");

        return this.xRoot.contains(x) || this.pRoot.contains(p);
    }
}
