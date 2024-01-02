import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
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
        writePositions();
    }

    public Ant(Map map, int wait, Position init, Direction direction, boolean isLeadAnt) {
        this.map = map;
        this.wait = wait;
        this.head = new Head(init.getX(), init.getY(), direction);
        this.tail = new Tail(head);
        this.isLeadAnt = isLeadAnt;
        writePositions();
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

    private boolean move(RelativeDirection newRelDir) {
        Head newHead = this.head.move(newRelDir);
        Tail newTail = new Tail(newHead);

        if (newHead.getX() < 0 || newHead.getX() >= map.getWidth() || newHead.getY() < 0 || newHead.getY() >= map.getHeigth() ||
            newTail.getX() < 0 || newTail.getX() >= map.getWidth() || newTail.getY() < 0 || newTail.getY() >= map.getHeigth()
        ) {
            return false;
        }
        if (this.map.getPositions().get(new Point(newTail.getX(), newTail.getY())).getType() != ' ') {
            return false;
        }
        if (this.map.getPositions().get(new Point(newHead.getX(), newHead.getY())).getType() != ' ') {
            return false;
        }
        clearPositions();

        this.head = newHead;
        this.tail = newTail;

        writePositions();

        return true;
    }

    public String getPosition() {
        return head.getX() + " : " + head.getY() + " : " + tail.getX() + " : " + tail.getY();
    }

    public void clearPositions() {
        char level = ' ';
        if (this.pheromoneLevel > 0) {
            level = String.valueOf(this.pheromoneLevel).charAt(0);
        }
        this.map.getPositions().put(new Point(head.getX(), head.getY()), new Position(level, head.getX(), head.getY()));
        this.map.getPositions().put(new Point(tail.getX(), tail.getY()), new Position(level, tail.getX(), tail.getY()));
    }

    public void writePositions() {
        Point hp = new Point(head.getX(), head.getY());
        Point tp = new Point(tail.getX(), tail.getY());
        ConcurrentHashMap<Point, Position> positions = this.map.getPositions();
        Position pold = positions.get(hp);
        if (pold != null && pold.getType() != ' ') {
            System.out.println("Contains");
        }
        positions.put(hp, new Position(head.getHeadDirection(), head.getX(), head.getY()));
        this.map.getPositions().put(tp, new Position('+', tail.getX(), tail.getY()));
    }


    private void increasePheromoneLevel() {
        if (this.pheromoneLevel == 9) return;
        this.pheromoneLevel++;
    }

    @Override
    public synchronized void run() {
        double sleep = Math.random();
        if (isLeadAnt) {
            map.print();
            map.printCount();
        }
        while (true) {
            makeNextMove();
            if (waitSteps == 0) {
                System.out.println(waitSteps);
                map.print();
                Arena.stop();
            }

//            increasePheromoneLevel();
            try {
                Thread.sleep((int) (sleep * wait));
                if (isLeadAnt) {
                    map.print();
                    map.printCount();
                }
            } catch (InterruptedException e) {
                System.out.println("Ant " + this.hashCode() + " stopped");
                e.printStackTrace();
                return;
            }
        }
    }

    private synchronized void makeNextMove() {
        if (!skipNextMove) {
            List<Position> positions = getPossiblePositions();
            List<Double> probabilities = positions.stream().mapToDouble(p ->
                    switch (p.getType()) {
                        case 'X' -> 1;
                        case 'O' -> 0;
                        case ' ' -> 0.01;
                        default -> (double) Character.getNumericValue(p.getType()) / 9;
                    }
            ).collect(Vector::new, Vector::add, Vector::addAll);
            int index = new SampleFromProbabilties().apply(probabilities);
            if (index != -1) {
                Position newPosition = positions.get(index);
                RelativeDirection newRelDir = getRelativeDirection(newPosition);
                boolean moved = move(newRelDir);
                if (moved) {
                    steps++;
                }
            } else {
                this.waitSteps--;
                this.skipNextMove = true;
            }
        } else {
            this.skipNextMove = false;
        }
    }

    private synchronized List<Position> getPossiblePositions() {

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
        List<Position> positionList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            int x = head.getX() + finalXOffsets[i];
            int y = head.getY() + finalYOffsets[i];

            if (x < 0 || x >= map.getWidth() || y < 0 || y >= map.getHeigth()) continue;
            Position p = map.getPosition(x, y);
            if (p == null || this.isInvalidNextPosition(p)) continue;
            positionList.add(p);
        }
        return positionList;

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