package formicarium;

/**
 * ToDo: Write out specification and add subtype relation as stated
 * Ein Gerät zur Messung der Temperatur ist ein unverzichtbarer Bestandteil eines Formicariums für Ameisen, die bestimmte
 * Temperaturniveaus benötigen. Als Objekte von Thermometer werden hier nur solche Temperaturmessgeräte angesehen, die Bestandteil eines Formicariums sein können.
 */
public class Thermometer implements Instrument, FormicariumPart {
    @Override
    public Compatability compatability() {
        return null;
    }

    @Override
    public String quality() {
        return null;
    }
}
