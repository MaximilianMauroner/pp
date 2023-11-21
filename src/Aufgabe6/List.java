public class List<T> {
    private List<T> next;
    private T value;

    public List(T value) {
        this.value = value;
    }

    public void add(T value) {
        if (identical(value)) {
            return;
        }

        List<T> last = next;
        List<T> curr = next.next;
        while (curr != null) {
            last = curr;
            curr = curr.next;
        }
        last.next = new List<>(value);
    }

    public boolean contains(T value) {
        List<T> curr = next;
        while (curr != null) {
            if (curr.value.equals(value)) {
                return true;
            }
            curr = curr.next;
        }
        return false;
    }

    public boolean identical(T value) {
        List<T> curr = next;
        while (curr != null) {
            if (curr.value == value) {
                return true;
            }
            curr = curr.next;
        }
        return false;
    }

    public boolean remove(T value) {
        List<T> curr = next;
        List<T> last = next;
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
