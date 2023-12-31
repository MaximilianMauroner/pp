import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Vector;
import java.util.stream.IntStream;
import Ant.*;

public class Ant implements Runnable {
    private final Map map;
    private final int wait;
    private final boolean isLeadAnt;

    private int waitSteps = 0;
    private int steps = 0;

    private Head head;
    private Tail tail;

    private Transaction t;

    public Ant(Map map, int wait, Position init, Direction direction) {
        this.map = map;
        this.wait = wait;
        this.head = new Head(init.getX(), init.getY(), direction);
        this.tail = new Tail(head);
        this.isLeadAnt = false;
        t = new Transaction(map);
        writePositions();
        t.commit();
    }

    public Ant(Map map, int wait, Position init, Direction direction, boolean isLeadAnt) {
        this.map = map;
        this.wait = wait;
        this.head = new Head(init.getX(), init.getY(), direction);
        this.tail = new Tail(head);
        this.isLeadAnt = isLeadAnt;
        t = new Transaction(map);
        writePositions();
        t.commit();
    }

    private boolean move(RelativeDirection newRelDir) {
        Head newHead = this.head.move(newRelDir);
        Tail newTail = new Tail(newHead);

        if (newHead.getX() < 0 || newHead.getX() >= map.getSize() || newHead.getY() < 0 || newHead.getY() >= map.getSize() ||
                newTail.getX() < 0 || newTail.getX() >= map.getSize() || newTail.getY() < 0 || newTail.getY() >= map.getSize()
        ) {
            return false;
        }
        clearPositions();

        this.head = newHead;
        this.tail = newTail;

        writePositions();
        return true;
    }

    public String getPosition(){
        return head.getX() + " : " + head.getY() + " : " +tail.getX() + " : " + tail.getY();
    }

    public void clearPositions() {
        t.setValueByID(head.getX(), head.getY(), ' ');
        t.setValueByID(tail.getX(), tail.getY(), ' ');
    }

    public void writePositions() {
        t.setValueByID(head.getX(), head.getY(), head.getHeadDirection());
        t.setValueByID(tail.getX(), tail.getY(), '+');
    }

    @Override
    public void run() {
        while (true) {
            // ToDo: Do something
            this.t = new Transaction(map);
            List<Position> positions = getPossiblePositions();
            List<Double> probabilities = positions.stream().filter(Objects::nonNull).mapToDouble(p -> {
                return switch (p.getType()) {
                    case 'X' -> 1;
                    case 'O', '+', 'A', 'V', '<', '>' -> 0;
                    case ' ' -> 0.01;
                    default -> Character.getNumericValue(p.getType()) / 9;
                };
            }).collect(Vector::new, Vector::add, Vector::addAll);
            int index = new SampleFromProbabilties().apply(probabilities);
            if(index != -1){
                Position newPosition = positions.get(index);
                RelativeDirection newRelDir = getRelativeDirection(newPosition);

                boolean moved = move(newRelDir);
                if (moved) {
                    steps++;
                }
            } else {
                waitSteps++;
            }
            t.commit();


            try {
                Thread.sleep((int) (Math.random() * wait));
                if(isLeadAnt){
//                    map.printPositions();
                    map.print();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
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

                    if (x < 0 || x >= map.getSize() || y < 0 || y >= map.getSize()) {
                        return null;
                    }
                    Transaction t = new Transaction(map);
                    Position p = map.getPosition(x, y, t);
                    t.commit();
                    return p;
                }).filter(Objects::nonNull).toList();
    }

    private RelativeDirection getRelativeDirection(Position newPosition) {
        int dx = newPosition.getX()  - head.getX();
        int dy = newPosition.getY() - head.getY();

        RelativeDirection rd = RelativeDirection.STRAIGHT;
            switch (head.getDirection()) {
                case NORTH ->  {
                    if(dx > 0 && dy == 0) {
                        rd = RelativeDirection.RIGHT;
                    } else if(dx < 0 && dy == 0) {
                        rd = RelativeDirection.LEFT;
                    } else if(dx > 0 && dy < 0) {
                        rd = RelativeDirection.DIAGONAL_RIGHT;
                    } else if(dx < 0 && dy < 0) {
                        rd = RelativeDirection.DIAGONAL_LEFT;
                    }
                }
                case SOUTH -> {
                    if(dx > 0 && dy == 0) {
                        rd = RelativeDirection.LEFT;
                    } else if(dx < 0 && dy == 0) {
                        rd = RelativeDirection.RIGHT;
                    } else if(dx > 0 && dy < 0) {
                        rd = RelativeDirection.DIAGONAL_LEFT;
                    } else if(dx < 0 && dy < 0) {
                        rd = RelativeDirection.DIAGONAL_RIGHT;
                    }
                }
                case EAST ->  {
                    if(dy > 0 && dx == 0) {
                        rd = RelativeDirection.RIGHT;
                    } else if(dy < 0 && dx == 0) {
                        rd = RelativeDirection.LEFT;
                    } else if(dy > 0 && dx > 0) {
                        rd = RelativeDirection.DIAGONAL_RIGHT;
                    } else if(dy < 0 && dx > 0) {
                        rd = RelativeDirection.DIAGONAL_LEFT;
                    }
                }
                case WEST -> {
                    if(dy > 0 && dx == 0) {
                        rd = RelativeDirection.LEFT;
                    } else if(dy < 0 && dx == 0) {
                        rd = RelativeDirection.RIGHT;
                    } else if(dy > 0 && dx < 0) {
                        rd = RelativeDirection.DIAGONAL_LEFT;
                    } else if(dy < 0 && dx < 0) {
                        rd = RelativeDirection.DIAGONAL_RIGHT;
                    }
                }
            }
        return rd;
    }
};