package model;

import controller.GameState;

/**
 * Class for generating clusters of entities
 * generate function is accessed like a module
 */
public class ClusterGenerator {

    public static void generate(Entity entity, Position position, int size, GameState gs) {
        for (int i = -(size / 2); i <= size / 2; i++){
            for (int j = -(size / 2); j <= size / 2; j++) {
                Position pos = new Position(position.getX() + i, position.getY() + j, gs.getStatus());
                Point p = gs.getPoint(pos);
                if (p == null) {
                    p = new Point(pos, entity.clone());
                    gs.setPoint(p);
                } else {
                    p.getEntities().clear();
                    p.getEntities().add(entity.clone());
                }
            }
        }
    }
}