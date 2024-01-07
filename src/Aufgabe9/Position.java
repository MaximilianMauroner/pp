import java.util.concurrent.ThreadLocalRandom;

public class Position {

    private char type = ' ';
    private final int x;
    private final int y;

    // Pre: x >= 0, y >= 0
    // Post: creates a new Position object with the given coordinates
    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    // Pre: type is valid (see spec.), x >= 0, y >= 0
    // Post: creates a new Position object with the given coordinates and type
    public Position(char type, int x, int y) {
        this.type = type;
        this.x = x;
        this.y = y;
    }

    // Pre: position != null
    // Post: creates a new Position object with the same values as the given Position object
    public Position(Position position) {
        this.type = position.type;
        this.x = position.x;
        this.y = position.y;
    }

    // Pre: -
    // Post: creates a new Position object with random coordinates
    public Position() {
        Parameters parameters = Parameters.getInstance();

        int width = 0;
        int height = 0;
        try {
            width = parameters.get("WIDTH");
            height = parameters.get("HEIGHT");
        } catch(NullPointerException e) {
            System.out.println("Parameters not initialized. Using default values (not recommended)");
        } finally {
            this.x = (int) getRandomCoordinate(width);
            this.y = (int) getRandomCoordinate(height);
        }
    }

    // Pre: type is valid (see spec.)
    // Post: returns the distance between this and the given Position object
    public void setType(char type) {
        this.type = type;
    }

    // Pre: -
    // Post: returns the type of the position
    public char getType() {
        return type;
    }

    // Pre: -
    // Post: returns the x coordinate of the position
    public int getX() {
        return x;
    }

    // Pre: -
    // Post: returns the y coordinate of the position
    public int getY() {
        return y;
    }

    // Pre: num > 0
    // Post: returns a random coordinate between 1 and num
    public static double getRandomCoordinate(int num) {
        return ThreadLocalRandom.current().nextDouble(1, num);
    }

    @Override
    public String toString() {
        return "Position{" +
               "type=" + type +
               ", x=" + x +
               ", y=" + y +
               '}';
    }
}
