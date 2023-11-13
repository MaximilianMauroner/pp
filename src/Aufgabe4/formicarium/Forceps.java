package formicarium;

/**
 * ToDo: Write out specification and add subtype relation as stated
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

    public Forceps(String quality, int minSize, int maxSize) {
        this.quality = switch (quality) {
            case "professional", "semiprofessional", "hobby" -> quality;
            default -> "unusable";
        };
        this.minSize = minSize;
        this.maxSize = maxSize;
    }

    @Override
    public Compatability compatability() {
        return new Compatability (minSize, maxSize, Double.MIN_VALUE, Double.MAX_VALUE, Double.MIN_VALUE, Double.MAX_VALUE, Time.UNBOUNDED, Time.UNBOUNDED);
    }

    @Override
    public String quality() {
        return this.quality;
    }
}
