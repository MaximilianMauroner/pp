package formicarium;

public interface Part extends Rated<Part, Quality> {

    Numeric value();
    String toString();
}
