package formicarium;

/**
 * Die Pinzette ist ein wichtiges Instrument, das im Zusammenhang von Formicarien häufig verwendet wird (etwa zur genaueren
 * Untersuchung einzelner Ameisen), aber kein Formicarium oder Bestandteil eines Formicariums ist. Die Methode compatibility gibt
 * ein Objekt vom Typ Compatible zurück, das (neben irrelevanten
 * anderen Bedingungen) den Größenbereich von Ameisen angibt, für
 * den die Pinzette gut einsetzbar ist.
 */
public class Forceps implements Instrument {
    private final int minSize;
    private final int maxSize;
    private final String quality;

    // Pre: quality is one of "professional", "semiprofessional", "hobby"
    // Post: creates a new Forceps object with the given quality, minSize and maxSize
    public Forceps(String quality, int minSize, int maxSize) {
        this.quality = switch (quality) {
            case "professional", "semiprofessional", "hobby" -> quality;
            default -> "unusable";
        };
        this.minSize = minSize;
        this.maxSize = maxSize;
    }

    // Pre: -
    // Post: returns a Compatability object with the minSize and maxSize of the Forceps
    @Override
    public Compatability compatability() {
        return new Compatability(minSize, maxSize, Double.MIN_VALUE, Double.MAX_VALUE, Double.MIN_VALUE, Double.MAX_VALUE, Time.UNBOUNDED, Time.UNBOUNDED);
    }

    // Pre: -
    // Post: returns a clone of the Forceps
    @Override
    public FormicariumItem clone() {
        return new Forceps(this.quality, this.minSize, this.maxSize);
    }

    // Pre: o is not null
    // Post: returns true if the Forceps is equal to the given object, false otherwise
    @Override
    public boolean equals(Object o) {
        if (o instanceof Forceps forceps) {
            return this.quality.equals(forceps.quality) && this.minSize == forceps.minSize && this.maxSize == forceps.maxSize;
        }
        return false;
    }

    // Pre: -
    // Post: returns the quality of the Forceps
    @Override
    public String quality() {
        return this.quality;
    }
}
