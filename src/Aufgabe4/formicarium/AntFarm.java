package formicarium;

import java.util.Iterator;

/**
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
    private final Thermometer thermometer;

    // Pre: thermometer is not null and a valid Thermometer object,
    //      substrate is one of "Sand", "Kies", "Erde" or "Holz",
    //      plateDistance is non-negative
    // Post: creates a new AntFarm object with the given substrate and plateDistance
    public AntFarm(Thermometer thermometer, String substrate, int plateDistance) {
        this.substrate = substrate;
        this.plateDistance = plateDistance;
        this.thermometer = thermometer;
    }

    // Pre: substrate is one of "Sand", "Kies", "Erde" or "Holz",
    //      plateDistance is non-negative
    // Post:creates a new AntFarm object with the given substrate and plateDistance,
    //      and a null thermometer
    public AntFarm(String substrate, int plateDistance) {
        this.substrate = substrate;
        this.plateDistance = plateDistance;
        this.thermometer = null;
    }

    // Pre: -
    // Post: returns true if AntFarm is a Formicarium, false otherwise
    @Override
    public boolean isFormicarium() {
        return thermometer != null;
    }

    // Pre: -
    // Post: returns the substrate of the AntFarm
    @Override
    public Thermometer thermometer() {
        return this.thermometer;
    }

    // Pre: -
    // Post: returns the substrate of the AntFarm
    @Override
    public Compatability compatability() {
        int substrateFactor = switch (this.substrate) {
            case "Sand" -> 5;
            case "Kies" -> 4;
            case "Erde" -> 2;
            default -> 1;
        };

        int minSize = this.plateDistance - (substrateFactor / 2);
        int maxSize = this.plateDistance + (substrateFactor / 2);
        double minTemperature = 5 * substrateFactor;
        double maxTemperature = 6 * substrateFactor;
        double minHumidity = 16 * substrateFactor;
        double maxHumidity = 17 * substrateFactor;

        Time time;
        Time maxTime = Time.YEAR;
        if (this.isFormicarium()) {
            time = Time.DAY;
        } else {
            time = maxTime;
        }

        return new Compatability(minSize, maxSize, minTemperature, maxTemperature, minHumidity, maxHumidity, time, maxTime);
    }

    // Pre: -
    // Post: returns an iterator substrate of the AntFarm
    @Override
    public Iterator<FormicariumPart> iterator() {
        return new FormicariumPartIterator(this);
    }
}
