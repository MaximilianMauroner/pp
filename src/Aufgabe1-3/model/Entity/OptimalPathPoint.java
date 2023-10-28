package model.Entity;

import controller.GameState;
import model.Path;
import model.Point;
import model.Position;
import model.Status;

public class OptimalPathPoint implements Entity {
    private Path path;

    private Position position;

    public OptimalPathPoint(Path path) {
        this.path = path;
    }

    @Override
    public void run(GameState gameState, Status status, Point point) {
        this.path.addPointMetric(point.getTrail());
    }

    @Override
    public Entity clone() {
        return new OptimalPathPoint(path);
    }

    @Override
    public int getPriority() {
        return model.Parameters.OPTIMAL_PATH_PRIORITY;
    }

    @Override
    public Position getPosition() {
        return this.position;
    }

    @Override
    public void setPosition(Position position) {
        this.position = position;
    }
}
