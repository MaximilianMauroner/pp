package formicarium;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

/**
 * Eine (möglicherweise leere) Menge von Formicarien
 * oder Bestandteilen von Formicarien oder Messgeräten bzw. Werkzeugen, die zusammen mit Formicarien verwendbar sind, unabhängig davon, ob sie als Teil eines Formicariums angesehen werden oder
 * nicht. Es ist nicht notwendig (aber erlaubt), dass alle Elemente der
 * Menge zusammen ein Formicarium ergeben. Z. B. könnte es sich bei
 * der Menge auch um einen Lagerbestand oder eine Bestellliste handeln. Über einen Iterator sind die jeweils voneinander verschiedenen
 * Elemente der Menge auflistbar (gleiche Elemente nicht mehrfach).
 * Eine Methode count im Iterator retourniert die Anzahl der vorhandenen Elemente, die gleich dem zuletzt von next zurückgegebenen
 * Element sind. Die Methode remove des Iterators (ohne Argument)
 * verringert die Anzahl vorhandener Elemente (gleich dem, das zuletzt von next zurückgegeben wurde) um 1, eine Methode remove
 * des Iterators mit einer Zahl größer 0 als Argument um die gegebene Anzahl gleicher Elemente, sofern eine ausreichende Zahl gleicher
 * Elemente vorhanden ist. Eine Methode add mit einem Parameter
 * in einem Objekt von FormicariumSet fügt ein neues Element hinzu, sofern das identische Element nicht schon vorhanden ist (gleiche
 * Elemente können jedoch mehrfach vorhanden sein).
 */
public class FormicariumSet implements Iterable<FormicariumItem> {
    Set<FormicariumItem> items;

    // Pre: -
    // Post: creates a new FormicariumSet object
    public FormicariumSet() {
        this.items = new HashSet<>();
    }

    // Pre: item is not null and a valid FormicariumItem object
    // Post: adds the given item to the FormicariumSet
    public void add(FormicariumItem item) {
        this.items.add(item);
    }

    public Set<FormicariumItem> getItems() {
        return items;
    }

    // Pre: -
    // Post: returns an iterator over all FormicariumItems
    @Override
    public Iterator<FormicariumItem> iterator() {
        return new FormicariumItemIterator(this.items);
    }
}
