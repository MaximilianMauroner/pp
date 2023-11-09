package formicarium;

import java.util.Iterator;

/**
 * ToDo: Write out specification and add subtype relation as stated
 * Eine Form eines Ameisennests bestehend aus einem Substrat
 * (z. B. Sand, Kies oder Erde) zwischen zwei Glas- oder Kunststoffplatten, die an den Rändern miteinander verbunden sind. Durch die
 * Platten sind die von den Ameisen ins Substrat gegrabenen Gänge beobachtbar. Je nach Substrat sind solche Ameisenfarmen für
 * Ameisen unterschiedlicher Größen und mit unterschiedlichen natürlichen Lebensräumen geeignet. Auch der Plattenabstand spielt für
 * die Ameisengröße eine Rolle, da Ameisen ohne ausreichend Platz
 * keine Gänge anlegen können und sich bei zu viel Platz in der Mitte
 * verstecken (von außen nicht beobachtbar sind).
 */
public class AntFarm implements Nest {
    private final String substrate;
    private final int plateDistance;
    private Thermometer thermometer;

    public AntFarm(Thermometer thermometer) {
        this.substrate = "Sand";
        this.plateDistance = 10;
        this.thermometer = thermometer;
    }

    public AntFarm() {
        this.substrate = "Sand";
        this.plateDistance = 10;
    }

    /**
     * When AntFarm is a Formicarium, it needs to have a thermometer
     * When it is just a Part of a CompositeFormicarium, it doesn't.
     * @return true if AntFarm is a Formicarium, false otherwise
     */
    @Override
    public boolean isFormicarium() {
        return thermometer != null;
    }

    @Override
    public Thermometer thermometer() {
        return this.thermometer;
    }

    @Override
    public Compatability compatability() {
        int minSize = this.plateDistance - 5;
        int maxSize = this.plateDistance + 5;
        double minTemperature = 20.5;
        double maxTemperature = 25.5;
        double minHumidity = 80;
        double maxHumidity = 95;

        Time time;
        Time maxTime = Time.YEAR;
        if (this.isFormicarium()) {
            time = Time.DAY;
        } else {
            time = maxTime;
        }

        return new Compatability(minSize, maxSize, minTemperature, maxTemperature, minHumidity, maxHumidity, time, maxTime);
    }

    @Override
    public Iterator<FormicariumPart> iterator() {
        return new FormicariumPartIterator(this);
    }
}
