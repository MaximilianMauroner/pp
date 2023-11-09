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

    public FormicariumItemIterator(Set<FormicariumItem> items) {
        this.items = items;

        distinctItems = new ArrayList<>(items.stream()
                .collect(Collectors.groupingBy(FormicariumItem::getClass,
                        Collectors.collectingAndThen(Collectors.toList(), list -> list.get(0)))
                )
                .values());
    }

    @Override
    public boolean hasNext() {
        return index < distinctItems.size();
    }

    @Override
    public FormicariumItem next() {
        if (!hasNext()) {
            throw new IllegalStateException("No more elements");
        }

        FormicariumItem item = distinctItems.get(index++);
        lastReturnedClass = item.getClass();
        return item;
    }

    public int count() {
        if (lastReturnedClass == null) {
            return 0;
        }
        return (int) items.stream().filter(item -> item.getClass().equals(lastReturnedClass)).count();
    }

    @Override
    public void remove() {
        if (count() > 1) {
            remove(1);
        }
    }

    public void remove(int count) {
        if (lastReturnedClass == null) {
            throw new IllegalStateException("No element to remove");
        }

        if (count() >= count) {
            for (int i = 0; i < count; i++) {
                items.stream().filter(item -> item.getClass().equals(lastReturnedClass)).findFirst().ifPresent(items::remove);
            }
            lastReturnedClass = null;
        }
    }
}
