package Ant;

public class Head extends AntPart {
    private Ant.Direction direction;

    // Pre: -
    // Post: returns the direction of the head
    public Direction getDirection() {
        return direction;
    }

    // Pre: -
    // Post: creates a new head with the given coordinates and direction
    public Head(int x, int y, Ant.Direction direction) {
        setX(x);
        setY(y);
        this.direction = direction;
    }

    // Pre: -
    // Post: returns a new head with the new coordinates and correct direction based on the relative direction
    public Head move(Ant.RelativeDirection newRelDir) {
        int x = getX();
        int y = getY();

        int multiplier = newRelDir == Ant.RelativeDirection.STRAIGHT
                || newRelDir == Ant.RelativeDirection.LEFT
                || newRelDir == Ant.RelativeDirection.RIGHT ? 2 : 1;
        switch (newRelDir) {
            case LEFT -> {
                switch (direction) {
                    case NORTH -> {
                        x -= multiplier;
                        direction = Ant.Direction.WEST;
                    }
                    case SOUTH -> {
                        x += multiplier;
                        direction = Ant.Direction.EAST;
                    }
                    case EAST -> {
                        y -= multiplier;
                        direction = Ant.Direction.NORTH;
                    }
                    case WEST -> {
                        y += multiplier;
                        direction = Ant.Direction.SOUTH;
                    }
                }
            }
            case RIGHT -> {
                switch (direction) {
                    case NORTH -> {
                        x += multiplier;
                        direction = Ant.Direction.EAST;
                    }
                    case SOUTH -> {
                        x -= multiplier;
                        direction = Ant.Direction.WEST;
                    }
                    case EAST -> {
                        y += multiplier;
                        direction = Ant.Direction.SOUTH;
                    }
                    case WEST -> {
                        y -= multiplier;
                        direction = Ant.Direction.NORTH;
                    }
                }
            }
            case DIAGONAL_LEFT -> {
                switch (direction) {
                    case NORTH -> {
                        x -= multiplier;
                        y -= multiplier;
                    }
                    case SOUTH -> {
                        x += multiplier;
                        y += multiplier;
                    }
                    case EAST -> {
                        x += multiplier;
                        y -= multiplier;
                    }
                    case WEST -> {
                        x -= multiplier;
                        y += multiplier;
                    }
                }
            }
            case DIAGONAL_RIGHT -> {
                switch (direction) {
                    case NORTH -> {
                        x += multiplier;
                        y -= multiplier;
                    }
                    case SOUTH -> {
                        x -= multiplier;
                        y += multiplier;
                    }
                    case EAST -> {
                        x += multiplier;
                        y += multiplier;
                    }
                    case WEST -> {
                        x -= multiplier;
                        y -= multiplier;
                    }
                }
            }
            case STRAIGHT -> {
                switch (direction) {
                    case NORTH -> y -= multiplier;
                    case SOUTH -> y += multiplier;
                    case EAST -> x += multiplier;
                    case WEST -> x -= multiplier;
                }
            }
        }

        return new Head(x, y, direction);
    }

    // Pre: -
    // Post: returns the direction of the head as a char
    public char getHeadDirection() {
        return switch (direction) {
            case EAST -> '>';
            case WEST -> '<';
            case NORTH -> 'A';
            case SOUTH -> 'V';
        };
    }


}