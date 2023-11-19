package formicarium;

public interface Part extends Rated<Part, Quality> {

    double value();
    String toString();
}
