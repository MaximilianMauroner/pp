package formicarium;

/**
 * ToDo: Write out specification and add subtype relation as stated
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
    private final int tubeLength; // non-negative, if 0, no tube

    public Arena() {
        this.substrate = "Sand";
        this.tubeLength = 15;
    }


    @Override
    public Compatability compatability() {
        int minSize = this.tubeLength / 3;
        int maxSize = this.tubeLength / 2;
        return new Compatability(minSize, maxSize, 10, 15, 70, 85, Time.MONTH, Time.YEAR);
    }
}
