package src.model;

public class Position {
    private int x, y;
    private Status status;

    public Position(int x, int y, Status status) {
        if (x > status.getWidth()) {
            x = 0;
        }
        if (y > status.getHeight()) {
            y = 0;
        }
        if (x < 0) {
            x = status.getWidth();
        }
        if (y < 0) {
            y = status.getHeight();
        }
        this.status = status;
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }


    @Override
    public Position clone() {
        return new Position(x, y, status);
    }
}
