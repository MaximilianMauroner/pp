package model;

import controller.GameState;

public class OptimalPathPoint implements Entity {
    private Path path;

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
}
