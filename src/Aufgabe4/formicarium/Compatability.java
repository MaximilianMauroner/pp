package formicarium;

/**
 * ToDo: Write out specification and add subtype relation as stated
 * Objekte dieses Typs dienen zur Beschreibung des Lebensraums von Ameisen, für die der damit verbundene Gegenstand
 * (Formicarium, ein Bestandteil davon, Messgerät, Werkzeug, . . . ) geeignet ist. Die Beschreibung erfolgt über Wertebereiche
 * verschiedener Umweltparameter, abfragbar über Methodenpaare (jeweils für
 * den minimalen und maximalen Wert):
 * • minSize, maxSize: geeignete Größe der Ameisen in mm;
 * • minTemperature, maxTemperature: Temperatur in ◦C;
 * • minHumidity, maxHumidity: relative Luftfeuchtigkeit in %.
 * Der Gegenstand ist zur Haltung von Ameisen geeignet, die eine passende Größe haben und unter den gegebenen Umweltbedingungen
 * leben können. Zusätzlich geben die Methoden time und maxTime je
 * einen zeitlichen Horizont für die längstmögliche Dauer der Ameisenhaltung mit diesem Gegenstand zurück – eine Stunde, einen
 * Tag, eine Woche, ein Monat, ein Jahr oder unbeschränkt. Gründe für zeitliche Einschränkungen sind vielfältig, etwa die Abgabe
 * von Giftstoffen oder das Fehlen von Ressourcen. Manche Gründe
 * sind behebbar, andere nicht. Der Wert von maxTime bezieht sich
 * auf nicht behebbare Gründe, der von time auf den aktuellen Zustand. Durch setTime kann der Wert von time angepasst werden,
 * wobei jedoch kein Wert gesetzt werden kann, der über maxTime
 * hinausgeht. Die Methode compatible mit einem Argument von
 * Compatibility gibt ein Objekt von Compatibility zurück, das für
 * time und maxTime die jeweils kleineren Werte aus this und dem
 * Argument übernimmt und für Umweltparameter die überschneidenden Wertebereiche annimmt; überschneiden sich Wertebereiche für
 * einen Umweltparameter nicht, wird eine Ausnahme ausgelöst. Ein
 * Objekt von Compatibility stellt eine Ansammlung von Eigenschaften eines physischen Objekts der realen Welt dar, aber niemals das
 * physische Objekt der realen Welt selbst. Damit ist ein Objekt von
 * Compatibility nie gleich einem Objekt, das ein physisches Objekt
 * der realen Welt darstellt.
 */
public class Compatability {
    private final int minSize;
    private final int maxSize;
    private final double minTemperature;
    private final double maxTemperature;
    private final double minHumidity;
    private final double maxHumidity;
    private Time time;
    private final Time maxTime;

    // Pre: minSize <= maxSize, minTemperature <= maxTemperature, minHumidity <= maxHumidity, time <= maxTime
    // Post: creates a new Compatability object with the given values
    public Compatability(int minSize, int maxSize, double minTemperature, double maxTemperature, double minHumidity, double maxHumidity, Time time, Time maxTime) {
        this.minSize = minSize;
        this.maxSize = maxSize;
        this.minTemperature = minTemperature;
        this.maxTemperature = maxTemperature;
        this.minHumidity = minHumidity;
        this.maxHumidity = maxHumidity;
        this.time = time;
        this.maxTime = maxTime;
    }

    // Pre: -
    // Post: returns the minimal size of the Compatability
    int minSize() {
        return this.minSize;
    }

    // Pre: -
    // Post: returns the maximal size of the Compatability
    int maxSize() {
        return this.maxSize;
    }

    // Pre: -
    // Post: returns the minimal temperature of the Compatability
    double minTemperature() {
        return this.minTemperature;
    }

    // Pre: -
    // Post: returns the maximal temperature of the Compatability
    double maxTemperature() {
        return this.maxTemperature;
    }

    // Pre: -
    // Post: returns the minimal humidity of the Compatability
    double minHumidity() {
        return this.minHumidity;
    }

    // Pre: -
    // Post: returns the maximal humidity of the Compatability
    double maxHumidity() {
        return this.maxHumidity;
    }

    // Pre: -
    // Post: returns the time of the Compatability
    Time time() {
        return this.time;
    }

    // Pre: time <= maxTime
    // Post: sets the time of the Compatability to the given time
    void setTime(Time time) {
        if (time.compareTo(this.maxTime) <= 0) {
            this.time = time;
        }
    }

    // Pre: -
    // Post: returns the maximal time of the Compatability
    Time maxTime() {
        return this.maxTime;
    }

    // Pre: other is not null and a valid Compatability object
    // Post: returns a Compatability object with the minSize and maxSize of the Compatability
    Compatability compatible(Compatability other) throws IllegalArgumentException {
        Time compTime = Time.values()[Math.min(this.time.ordinal(), other.time.ordinal())];
        Time compMaxTime = Time.values()[Math.min(this.maxTime.ordinal(), other.maxTime.ordinal())];

        if (this.minSize > other.maxSize || this.maxSize < other.minSize) {
            throw new IllegalArgumentException("Size not compatible");
        }
        if (this.minTemperature > other.maxTemperature || this.maxTemperature < other.minTemperature) {
            throw new IllegalArgumentException("Temperature not compatible");
        }
        if (this.minHumidity > other.maxHumidity || this.maxHumidity < other.minHumidity) {
            throw new IllegalArgumentException("Humidity not compatible");
        }

        return new Compatability(
                Math.max(this.minSize, other.minSize),
                Math.min(this.maxSize, other.maxSize),
                Math.max(this.minTemperature, other.minTemperature),
                Math.min(this.maxTemperature, other.maxTemperature),
                Math.max(this.minHumidity, other.minHumidity),
                Math.min(this.maxHumidity, other.maxHumidity),
                compTime,
                compMaxTime
        );
    }
}

