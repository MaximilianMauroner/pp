import java.util.Arrays;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class MapGenerator {

    public static Character[][] generate(int size, int leafs) {
        Character[][] map = IntStream.range(0, size)
                .mapToObj(i -> IntStream.range(0, size)
                        .mapToObj(j -> ' ')
                        .toArray(Character[]::new))
                .toArray(Character[][]::new);

        double hiveX = getRandomCoordinate(size);
        double hiveY = getRandomCoordinate(size);

        map[(int) Math.floor(hiveX)][(int) Math.floor(hiveY)] = 'O';
        map[(int) Math.floor(hiveX)][(int) Math.ceil(hiveY)] = 'O';
        map[(int) Math.ceil(hiveX)][(int) Math.floor(hiveY)] = 'O';
        map[(int) Math.ceil(hiveX)][(int) Math.ceil(hiveY)] = 'O';

        Stream.generate(Position::new)
                .filter(position -> map[position.getX()][position.getY()] != 'O')
                .limit(leafs)
                .forEach(position -> map[position.getX()][position.getY()] = 'X');


        return map;
    }

    private static double getRandomCoordinate(int num) {
        return (Math.random() * num);
    }
}
