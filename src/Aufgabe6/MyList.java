import java.util.Iterator;
import java.util.NoSuchElementException;

public class MyList implements Iterable {

    private Node root;

    public MyList() {
        this.root = null;
    }

    public MyList(Object value) {
        this.root = new Node(value);
    }

    public void add(Object value) {
        if (root == null) {
            root = new Node(value);
        } else {
            Node node = root;
            while (node.next != null) {
                node = node.next;
            }
            node.next = new Node(value);
        }
    }

    public boolean contains(Object value) {
        Node node = root;
        while (node != null) {
            if (node.value.equals(value)) {
                return true;
            }
            node = node.next;
        }
        return false;
    }

    public boolean identical(Object value) {
        Node node = root;
        while (node != null) {
            if (node.value == value) {
                return true;
            }
            node = node.next;
        }
        return false;
    }

    public void remove(Object value) {
        if (root == null) {
            return;
        }
        if (root.value.equals(value)) {
            root = root.next;
            return;
        }
        Node node = root;
        while (node.next != null) {
            if (node.next.value.equals(value)) {
                node.next = node.next.next;
                return;
            }
            node = node.next;
        }
    }

    @Override
    public Iterator iterator() {
        return new Iterator() {

            Node node = root;

            @Override
            public boolean hasNext() {
                return node != null;
            }

            @Override
            public Object next() {
                if (!hasNext()) {
                    throw new NoSuchElementException("No more elements");
                }

                Object value = node.value;
                node = node.next;
                return value;
            }
        };
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("[");
        Node node = root;
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
        Node node = root;
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


    static class Node {
        Object value;
        Node next;

        public Node(Object value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return value.toString();
        }
    }
}
