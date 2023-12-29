public class Ant implements Runnable {
    private final Map map;
    private final int wait;

    private Head head;
    private Tail tail;

    public Ant(Map map, int wait, Position init, Direction direction) {
        this.map = map;
        this.wait = wait;
        this.head = new Head(init.getX(), init.getY(), direction);
        this.tail = new Tail(head);
        writePositions();
    }

    public void move(RelativeDirection newRelDir) {
        this.head.move(newRelDir);
        this.tail.move(head);
        writePositions();
    }

    public void writePositions() {
        Transaction t = new Transaction(map);
        int headX = head.x;
        int headY = head.y;
        int tailX = tail.x;
        int tailY = tail.y;

        t.setValueByID(headX, headY, head.getHeadDirection());
        t.setValueByID(tailX, tailY, '+');
        t.commit();
    }

    @Override
    public void run() {
        while (true) {
            // ToDo: Do something

            try {
                Thread.sleep((int) (Math.random() * wait));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    enum Direction {
        NORTH,
        EAST,
        SOUTH,
        WEST
    }

    enum RelativeDirection {
        LEFT,
        DIAGONAL_LEFT,
        RIGHT,
        DIAGONAL_RIGHT,
        STRAIGHT,
    }

    class Head {
        private Direction direction;

        private int x;
        private int y;

        public Head(int x, int y, Direction direction) {
            this.x = x;
            this.y = y;
            this.direction = direction;
        }

        public void move(RelativeDirection newRelDir) {
            int multiplier = newRelDir.equals(RelativeDirection.STRAIGHT) ? 2 : 1;
            switch (newRelDir) {
                case LEFT -> {
                    switch (direction) {
                        case NORTH -> {
                            x -= multiplier;
                            direction = Direction.WEST;
                        }
                        case SOUTH -> {
                            x += multiplier;
                            direction = Direction.EAST;
                        }
                        case EAST -> {
                            y += multiplier;
                            direction = Direction.NORTH;
                        }
                        case WEST -> {
                            y -= multiplier;
                            direction = Direction.SOUTH;
                        }
                    }
                }
                case RIGHT -> {
                    switch (direction) {
                        case NORTH -> {
                            x += multiplier;
                            direction = Direction.EAST;
                        }
                        case SOUTH -> {
                            x -= multiplier;
                            direction = Direction.WEST;
                        }
                        case EAST -> {
                            y -= multiplier;
                            direction = Direction.SOUTH;
                        }
                        case WEST -> {
                            y += multiplier;
                            direction = Direction.NORTH;
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

    class Tail {
        private int x;
        private int y;

        public Tail(Head h) {
            calcNewPos(h);
        }

        public void move(Head h) {
            calcNewPos(h);
        }

        public void calcNewPos(Head h) {
            switch (h.direction) {
                case NORTH -> {
                    x = h.x;
                    y = h.y + 1;
                }
                case SOUTH -> {
                    x = h.x;
                    y = h.y - 1;
                }
                case EAST -> {
                    x = h.x - 1;
                    y = h.y;
                }
                case WEST -> {
                    x = h.x + 1;
                    y = h.y;
                }
            }
        }
    }


};