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
        if (parameters != null) {
            int size = parameters.get("SIZE");
            this.x = (int) getRandomCoordinate(size);
            this.y = (int) getRandomCoordinate(size);
        } else {
            this.x = 0;
            this.y = 0;
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
