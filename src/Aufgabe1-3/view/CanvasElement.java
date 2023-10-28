package view;

import model.Entity.*;
import model.Position;

/**
 * CanvasElement is a wrapper class for the entities in the model. It is used to sort the entities by their priority.
 * @param entity The entity to draw.
 * @param position The position of the entity.
 */
public record CanvasElement(Entity entity, Position position) implements Comparable<CanvasElement> {

    @Override
    public int compareTo(CanvasElement o) {
        if (o.entity.getPriority() == this.entity.getPriority())
            return 0;
        return o.entity.getPriority() > this.entity.getPriority() ? 1 : -1;
    }
}
