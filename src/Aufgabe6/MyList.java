import java.util.Iterator;
import java.util.NoSuchElementException;

public class MyList<T> implements Iterable<T> {
    private MyList<T> next;
    private T value;

    public MyList(T value) {
        this.value = value;
    }

    public MyList() {
    }

    public void add(T value) {
        if (this.value == null) {
            this.value = value;
            return;
        } else if (next == null) {
            next = new MyList<>(value);
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

    @Override
    public Iterator<T> iterator() {
        return new Iterator<>() {
            private MyList<T> curr = MyList.this;

            @Override
            public boolean hasNext() {
                return curr != null;
            }

            @Override
            public T next() {
                if (curr == null) {
                    throw new NoSuchElementException("No more elements");
                }
                T value = curr.value;
                curr = curr.next;
                return value;
            }
        };
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        MyList<T> curr = next;

        while (curr != null) {
            sb.append(curr.value.toString());
            curr = curr.next;
            if (curr != null) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }
}
