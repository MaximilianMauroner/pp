import java.io.Serializable;

public class Leaf implements Serializable {
    private final double area;

    public Leaf() {
        Parameters p = Parameters.getInstance();
        double min = p.get("LEAF_MIN_AREA");
        double max = p.get("LEAF_MAX_AREA");

        area = Math.random() * (max - min) + min;
    }

    public double getArea() {
        return area;
    }

    public String toString() {
        return "{Leaf: " + this.hashCode() + " Area: " + area + "}";
    }

}
