package formicarium;

/*
 * The Part interface represents a part in the formicarium system. It extends the Rated interface with Part and Quality as the type parameters.
 * The Part interface provides methods to get the usage and the string representation of a part.
 */
public interface Part extends Rated<Part, Quality> {

    // Pre: -
    // Post: Returns the usage of the part.
    String usage();

    // Pre: -
    // Post: Returns a string representation of the part. The returned string is never null or empty.
    String toString();
}
