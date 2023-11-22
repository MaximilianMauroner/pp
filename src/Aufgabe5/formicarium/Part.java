package formicarium;

/*
 * The Part interface represents a part in the formicarium system. It extends the Rated interface with Part and Quality as the type parameters.
 * Each Part has a value and a string representation.
 * The Part interface provides methods to get the value and the string representation of a part.
 */
public interface Part extends Rated<Part, Quality> {

    // Pre: -
   // Post: Returns the monetary value of the part. The returned value is always non-negative.
    double value();

    // Pre: -
    // Post: Returns a string representation of the part. The returned string is never null or empty.
    String toString();
}
