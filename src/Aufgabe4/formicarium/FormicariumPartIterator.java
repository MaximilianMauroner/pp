package formicarium;

import java.util.Iterator;
import java.util.List;

public class FormicariumPartIterator implements Iterator<FormicariumPart> {
    private List<FormicariumPart> parts;
    private int index = 0;
    private FormicariumPart lastReturned;

    // Pre: parts is not null and a valid List of FormicariumPart objects
    // Post: creates a new FormicariumPartIterator object with the given parts
    public FormicariumPartIterator(List<FormicariumPart> parts) {
        this.parts = parts;
    }

    // Pre: part is not null and a valid FormicariumPart object
    // Post: creates a new FormicariumPartIterator object with the given part
    public FormicariumPartIterator(FormicariumPart part) {
        this.parts = List.of(part);
    }

    // Pre: -
    // Post: returns true if there is a next element, false otherwise
    @Override
    public boolean hasNext() {
        return this.index < this.parts.size();
    }

    // Pre: -
    // Post: returns the next element if hasNext is true. Otherwise, an exception is thrown
    @Override
    public FormicariumPart next() {
        if (!this.hasNext()) {
            throw new IllegalStateException("No more elements");
        }

        FormicariumPart part = this.parts.get(this.index++);
        this.lastReturned = part;
        return part;
    }

    // Pre: -
    // Post: removes the last returned element
    @Override
    public void remove() {
        if (this.lastReturned == null) {
            throw new IllegalStateException("No element to remove");
        }

        if (this.parts.size() > 1) {
            this.parts.remove(this.lastReturned);
            this.lastReturned = null;
        }
    }
}
