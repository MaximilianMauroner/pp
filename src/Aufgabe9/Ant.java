import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import Ant.*;

public class Ant implements Runnable {

    private final Map map;
    private final Hive hive;
    private final boolean isLeadAnt;
    private final int min_wait = Parameters.getInstance().get("MIN_WAIT");
    private final int max_wait = Parameters.getInstance().get("MAX_WAIT");

    private int waitSteps = Parameters.getInstance().get("WAITSTEPS");

    private boolean skipNextMove = false;
    private int steps = 0;

    private Head head;
    private Tail tail;

    private Leaf leaf;

    // Pre: map != null, hive != null, init != null
    // Post: creates a new Ant object with the given map, hive, init position and direction
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

    // Pre: map != null, hive != null, init != null
    // Post: creates a new Ant object with the given map, hive, init position and direction
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

    // Pre: -
    // Post: returns the number of steps the ant has taken and other useful information
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

    // Pre: t != null
    // Post: moves the ant to the given direction and returns true if the move was successful, otherwise false
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

    // Pre: -
    // Post: returns the position of the head and tail (only for debugging)
    public String getPosition() {
        return head.getX() + " : " + head.getY() + " : " + tail.getX() + " : " + tail.getY();
    }

    // Pre: t != null
    // Post: clears the positions from the map
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

    // Pre: t != null
    // Post: writes the positions to the map
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
        SampleFromProbabilties probabilties = new SampleFromProbabilties();
        while (!Thread.currentThread().isInterrupted()) {
//            TOOD if found leaf increase increasePheromoneLevel
            if (!skipNextMove) {

                if (this.map.isLocked()) {
                    continue;
                }

                List<Position> positions = getPossiblePositions();

                int index;

                if (this.leaf == null) {
                    List<Double> probabilities = positions.stream().parallel().map(Position::getType).mapToDouble(t ->
                        switch (t) {
                            case 'X' -> {
                                this.leaf = new Leaf();
                                yield 1;
                            }
                            case 'O' -> 0;
                            case ' ' -> ThreadLocalRandom.current().nextDouble(0.01);
                            default -> Character.getNumericValue(t) / 9.0;
                        }
                    ).boxed().toList();
                    index = probabilties.apply(probabilities);
                } else {
                    List<Double> distances = positions.stream()
                            .mapToDouble(p -> {
                                double dx = p.getX() - hive.getX();
                                double dy = p.getY() - hive.getY();
                                return Math.sqrt(dx * dx + dy * dy);
                            }).boxed().toList();

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
                    t.commit();
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
                Arena.invokeStop();
            }

            try {

                Thread.sleep(ThreadLocalRandom.current().nextInt(min_wait, max_wait + 1));
                if (isLeadAnt) {
                    map.print();
                }
            } catch (InterruptedException e) {
                System.out.println("Arena " + Arena.hashCode + ": Ant " + this.hashCode() + " stopped");
                return;
            }
        }
    }

    // Pre: -
    // Post: returns a list of possible positions (not thread-safe)
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
                .parallel()
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

    // Pre: newPosition != null
    // Post: returns the relative direction of the given position
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


    // Pre: -
    // Post: returns true if the given position is invalid, otherwise false
    private boolean isInvalidNextPosition(Position p) {

        return p == null || switch (p.getType()) {
            case '+', 'A', 'V', '<', '>' -> true;
            default -> false;
        };
    }
};