package Ant;

public class Tail extends AntPart {
    // Pre: h != null
    // Post: creates a new tail with the new coordinates based on the head
    public Tail(Head h) {
        calcNewPos(h);
    }

    // Pre: h != null
    // Post: calculates the new coordinates based on the head
    public void calcNewPos(Head h) {
        h.getHeadDirection();
        switch (h.getDirection()) {
            case NORTH -> {
                setX(h.getX());
                setY(h.getY() + 1);
            }
            case SOUTH -> {
                setX(h.getX());
                setY(h.getY() - 1);
            }
            case EAST -> {
                setX(h.getX() - 1);
                setY(h.getY());
            }
            case WEST -> {
                setX(h.getX() + 1);
                setY(h.getY());
            }
        }
    }
}