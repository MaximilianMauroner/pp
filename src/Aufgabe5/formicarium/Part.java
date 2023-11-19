package formicarium;

public interface Part extends Rated<Part, Quality> {
    String usage();
    double value();
    String toString();
}
