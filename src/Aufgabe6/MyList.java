import Annotations.Author;
import Annotations.PostCondition;
import Annotations.PreCondition;

import java.util.Iterator;
import java.util.NoSuchElementException;

@Author(name = "Maximilian Mauroner")
public class MyList implements Iterable {

    private Node root;

    @PostCondition(condition = "instantiates a empty new MyList")
    @Author(name = "Lukas Leskovar")
    public MyList() {
        this.root = null;
    }

    @PreCondition(condition = "value != null")
    @PostCondition(condition = "instantiates a new MyList with the given value")
    @Author(name = "Maximilian Mauroner")
    public MyList(Object value) {
        this.root = new Node(value);
    }


    @PreCondition(condition = "value != null")
    @PostCondition(condition = "adds the given value to the MyList")
    @Author(name = "Maximilian Mauroner")
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

    @PreCondition(condition = "value != null")
    @PostCondition(condition = "true if the MyList contains an equal value, otherwise false")
    @Author(name = "Lukas Leskovar")
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

    @PreCondition(condition = "value != null")
    @PostCondition(condition = "true if the MyList contains an identical value, otherwise false")
    @Author(name = "Lukas Leskovar")
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

    @PreCondition(condition = "value != null")
    @PostCondition(condition = "removes the given value from the MyList")
    @Author(name = "Maximilian Mauroner")
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

    @PostCondition(condition = "returns a new Iterator for the MyList")
    @Author(name = "Lukas Leskovar")
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

    @PostCondition(condition = "returns a string representation of the MyList (mainly for debugging)")
    @Author(name = "Christopher Scherling")
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

    @PostCondition(condition = "returns a string representation of the MyList")
    @Author(name = "Christopher Scherling")
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


    @Author(name = "Lukas Leskovar")
    static class Node {
        Object value;
        Node next;

        @PreCondition(condition = "value != null")
        @PostCondition(condition = "instantiates a new Node with the given value")
        @Author(name = "Lukas Leskovar")
        public Node(Object value) {
            this.value = value;
        }

        @PostCondition(condition = "returns a string representation of the Node")
        @Author(name = "Christopher Scherling")
        @Override
        public String toString() {
            return value.toString();
        }
    }
}
