package formicarium;

/**
 * ToDo: Write out specification and add subtype relation as stated
 * Ein Gerät zur Messung der Temperatur ist ein unverzichtbarer Bestandteil eines Formicariums für Ameisen, die bestimmte
 * Temperaturniveaus benötigen. Als Objekte von Thermometer werden hier nur solche Temperaturmessgeräte angesehen, die Bestandteil eines Formicariums sein können.
 */
public class Thermometer implements Instrument, FormicariumPart {
    private final Quality quality;

    public Thermometer() {
        this.quality = Quality.SEMIPROFESSIONAL;
    }

    @Override
    public Compatability compatability() {
        return new Compatability(Integer.MIN_VALUE, Integer.MAX_VALUE, Double.MIN_VALUE, Double.MAX_VALUE, Double.MIN_VALUE, Double.MAX_VALUE, Time.UNBOUNDED, Time.UNBOUNDED);
    }

    @Override
    public Quality quality() {
        return this.quality;
    }
}
