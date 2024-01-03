import java.util.*;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.IntStream;

import Ant.*;

public class Ant implements Runnable {

    private final Map map;
    private final Hive hive;
    private final boolean isLeadAnt;

    private int waitSteps = Parameters.getInstance().get("WAITSTEPS");
    private boolean skipNextMove = false;
    private int steps = 0;

    private Head head;
    private Tail tail;

    private Leaf leaf;


    public Ant(Map map, Hive hive, Position init, Direction direction) {
        this.map = map;
        this.hive = hive;
        this.head = new Head(init.getX(), init.getY(), direction);
        this.tail = new Tail(head);
        this.isLeadAnt = false;
        Transaction t = new Transaction(map);
        writePositions(t);
        t.commit();
    }

    public Ant(Map map, Hive hive, Position init, Direction direction, boolean isLeadAnt) {
        this.map = map;
        this.hive = hive;
        this.head = new Head(init.getX(), init.getY(), direction);
        this.tail = new Tail(head);
        this.isLeadAnt = isLeadAnt;
        Transaction t = new Transaction(map);
        writePositions(t);
        t.commit();
    }

    public String print() {
        return "{Ant: " +
                this.hashCode() +
                " Wait: " +
                (64 - waitSteps) +
                " Steps: " +
                steps +
                " Head: x=" +
                head.getX() +
                ", y=" +
                head.getY() +
                " Tail: x=" +
                tail.getX() +
                ", y=" +
                tail.getY() +
                "}";
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
        char headType = head.getOldType();
        char tailType = tail.getOldType();


        if (headType != 'O' && headType != 'X') {
            int headLevel = headType == ' ' ? 0 : Character.getNumericValue(headType);
            headType = headLevel < 9 ? String.valueOf(headLevel + 1).charAt(0) : ' ';
        }

        if (tailType != 'O' && tailType != 'X') {
            int tailLevel = tailType == ' ' ? 0 : Character.getNumericValue(tailType);
            tailType = tailLevel < 9 ? String.valueOf(tailLevel + 1).charAt(0) : ' ';
        }

        t.setValueByID(head.getX(), head.getY(), headType);
        t.setValueByID(tail.getX(), tail.getY(), tailType);
    }

    public void writePositions(Transaction t) {
        char newHead = t.getPositionByID(head.getX(), head.getY()).getType();
        char newTail = t.getPositionByID(tail.getX(), tail.getY()).getType();
        head.setOldType(newHead);
        tail.setOldType(newTail);
        t.setValueByID(head.getX(), head.getY(), head.getHeadDirection());
        t.setValueByID(tail.getX(), tail.getY(), '+');
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
//            TOOD if found leaf increase increasePheromoneLevel
            if (!skipNextMove) {
                if (this.map.isLocked()) {
                    continue;
                }
                List<Position> positions = getPossiblePositions();

                int index;

                if (this.leaf == null) {
                    List<Double> probabilities = positions.stream().mapToDouble(p ->
                            switch (p.getType()) {
                                case 'X' -> {
                                    this.leaf = new Leaf();
                                    yield 1;
                                }
                                case 'O' -> 0;
                                case ' ' -> Math.random() * 0.01;
                                default -> Character.getNumericValue(p.getType()) / 9;
                            }
                    ).collect(Vector::new, Vector::add, Vector::addAll);
                    index = new SampleFromProbabilties().apply(probabilities);
                } else {
                    List<Double> distances = positions.stream()
                            .mapToDouble(p -> {
                                double dx = p.getX() - hive.getX();
                                double dy = p.getY() - hive.getY();
                                return Math.sqrt(dx * dx + dy * dy);
                            }).collect(Vector::new, Vector::add, Vector::addAll);

                    try {
                        index = distances.indexOf(Collections.min(distances));

                        if (positions.get(index).getType() == 'O') {
                            this.hive.receiveFood(this.leaf);
                            this.leaf = null;
                        } else if (isInvalidNextPosition(positions.get(index))) {
                            index = -1;
                        }
                    } catch (NoSuchElementException e) {
                        index = -1;
                    }
                }


                if (index != -1) {
                    Position newPosition = positions.get(index);
                    Transaction t = new Transaction(map);
                    RelativeDirection newRelDir = getRelativeDirection(newPosition);
                    boolean moved = move(newRelDir, t);
                    //this.increasePheromoneLevel();
                    t.commit();
                    //System.out.println("Locks:" + this.map.getLocksCount());
                    if (moved) {
                        steps++;
                    } else {
                        waitSteps--;
                        this.skipNextMove = true;
                    }
                } else {
                    waitSteps--;
                    this.skipNextMove = true;
                }
            } else {
                this.skipNextMove = false;
            }
            if (waitSteps <= 0) {
                Arena.stop();
            }


            try {
                int min_wait = 5;
                int max_wait = 50;
                Thread.sleep((int) (Math.random() * (max_wait - min_wait) + min_wait));
                if (isLeadAnt) {
                    map.print();
                }
            } catch (InterruptedException e) {
                System.out.println("Arena " + Arena.hashCode + ": Ant " + this.hashCode() + " stopped");
                return;
            }
        }
    }

    private List<Position> getPossiblePositions() {

        //int[] aOffsets = new int[]{-2, -1, -1, 0, 0, 1, 1, 2};
        //int[] bOffsets = new int[]{0, 0, -1, -1, -2, -1, 0, 0};
        int[] aOffsets = new int[]{-2, -1, 0, 1, 2};
        int[] bOffsets = new int[]{0, -1, -2, -1, 0};
        int[] cOffsets = new int[]{-1, -1, 0, 1, 1};
        int[] dOffsets = new int[]{-1, -1, -2, -1, -1};

        int[] xHeads = new int[5];
        int[] yHeads = new int[5];
        int[] xTails = new int[5];
        int[] yTails = new int[5];

        switch (head.getDirection()) {
            case NORTH -> {
                xHeads = aOffsets;
                yHeads = bOffsets;
                xTails = cOffsets;
                yTails = dOffsets;
            }
            case SOUTH -> {
                xHeads = Arrays.stream(aOffsets).map(i -> -i).toArray();
                yHeads = Arrays.stream(bOffsets).map(i -> -i).toArray();
                xTails = Arrays.stream(cOffsets).map(i -> -i).toArray();
                yTails = Arrays.stream(dOffsets).map(i -> -i).toArray();
            }
            case EAST -> {
                xHeads = Arrays.stream(bOffsets).map(i -> -i).toArray();
                yHeads = aOffsets;
                xTails = Arrays.stream(dOffsets).map(i -> -i).toArray();
                yTails = cOffsets;
            }
            case WEST -> {
                xHeads = bOffsets;
                yHeads = Arrays.stream(aOffsets).map(i -> -i).toArray();
                xTails = dOffsets;
                yTails = Arrays.stream(cOffsets).map(i -> -i).toArray();
            }
        }

        int[] finalXHeads = xHeads;
        int[] finalYHeads = yHeads;
        int[] finalXTails = xTails;
        int[] finalYTails = yTails;

        return IntStream.range(0, 5)
                .mapToObj(i -> {
                    int hx = head.getX() + finalXHeads[i];
                    int hy = head.getY() + finalYHeads[i];
                    int tx = tail.getX() + finalXTails[i];
                    int ty = tail.getY() + finalYTails[i];

                    if (hx < 0 || hx >= map.getWidth() || hy < 0 || hy >= map.getHeigth()
                        || tx < 0 || tx >= map.getWidth() || ty < 0 || ty >= map.getHeigth()) {
                        return null;
                    }

                    Position ph = map.getPositions()[hy][hx];
                    Position pt = map.getPositions()[ty][tx];
                    if (isInvalidNextPosition(ph) || isInvalidNextPosition(pt)) {
                        return null;
                    }

                    return ph;

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

        return p == null || switch (p.getType()) {
            case '+', 'A', 'V', '<', '>' -> true;
            default -> false;
        };
    }
};