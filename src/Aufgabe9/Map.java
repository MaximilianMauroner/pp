import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Map {
    private final Position[][] positions;
    private final ReentrantLock[][] locks;

    public Map(int width, int height, int leafs, Hive hive) {
        this.positions = Map.generate(width, height, leafs, hive);
        this.locks = new ReentrantLock[height][width];

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                locks[j][i] = new ReentrantLock();
            }
        }
    }

    public int getSize(){
        return this.positions.length;
    }

    public Position[][] getPositions() {
        return positions;
    }

    public ReentrantLock[][] getLocks() {
        return locks;
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

    public Position getPosition(int x, int y, Transaction t) {
        if (x < 0 || x >= positions.length || y < 0 || y >= positions.length) {
            return null;
        }

        return t.getPositionByID(x,y);
    }

    public void setPosition(int x, int y, Position p, Transaction t) {
        if (x < 0 || x >= positions.length || y < 0 || y >= positions.length)
            return;
        t.setPositionByID(x,y,p);
    }




    public void print() {
        System.out.println("-------------------------");
        for (Position[] row : positions) {
            for (Position c : row) {
                System.out.print(c.getType());
            }
            System.out.println();
        }
        System.out.println("-------------------------");
    }

    public void printPositions(){
        for (Position[] row : positions) {
            for (Position c : row) {
                switch(c.getType()) {
                    case '+', 'A', 'V', '<', '>' ->
                            System.out.println("x:" + c.getX() + "\ty:" + c.getY() + "\t" + c.getType());
                    default -> System.out.print("");
                }
            }
        }
    }
}
