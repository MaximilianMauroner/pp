package formicarium;

/**
 * Ein Gerät zur Messung der Temperatur ist ein unverzichtbarer Bestandteil eines Formicariums für Ameisen, die bestimmte
 * Temperaturniveaus benötigen. Als Objekte von Thermometer werden hier nur solche Temperaturmessgeräte angesehen, die Bestandteil eines Formicariums sein können.
 */
public class Thermometer implements Instrument, FormicariumPart {
    private final String quality;

    // Pre: quality is one of "professional", "semiprofessional", "hobby"
    // Post: creates a new Thermometer object with the given quality
    public Thermometer(String quality) {
        this.quality = switch (quality) {
            case "professional", "semiprofessional", "hobby" -> quality;
            default -> "unusable";
        };
    }

    // Pre: -
    // Post: returns a Compatability object with the minSize and maxSize of the Thermometer
    @Override
    public Compatability compatability() {
        return new Compatability(Integer.MIN_VALUE, Integer.MAX_VALUE, Double.MIN_VALUE, Double.MAX_VALUE, Double.MIN_VALUE, Double.MAX_VALUE, Time.UNBOUNDED, Time.UNBOUNDED);
    }

    // Pre: -
    // Post: returns a clone of the Thermometer
    @Override
    public FormicariumItem clone() {
        return new Thermometer(this.quality);
    }

    // Pre: o is not null
    // Post: returns true if the given object is equal to the Thermometer
    @Override
    public boolean equals(Object o) {
        if (o instanceof Thermometer thermometer) {
            return this.quality.equals(thermometer.quality);
        }
        return false;
    }

    // Pre: -
    // Post: returns the quality of the Thermometer
    @Override
    public String quality() {
        return this.quality;
    }
}
