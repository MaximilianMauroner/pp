public class Position {

    private char type = ' ';
    private final int x;
    private final int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Position(char type, int x, int y) {
        this.type = type;
        this.x = x;
        this.y = y;
    }

    public Position(Position position) {
        this.type = position.type;
        this.x = position.x;
        this.y = position.y;
    }

    public Position() {
        Parameters parameters = Parameters.getInstance();

        int width = 0;
        int height = 0;
        try {
            width = parameters.get("WIDTH");
            height = parameters.get("HEIGHT");
        } catch(NullPointerException e) {
            System.out.println("Parameters not initialized");
        } finally {
            this.x = (int) getRandomCoordinate(width);
            this.y = (int) getRandomCoordinate(height);
        }
    }

    public void setType(char type) {
        this.type = type;
    }

    public char getType() {
        return type;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public static double getRandomCoordinate(int num) {
        return (Math.random() * num);
    }
}
