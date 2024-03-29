package formicarium;

/**
 * Ein mit einem Substrat gefüllter Glas- oder Kunststoffbehälter,
 * in dem sich Ameisen frei bewegen können. Eine Arena alleine (ohne
 * Nest) bietet Ameisenkolonien keine ausreichende Grundlage für ein
 * längeres Überleben. Es wird streng zwischen Nestern und Arenen
 * unterschieden, das heißt, eine Arena ist kein Nest, ein Nest keine
 * Arena und eine Arena alleine kein Formicarium. Je nach Substrat
 * sind Arenen für unterschiedliche Ameisenarten geeignet. Natürlich
 * brauchen größere Ameisen und größere Ameisenkolonien mehr Platz
 * als kleine. Lange Verbindungsgänge zwischen der Arena und dem
 * Nest lassen vor allem größeren Ameisenarten die Umwelt größer
 * erscheinen, als sie tatsächlich ist.
 */
public class Arena implements FormicariumPart {
    private final String substrate;
    private final int tubeLength; // non-negative, if 0: no tube

    // Pre: substrate is one of "Sand", "Kies", "Erde" or "Holz",
    //      tubeLength is non-negative
    // Post: creates a new Arena object with the given substrate and tubeLength
    public Arena(String substrate, int tubeLength) {
        this.substrate = substrate;
        this.tubeLength = tubeLength;
    }


    // Pre: -
    // Post: returns the compatability of the Arena based on its substrate and tubeLength
    @Override
    public Compatability compatability() {
        int substrateFactor = switch (this.substrate) {
            case "Sand" -> 5;
            case "Kies" -> 4;
            case "Erde" -> 2;
            default -> 1;
        };

        int minSize = this.tubeLength / substrateFactor;
        int maxSize = this.tubeLength * substrateFactor;
        double minTemperature = 4 * substrateFactor;
        double maxTemperature = 8 * substrateFactor;
        double minHumidity = 14 * substrateFactor;
        double maxHumidity = 19 * substrateFactor;
        Time time = Time.values()[substrateFactor - 1];
        return new Compatability(minSize, maxSize, minTemperature, maxTemperature, minHumidity, maxHumidity, time, Time.UNBOUNDED);
    }

    // Pre: -
    // Post: returns a clone of the Arena
    @Override
    public FormicariumItem clone() {
        return new AntFarm(this.substrate, this.tubeLength);
    }

    // Pre: o is not null
    // Post: returns true if the Arena is equal to the given object, false otherwise
    @Override
    public boolean equals(Object o) {
        if (o instanceof Arena arena) {
            return this.substrate.equals(arena.substrate) && this.tubeLength == arena.tubeLength;
        }
        return false;
    }
}
