import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Map {
    private final Position[][] positions;
    private final ReentrantLock[][] locks;

    public Map(int size, int leafs, Hive hive) {
        this.positions = Map.generate(size, leafs, hive);
        this.locks = new ReentrantLock[size][size];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                locks[i][j] = new ReentrantLock();
            }
        }
    }

    public Position[][] getPositions() {
        return positions;
    }

    public ReentrantLock[][] getLocks() {
        return locks;
    }

    public static Position[][] generate(int size, int leafs, Hive hive) {
        Position[][] map = IntStream.range(0, size)
                .mapToObj(i -> IntStream.range(0, size)
                        .mapToObj(j -> new Position(i, j))
                        .toArray(Position[]::new))
                .toArray(Position[][]::new);

        double hiveX = hive.getX();
        double hiveY = hive.getY();

        map[(int) Math.floor(hiveX)][(int) Math.floor(hiveY)].setType('O');
        map[(int) Math.floor(hiveX)][(int) Math.ceil(hiveY)].setType('O');
        map[(int) Math.ceil(hiveX)][(int) Math.floor(hiveY)].setType('O');
        map[(int) Math.ceil(hiveX)][(int) Math.ceil(hiveY)].setType('O');

        Stream.generate(Position::new)
                .filter(position -> map[position.getX()][position.getY()].getType() != 'O')
                .limit(leafs)
                .forEach(position -> map[position.getX()][position.getY()].setType('X'));


        return map;
    }

    public Position getPosition(int x, int y, Transaction t) {
        Position p = t.getPositionByID(x,y);
        return p;
    }

    public void setPosition(int x, int y, Position p, Transaction t) {
         t.setPositionByID(x,y,p);
    }

    //     T.setValueByID(i % 100, i);
//                        T.getValueByID(i % 100);
//                        if (i % 20 == 0) T.commit();



    public void print() {
        for (Position[] row : positions) {
            for (Position c : row) {
                System.out.print(c.getType());
            }
            System.out.println();
        }
    }
}
