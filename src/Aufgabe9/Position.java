public class Position {

    private final int x;
    private final int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Position(Position position) {
        this.x = position.getX();
        this.y = position.getY();
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
