import java.io.Serializable;

public class Leaf implements Serializable {
    private final double area;

    // Pre: -
    // Post: creates a new Leaf object with a random area between LEAF_MIN_AREA and LEAF_MAX_AREA
    public Leaf() {
        Parameters p = Parameters.getInstance();
        double min = p.get("LEAF_MIN_AREA");
        double max = p.get("LEAF_MAX_AREA");

        area = Math.random() * (max - min) + min;
    }

    // Pre: -
    // Post: returns the area of the leaf
    public double getArea() {
        return area;
    }

    @Override
    public String toString() {
        return "{Leaf: " + this.hashCode() + " Area: " + area + "}";
    }

}
