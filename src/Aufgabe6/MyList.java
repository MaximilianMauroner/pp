public class MyList<T> {
    private MyList<T> next;
    private T value;

    public MyList(T value) {
        this.value = value;
    }

    public MyList() {

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
