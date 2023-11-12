package formicarium;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Ein Formicarium, das durch Hinzufügen und
 * Wegnehmen von Bestandteilen änderbar ist. Über einen Iterator
 * sind alle Bestandteile zugreifbar; remove im Iterator entfernt den
 * zuletzt von next zurückgegebenen Bestandteil wenn mehr als ein
 * Bestandteil vorhanden ist, sonst wird eine Ausnahme ausgelöst.
 * Die Methode add in einem Objekt von CompositeFormicarium fügt
 * einen Bestandteil hinzu, sofern er mit dem Formicarium kompatibel
 * ist und nicht schon mit derselben Identität vorkommt (nicht-idente
 * gleiche Bestandteile können mehrfach vorkommen). Kompatibilität
 * ist gegeben, wenn compatible aus Compatibility angewandt auf
 * die Umweltbeschreibungen des Formicariums und neuen Bestandteils keine Ausnahme auslöst. Von compatibility zurückgegebene
 * Werte (siehe Formicarium) sind anzupassen. Änderungen durch add
 * und remove lassen die Identität des Formicariums unverändert.
 */
public class CompositeFormicarium implements Formicarium {
    List<FormicariumPart> parts;
    Compatability compatibility;

    public CompositeFormicarium(Thermometer thermometer) {
        this.parts = new ArrayList<>();
        this.parts.add(thermometer);
        this.compatibility = new Compatability(Integer.MIN_VALUE, Integer.MAX_VALUE, Double.MIN_VALUE, Double.MAX_VALUE, Double.MIN_VALUE, Double.MAX_VALUE, Time.UNBOUNDED, Time.UNBOUNDED);
    }

    @Override
    public Compatability compatability() {
        return this.compatibility;
    }

    public void add(FormicariumPart part) {
        try {
            Compatability newCompatibility = part.compatability().compatible(this.compatability());
            if (!this.parts.contains(part)) {
                this.parts.add(part);
                this.compatibility = newCompatibility;
            }
        } catch (IllegalArgumentException e) {
            System.out.println("The part is not compatible with the formicarium.");
        }
    }

    @Override
    public Iterator<FormicariumPart> iterator() {
        return new FormicariumPartIterator(this.parts);
    }

    @Override
    public Thermometer thermometer() {
        for (FormicariumPart part : this.parts) {
            if (part instanceof Thermometer) {
                return (Thermometer) part;
            }
        }
        return null;
    }
}
