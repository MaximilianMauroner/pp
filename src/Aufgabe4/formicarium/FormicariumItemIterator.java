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
    private Class<? extends FormicariumItem> lastReturnedClass;

    // Pre: items is not null and a valid Set of FormicariumItem objects
    // Post: creates a new FormicariumItemIterator object with the given items
    public FormicariumItemIterator(Set<FormicariumItem> items) {
        this.items = items;

        distinctItems = new ArrayList<>(items.stream()
                .collect(Collectors.groupingBy(FormicariumItem::getClass,
                        Collectors.collectingAndThen(Collectors.toList(), list -> list.get(0)))
                )
                .values());
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
        lastReturnedClass = item.getClass();
        return item;
    }

    // Pre: -
    // Post: returns the number of elements, of the last returned class
    public int count() {
        if (lastReturnedClass == null) {
            return 0;
        }
        return (int) items.stream().filter(item -> item.getClass().equals(lastReturnedClass)).count();
    }

    // Pre: -
    // Post: removes the last returned element
    @Override
    public void remove() {
        if (count() > 1) {
            remove(1);
        }
    }

    // Pre: count not negative, count <= count()
    // Post: removes the last returned element count-times
    public void remove(int count) {
        if (lastReturnedClass == null) {
            throw new IllegalStateException("No element to remove");
        }

        if (count() >= count) {
            for (int i = 0; i < count; i++) {
                items.stream().filter(item -> item.getClass().equals(lastReturnedClass)).findFirst().ifPresent(items::remove);
            }
        }
    }
}
