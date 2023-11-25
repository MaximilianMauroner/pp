import java.util.Iterator;
import java.util.NoSuchElementException;

public class MyList<T> implements Iterable<T> {

    private Node<T> root;

    public MyList() {
        this.root = null;
    }

    public MyList(T value) {
        this.root = new Node<>(value);
    }

    public void add(T value) {
        if (root == null) {
            root = new Node<>(value);
        } else {
            Node<T> node = root;
            while (node.next != null) {
                node = node.next;
            }
            node.next = new Node<>(value);
        }
    }

    public boolean contains(T value) {
        Node<T> node = root;
        while (node != null) {
            if (node.value.equals(value)) {
                return true;
            }
            node = node.next;
        }
        return false;
    }

    public boolean identical(T value) {
        Node<T> node = root;
        while (node != null) {
            if (node.value == value) {
                return true;
            }
            node = node.next;
        }
        return false;
    }

    public void remove(T value) {
        if (root == null) {
            return;
        }
        if (root.value.equals(value)) {
            root = root.next;
            return;
        }
        Node<T> node = root;
        while (node.next != null) {
            if (node.next.value.equals(value)) {
                node.next = node.next.next;
                return;
            }
            node = node.next;
        }
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {

            Node<T> node = root;

            @Override
            public boolean hasNext() {
                return node != null;
            }

            @Override
            public T next() {
                if (!hasNext()) {
                    throw new NoSuchElementException("No more elements");
                }

                T value = node.value;
                node = node.next;
                return value;
            }
        };
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("[");
        Node<T> node = root;
        while (node != null) {
            builder.append(node.value);
            if (node.next != null) {
                builder.append(", ");
            }
            node = node.next;
        }
        builder.append("]");
        return builder.toString();
    }

    public String print() {
        StringBuilder builder = new StringBuilder();
        builder.append("[");
        Node<T> node = root;
        while (node != null) {
            if (node.value instanceof Formicarium) {
                builder.append(((Formicarium) node.value).print());
                if (node.next != null) {
                    builder.append(", ");
                }
                node = node.next;
            }
        }
        builder.append("]");
        return builder.toString();
    }


    static class Node<T> {
        T value;
        Node<T> next;

        public Node(T value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return value.toString();
        }
    }
}
