package Ant;

public class Head {
    private Ant.Direction direction;

    private char oldType = ' ';
    private int x;
    private int y;


    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Direction getDirection() {
        return direction;
    }

    public char getOldType() {
        return oldType;
    }

    public void setOldType(char oldType) {
        this.oldType = oldType;
    }

    public Head(int x, int y, Ant.Direction direction) {
        this.x = x;
        this.y = y;
        this.direction = direction;
    }

    public Head move(Ant.RelativeDirection newRelDir) {
        int x = this.x;
        int y = this.y;

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

    public char getHeadDirection() {
        return switch (direction) {
            case EAST -> '>';
            case WEST -> '<';
            case NORTH -> 'A';
            case SOUTH -> 'V';
        };
    }
}