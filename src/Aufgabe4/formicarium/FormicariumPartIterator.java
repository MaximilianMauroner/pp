package formicarium;

import java.util.Iterator;
import java.util.List;

public class FormicariumPartIterator implements Iterator<FormicariumPart> {
    private List<FormicariumPart> parts;
    private int index = 0;
    private FormicariumPart lastReturned;

    public FormicariumPartIterator(List<FormicariumPart> parts) {
        this.parts = parts;
    }

    public FormicariumPartIterator(FormicariumPart part) {
        this.parts = List.of(part);
    }

    @Override
    public boolean hasNext() {
        return this.index < this.parts.size();
    }

    @Override
    public FormicariumPart next() {
        if (!this.hasNext()) {
            throw new IllegalStateException("No more elements");
        }

        FormicariumPart part = this.parts.get(this.index++);
        this.lastReturned = part;
        return part;
    }

    @Override
    public void remove() {
        if (this.lastReturned == null) {
            throw new IllegalStateException("No element to remove");
        }

        if (this.parts.size() > 1) {
            int removedIndex = this.parts.indexOf(this.lastReturned);
            if (removedIndex < this.index) {
                this.index--;
            }
            this.parts.remove(this.lastReturned);
            this.lastReturned = null;
        }
    }
}
