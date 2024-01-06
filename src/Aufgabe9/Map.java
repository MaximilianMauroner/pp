import javax.xml.crypto.dsig.TransformService;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Map {

    private final Position[][] positions;
    private final ReentrantLock[][] locks;
    private final ReentrantLock mapLock = new ReentrantLock();
    private final AtomicBoolean isMapLocked = new AtomicBoolean(false);

    // Pre: width > 0, height > 0, leafs > 0, hive != null
    // Post: creates a new Map object with the given width, height, leafs and hive
    public Map(int width, int height, int leafs, Hive hive) {
        this.positions = Map.generate(width, height, leafs, hive);
        this.locks = new ReentrantLock[height][width];


        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                locks[j][i] = new ReentrantLock();
            }
        }
    }

    // Pre: width > 0, height > 0, leafs > 0, hive != null
    // Post: returns a Map of Positions with the given width, height, leafs and hive
    private static Position[][] generate(int width, int height, int leafs, Hive hive) {
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

    // Pre: -
    // Post: locks the whole map
    public void lockMap() {
        mapLock.lock();
        isMapLocked.set(true);
    }

    // Pre: -
    // Post: returns true if the map is locked, otherwise false
    public boolean isLocked() {
        return isMapLocked.get();
    }

    // Pre: -
    // Post: unlocks the whole map
    public void unlockMap() {
        isMapLocked.set(false);
        mapLock.unlock();
    }

    // Pre: -
    // Post: returns the width of the map
    public int getWidth() {
        return this.positions[0].length;
    }

    // Pre: -
    // Post: returns the height of the map
    public int getHeigth() {
        return this.positions.length;
    }

    // Pre: -
    // Post: returns the positions of the map (not thread-safe)
    public Position[][] getPositions() {
        return positions;
    }

    // Pre: -
    // Post: returns the locks of the map
    public ReentrantLock[][] getLocks() {
        return locks;
    }

    // Pre: x >= 0, y >= 0, t != null
    // Post: returns the position at the given coordinates, if the coordinates are out of bounds, null is returned
    public Position getPosition(int x, int y, Transaction t) {
        if (x < 0 || x >= this.getWidth() || y < 0 || y >= this.getHeigth()) {
            return null;
        }

        return t.tryGetPositionByID(x, y);
    }

    // Pre: -
    // Post: prints the map (map is locked during printing)
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

    // Pre: -
    // Post: prints the positions of the map (not thread-safe and only for debugging)
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

    // Pre: -
    // Post: returns the number of locks that are locked (only for debugging)
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
