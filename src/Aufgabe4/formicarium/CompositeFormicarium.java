package formicarium;

/**
 * ToDo: Write out specification and add subtype relation as stated
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
public class CompositeFormicarium {
}
