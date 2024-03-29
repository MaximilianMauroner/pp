package formicarium;

import java.util.ArrayList;
import java.util.Arrays;
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

    // Pre: thermometer is a valid Thermometer object and not null
    // Post: creates a new CompositeFormicarium object with the given thermometer
    public CompositeFormicarium(Thermometer thermometer) {
        this.parts = new ArrayList<>();
        this.parts.add(thermometer);
        this.compatibility = new Compatability(Integer.MIN_VALUE, Integer.MAX_VALUE, Double.MIN_VALUE, Double.MAX_VALUE, Double.MIN_VALUE, Double.MAX_VALUE, Time.UNBOUNDED, Time.UNBOUNDED);
    }

    // Pre: -
    // Post: creates a new CompositeFormicarium object with a null thermometer
    @Override
    public Compatability compatability() {
        return this.compatibility;
    }

    // Pre: -
    // Post: returns a deep clone of the CompositeFormicarium
    @Override
    public FormicariumItem clone() {
        CompositeFormicarium clone = new CompositeFormicarium((Thermometer) this.thermometer().clone());
        clone.parts = new ArrayList<>();
        for (FormicariumPart part : this.parts) {
            clone.parts.add((FormicariumPart) part.clone());
        }
        return clone;
    }

    // Pre: o is not null
    // Post: returns true if the given object is equal to the CompositeFormicarium, false otherwise
    @Override
    public boolean equals(Object o) {
        if (o instanceof CompositeFormicarium formicarium) {
            return this.parts.equals(formicarium.parts);
        }
        return false;
    }

    // Pre: part is not null
    // Post: adds the given part to the CompositeFormicarium if it is compatible and not already present
    public void add(FormicariumPart part) {
        try {
            Compatability newCompatibility = part.compatability().compatible(this.compatability());
            if (!containsIdentical(part)) {
                this.parts.add(part);
                this.compatibility = newCompatibility;
            }
        } catch (IllegalArgumentException e) {
            System.out.println("The part is not compatible with the formicarium.");
        }
    }

    // Pre: -
    // Post: returns the iterator over all Parts
    @Override
    public Iterator<FormicariumPart> iterator() {
        return new FormicariumPartIterator(this.parts);
    }

    // Pre: -
    // Post: returns the thermometer of the CompositeFormicarium
    @Override
    public Thermometer thermometer() {
        for (FormicariumPart part : this.parts) {
            if (part instanceof Thermometer) {
                return (Thermometer) part;
            }
        }
        return null;
    }

    // Pre: index is a valid index (0 <= index < parts.size())
    // Post: returns the part at the given index
    // Note: this method is not part of the specification but is needed for testing the CompositeFormicarium without its iterator
    public FormicariumPart get(int index) {
        return this.parts.get(index);
    }

    // Pre: part is not null and a valid (compatile) FormicariumPart object
    // Post: returns true if the CompositeFormicarium contains an identical part, false otherwise
    private boolean containsIdentical(FormicariumPart part) {
        for (FormicariumPart p : this.parts) {
            if (p == part) {
                return true;
            }
        }
        return false;
    }
}
