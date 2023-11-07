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
    @Override
    public Compatability compatability() {
        return null;
    }

    @Override
    public String quality() {
        return null;
    }
}
