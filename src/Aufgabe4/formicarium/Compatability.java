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
public interface Compatability {
    int minSize();
    int maxSize();

    double minTemperature();
    double maxTemperature();

    double minHumidity();
    double maxHumidity();

    String time();
    void setTime();
    String maxTime();

    Compatability compatible(Compatability other) throws IllegalArgumentException;
}

