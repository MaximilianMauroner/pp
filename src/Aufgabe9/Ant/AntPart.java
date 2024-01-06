package Ant;

public abstract class AntPart {
    private int x;
    private int y;
    private char oldType = ' '; // client-side-history-constraint: update before moving ant to position

    // Pre: -
    // Post: returns x coordinate
    public int getX() {
        return x;
    }

    // Pre: -
    // Post: sets x coordinate
    public void setX(int x) {
        this.x = x;
    }

    // Pre: -
    // Post: returns y coordinate
    public int getY() {
        return y;
    }

    // Pre: -
    // Post: sets y coordinate
    public void setY(int y) {
        this.y = y;
    }

    // Pre: -
    // Post: returns the type of the position before the ant was on it
    public char getOldType() {
        return oldType;
    }

    // Pre: -
    // Post: sets the type of the position before the ant was on it (typically ' ', O, X or a number 1..9)
    public void setOldType(char oldType) {
        this.oldType = oldType;
    }
}
