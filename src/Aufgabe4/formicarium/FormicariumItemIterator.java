package formicarium;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class FormicariumItemIterator implements Iterator<FormicariumItem> {
    private Set<FormicariumItem> items;
    private List<FormicariumItem> distinctItems;
    private int index = 0;
    private FormicariumItem lastReturned;

    // Pre: items is not null and a valid Set of FormicariumItem objects
    // Post: creates a new FormicariumItemIterator object with the given items
    public FormicariumItemIterator(Set<FormicariumItem> items) {
        this.items = items;

        distinctItems = new ArrayList<>();
        for (FormicariumItem item : items) {
             if (!distinctItems.contains(item)) {
                 distinctItems.add(item);
             }
        }
    }

    // Pre: -
    // Post: returns true if there is a next element, false otherwise
    @Override
    public boolean hasNext() {
        return index < distinctItems.size();
    }

    // Pre: -
    // Post: returns the next element if hasNext is true. Otherwise, an exception is thrown
    @Override
    public FormicariumItem next() {
        if (!hasNext()) {
            throw new IllegalStateException("No more elements");
        }

        FormicariumItem item = distinctItems.get(index++);
        lastReturned = item.clone();
        return item;
    }

    // Pre: -
    // Post: returns the number of elements, equal to the last returned
    public int count() {
        if (lastReturned == null) {
            return 0;
        }

        return (int) items.stream().filter(item -> item.equals(lastReturned)).count();
    }

    // Pre: -
    // Post: removes the last returned element
    @Override
    public void remove() {
        if (count() >= 1) {
            remove(1);
        }
    }

    // Pre: count not negative, count <= count()
    // Post: removes the last returned element count-times
    public void remove(int count) {
        if (lastReturned == null) {
            throw new IllegalStateException("No element to remove");
        }

        if (count() >= count) {
            for (int i = 0; i < count; i++) {
                items.stream().filter(item -> item.equals(lastReturned)).findFirst().ifPresent(items::remove);
            }

            if (count() == 0) {
                distinctItems.remove(lastReturned);
                index--;
            }
        }
    }
}
