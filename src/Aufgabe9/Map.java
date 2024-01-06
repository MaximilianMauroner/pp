import javax.xml.crypto.dsig.TransformService;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Map {

    private final Position[][] positions;
    private final ReentrantLock[][] locks;
    private final ReentrantLock mapLock = new ReentrantLock();
    AtomicBoolean isMapLocked = new AtomicBoolean(false);

    public Map(int width, int height, int leafs, Hive hive) {
        this.positions = Map.generate(width, height, leafs, hive);
        this.locks = new ReentrantLock[height][width];


        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                locks[j][i] = new ReentrantLock();
            }
        }
    }

    public static Position[][] generate(int width, int height, int leafs, Hive hive) {
        Position[][] map = IntStream.range(0, height)
                .mapToObj(i -> IntStream.range(0, width)
                        .mapToObj(j -> new Position(j, i))
                        .toArray(Position[]::new))
                .toArray(Position[][]::new);

        double hiveX = hive.getX();
        double hiveY = hive.getY();

        map[(int) Math.floor(hiveX)][(int) Math.floor(hiveY)].setType('O');
        map[(int) Math.floor(hiveX)][(int) Math.ceil(hiveY)].setType('O');
        map[(int) Math.ceil(hiveX)][(int) Math.floor(hiveY)].setType('O');
        map[(int) Math.ceil(hiveX)][(int) Math.ceil(hiveY)].setType('O');

        Stream.generate(Position::new)
                .filter(position -> map[position.getY()][position.getX()].getType() != 'O')
                .limit(leafs)
                .forEach(position -> map[position.getY()][position.getX()].setType('X'));


        return map;
    }

    public void lockMap() {
        mapLock.lock();
        isMapLocked.set(true);
    }

    public boolean isLocked() {
        return isMapLocked.get();
    }

    public void unlockMap() {
        isMapLocked.set(false);
        mapLock.unlock();
    }

    public int getWidth() {
        return this.positions[0].length;
    }

    public int getHeigth() {
        return this.positions.length;
    }

    public Position[][] getPositions() {
        return positions;
    }

    public ReentrantLock[][] getLocks() {
        return locks;
    }

    public Position getPosition(int x, int y, Transaction t) {
        if (x < 0 || x >= this.getWidth() || y < 0 || y >= this.getHeigth()) {
            return null;
        }

        return t.tryGetPositionByID(x, y);
    }

    public void print() {
        this.lockMap();
        System.out.println("Arena "+ Arena.hashCode +
                "\n-------------------------");
        for (Position[] row : positions) {
            for (Position c : row) {
                System.out.print(c.getType());
            }
            System.out.println();
        }
        System.out.println("-------------------------");
        this.unlockMap();
    }


    public void printPositions() {
        for (Position[] row : positions) {
            for (Position c : row) {
                switch (c.getType()) {
                    case '+', 'A', 'V', '<', '>' ->
                            System.out.println("x:" + c.getX() + "\ty:" + c.getY() + "\t" + c.getType());
                    default -> System.out.print("");
                }
            }
        }
    }

    public int getLocksCount() {
        int count = 0;
        for (int i = 0; i < this.getHeigth(); i++) {
            for (int j = 0; j < this.getWidth(); j++) {
                if (this.locks[i][j].isLocked()) {
                    count++;
                }
            }
        }
        return count;
    }
}
