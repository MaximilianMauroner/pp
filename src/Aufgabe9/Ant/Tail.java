package Ant;

public class Tail {
    private int x;
    private int y;

    private char oldType = ' ';

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public char getOldType() {
        return oldType;
    }

    public void setOldType(char oldType) {
        this.oldType = oldType;
    }

    public Tail(Head h) {
        calcNewPos(h);
    }

    public void calcNewPos(Head h) {
        switch (h.getDirection()) {
            case NORTH -> {
                x = h.getX();
                y = h.getY() + 1;
            }
            case SOUTH -> {
                x = h.getX();
                y = h.getY() - 1;
            }
            case EAST -> {
                x = h.getX() - 1;
                y = h.getY();
            }
            case WEST -> {
                x = h.getX() + 1;
                y = h.getY();
            }
        }
    }
}