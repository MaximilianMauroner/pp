import java.util.*;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.IntStream;

import Ant.*;

public class Ant implements Runnable {

    private final Map map;
    private final int wait;
    private final boolean isLeadAnt;

    private int waitSteps = 64;
    private boolean skipNextMove = false;
    private int steps = 0;

    private int pheromoneLevel = 0;

    private Head head;
    private Tail tail;


    public Ant(Map map, int wait, Position init, Direction direction) {
        this.map = map;
        this.wait = wait;
        this.head = new Head(init.getX(), init.getY(), direction);
        this.tail = new Tail(head);
        this.isLeadAnt = false;
        Transaction t = new Transaction(map);
        writePositions(t);
        t.commit();
    }

    public Ant(Map map, int wait, Position init, Direction direction, boolean isLeadAnt) {
        this.map = map;
        this.wait = wait;
        this.head = new Head(init.getX(), init.getY(), direction);
        this.tail = new Tail(head);
        this.isLeadAnt = isLeadAnt;
        Transaction t = new Transaction(map);
        writePositions(t);
        t.commit();
    }

    public String print() {
        return "Wait: " +
               (64 - waitSteps) +
               " Steps: " +
               steps +
               "Head: x=" +
               head.getX() +
               ", y=" +
               head.getY() +
               "Tail: x=" +
               tail.getX() +
               ", y=" +
               tail.getY();
    }

    private boolean move(RelativeDirection newRelDir, Transaction t) {
        Head newHead = this.head.move(newRelDir);
        Tail newTail = new Tail(newHead);

        if (newHead.getX() < 0 || newHead.getX() >= map.getWidth() || newHead.getY() < 0 || newHead.getY() >= map.getHeigth() ||
            newTail.getX() < 0 || newTail.getX() >= map.getWidth() || newTail.getY() < 0 || newTail.getY() >= map.getHeigth()
        ) {
            return false;
        }

//        concurrent get positions
        Position hp = this.map.getPosition(newHead.getX(), newHead.getY(), t);
        Position tp = this.map.getPosition(newTail.getX(), newTail.getY(), t);
        if (this.isInvalidNextPosition(hp) || this.isInvalidNextPosition(tp)) {
            return false;
        }

        clearPositions(t);

        this.head = newHead;
        this.tail = newTail;

        writePositions(t);
        return true;
    }

    public String getPosition() {
        return head.getX() + " : " + head.getY() + " : " + tail.getX() + " : " + tail.getY();
    }

    public void clearPositions(Transaction t) {
        char level = ' ';
        if (this.pheromoneLevel > 0) {
            level = String.valueOf(this.pheromoneLevel).charAt(0);
        }
        t.setValueByID(head.getX(), head.getY(), level);
        t.setValueByID(tail.getX(), tail.getY(), level);
    }

    public void writePositions(Transaction t) {
        t.setValueByID(head.getX(), head.getY(), head.getHeadDirection());
        t.setValueByID(tail.getX(), tail.getY(), '+');
    }


    private void increasePheromoneLevel() {
        if (this.pheromoneLevel == 9) return;
        this.pheromoneLevel++;
    }

    @Override
    public void run() {
        while (true) {
//            TOOD if found leaf increase increasePheromoneLevel
            if (!skipNextMove) {
                if (this.map.isLocked()) {
                    continue;
                }
                List<Position> positions = getPossiblePositions();

                List<Double> probabilities = positions.stream().mapToDouble(p ->
                        switch (p.getType()) {
                            case 'X' -> 1;
                            case 'O' -> 0;
                            case ' ' -> 0.01;
                            default -> Character.getNumericValue(p.getType()) / 9;
                        }
                ).collect(Vector::new, Vector::add, Vector::addAll);
                int index = new SampleFromProbabilties().apply(probabilities);
                if (index != -1) {
                    Position newPosition = positions.get(index);
                    Transaction t = new Transaction(map);
                    RelativeDirection newRelDir = getRelativeDirection(newPosition);
                    boolean moved = move(newRelDir, t);
//                    this.increasePheromoneLevel();
                    t.commit();
                    //System.out.println("Locks:" + this.map.getLocksCount());
                    if (moved) {
                        steps++;
                    }
                } else {
                    waitSteps--;
                    this.skipNextMove = true;
                }
            } else {
                this.skipNextMove = false;
            }
            if (waitSteps == 0) {
                Arena.stop();
            }


            try {
                Thread.sleep((int) (Math.random() * wait));
                if (isLeadAnt) {
                    map.print();
                }
            } catch (InterruptedException e) {
                System.out.println("Ant " + this.hashCode() + " stopped");
            }
        }
    }

    private List<Position> getPossiblePositions() {

        //int[] aOffsets = new int[]{-2, -1, -1, 0, 0, 1, 1, 2};
        //int[] bOffsets = new int[]{0, 0, -1, -1, -2, -1, 0, 0};
        int[] aOffsets = new int[]{-2, -1, 0, 1, 2};
        int[] bOffsets = new int[]{0, -1, -2, -1, 0};

        int[] xOffsets = new int[5];
        int[] yOffsets = new int[5];

        switch (head.getDirection()) {
            case NORTH -> {
                xOffsets = aOffsets;
                yOffsets = bOffsets;
            }
            case SOUTH -> {
                xOffsets = Arrays.stream(aOffsets).map(i -> -i).toArray();
                yOffsets = Arrays.stream(bOffsets).map(i -> -i).toArray();
            }
            case EAST -> {
                xOffsets = Arrays.stream(bOffsets).map(i -> -i).toArray();
                yOffsets = aOffsets;
            }
            case WEST -> {
                xOffsets = bOffsets;
                yOffsets = Arrays.stream(aOffsets).map(i -> -i).toArray();
            }
        }

        int[] finalXOffsets = xOffsets;
        int[] finalYOffsets = yOffsets;

        return IntStream.range(0, 5)
                .mapToObj(i -> {
                    int x = head.getX() + finalXOffsets[i];
                    int y = head.getY() + finalYOffsets[i];

                    if (x < 0 || x >= map.getWidth() || y < 0 || y >= map.getHeigth()) {
                        return null;
                    }
                    return map.getPositions()[y][x];

                }).filter(Objects::nonNull).toList();
    }

    private RelativeDirection getRelativeDirection(Position newPosition) {
        int dx = newPosition.getX() - head.getX();
        int dy = newPosition.getY() - head.getY();

        RelativeDirection rd = RelativeDirection.STRAIGHT;
        switch (head.getDirection()) {
            case NORTH -> {
                if (dx > 0 && dy == 0) {
                    rd = RelativeDirection.RIGHT;
                } else if (dx < 0 && dy == 0) {
                    rd = RelativeDirection.LEFT;
                } else if (dx > 0 && dy < 0) {
                    rd = RelativeDirection.DIAGONAL_RIGHT;
                } else if (dx < 0 && dy < 0) {
                    rd = RelativeDirection.DIAGONAL_LEFT;
                }
            }
            case SOUTH -> {
                if (dx > 0 && dy == 0) {
                    rd = RelativeDirection.LEFT;
                } else if (dx < 0 && dy == 0) {
                    rd = RelativeDirection.RIGHT;
                } else if (dx > 0 && dy < 0) {
                    rd = RelativeDirection.DIAGONAL_LEFT;
                } else if (dx < 0 && dy < 0) {
                    rd = RelativeDirection.DIAGONAL_RIGHT;
                }
            }
            case EAST -> {
                if (dy > 0 && dx == 0) {
                    rd = RelativeDirection.RIGHT;
                } else if (dy < 0 && dx == 0) {
                    rd = RelativeDirection.LEFT;
                } else if (dy > 0 && dx > 0) {
                    rd = RelativeDirection.DIAGONAL_RIGHT;
                } else if (dy < 0 && dx > 0) {
                    rd = RelativeDirection.DIAGONAL_LEFT;
                }
            }
            case WEST -> {
                if (dy > 0 && dx == 0) {
                    rd = RelativeDirection.LEFT;
                } else if (dy < 0 && dx == 0) {
                    rd = RelativeDirection.RIGHT;
                } else if (dy > 0 && dx < 0) {
                    rd = RelativeDirection.DIAGONAL_LEFT;
                } else if (dy < 0 && dx < 0) {
                    rd = RelativeDirection.DIAGONAL_RIGHT;
                }
            }
        }
        return rd;
    }

    private boolean isInvalidNextPosition(Position p) {
        return switch (p.getType()) {
            case '+', 'A', 'V', '<', '>' -> true;
            default -> false;
        };
    }
};