import javax.xml.crypto.dsig.TransformService;
import java.awt.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Map {
    private final ConcurrentHashMap<Point, Position> positions;
    private final ReentrantLock[][] locks;
    private final ReentrantLock mapLock = new ReentrantLock();

    private int width;
    private int height;

    public Map(int width, int height, int leafs, Hive hive) {
        this.positions = Map.generate(width, height, leafs, hive);
        this.locks = new ReentrantLock[height][width];

        this.width = width;
        this.height = height;

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                locks[j][i] = new ReentrantLock();
            }
        }
    }

    public static ConcurrentHashMap<Point, Position> generate(int width, int height, int leafs, Hive hive) {
        ConcurrentHashMap<Point, Position> pos = new ConcurrentHashMap<Point, Position>();
        Position[][] mappa = IntStream.range(0, height)
                .mapToObj(i -> IntStream.range(0, width)
                        .mapToObj(j -> new Position(j, i))
                        .toArray(Position[]::new))
                .toArray(Position[][]::new);

        for (int i = 0; i < mappa.length; i++) {
            for (int j = 0; j < mappa[i].length; j++) {
                pos.put(new Point(j, i), mappa[i][j]);
            }
        }

        double hiveX = hive.getX();
        double hiveY = hive.getY();

        Point p1 = new Point((int) Math.floor(hiveX), (int) Math.floor(hiveY));
        Point p2 = new Point((int) Math.floor(hiveX), (int) Math.ceil(hiveY));
        Point p3 = new Point((int) Math.ceil(hiveX), (int) Math.floor(hiveY));
        Point p4 = new Point((int) Math.ceil(hiveX), (int) Math.ceil(hiveY));
        pos.get(p1).setType('O');
        pos.get(p2).setType('O');
        pos.get(p3).setType('O');
        pos.get(p4).setType('O');

        Stream.generate(Position::new)
                .filter(position -> pos.get(new Point(position.getX(), position.getY())).getType() != 'O')
                .limit(leafs)
                .forEach(position -> pos.get(new Point(position.getX(), position.getY())).setType('X'));


        return pos;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeigth() {
        return this.height;
    }

    public ConcurrentHashMap<Point, Position> getPositions() {
        return positions;
    }

//    public ReentrantLock[][] getLocks() {
//        return locks;
//    }

    public Position getPosition(int x, int y) {
        if (x < 0 || x >= this.getWidth() || y < 0 || y >= this.getHeigth()) {
            return null;
        }

        return this.positions.get(new Point(x, y));
    }

    public Position tryGetPosition(int x, int y, Transaction t) {
        if (x < 0 || x >= this.getWidth() || y < 0 || y >= this.getHeigth()) {
            return null;
        }

        return this.positions.get(new Point(x, y));
    }

    public void print() {
        synchronized (this.positions) {
            System.out.println("-------------------------");
            for (int i = 0; i < this.height; i++) {
                for (int j = 0; j < this.width; j++) {
                    System.out.print(positions.get(new Point(j, i)).getType());
                }
                System.out.println();
            }
            System.out.println("-------------------------");
        }
    }


    public synchronized void printCount() {
        int tailcount = 0;
        int headcount = 0;
        synchronized (this.positions) {
            for (int i = 0; i < this.height; i++) {
                for (int j = 0; j < this.width; j++) {
                    char type = positions.get(new Point(j, i)).getType();
                    switch (type) {
                        case '+' -> tailcount++;
                        case 'A', 'V', '<', '>' -> headcount++;
                    }
                }
            }
            if (headcount - tailcount != 0) {
                System.out.println("\n\n");
                System.out.println("Headcount: " + headcount);
                System.out.println("Tailcount: " + tailcount);
                System.out.println("\n\n");
            }
        }
    }

    public int getLockCount() {
        int count = 0;
        for (ReentrantLock[] row : locks) {
            for (ReentrantLock c : row) {
                if (c.isLocked()) {
                    count++;
                }
            }
        }
        return count;
    }
}
