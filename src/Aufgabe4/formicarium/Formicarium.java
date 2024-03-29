package formicarium;

import java.util.Iterator;
import java.util.List;

/**
 * Ein zur Haltung von Ameisen vorgesehenes Terrarium mit
 * allen dazu gehörenden Bestandteilen. Formicarien können änderbar
 * (vor allem erweiterbar) sein, aber nicht jedes Formicarium ist änderbar. Die Methode iterator gibt einen neuen Iterator zurück, der
 * über alle Bestandteile des Formicariums iteriert (falls das Formicarium aus Bestandteilen zusammengesetzt ist; mehrfach vorkommende
 * gleiche, aber nicht identische Bestandteile werden entsprechend oft,
 * jeweils mit unterschiedlicher Identität retourniert) oder nur einmalig this zurückgibt (falls das Formicarium nicht weiter in Bestandteile zerlegbar ist). Die Methode compatibility gibt ein Objekt
 * vom Typ Compatible zurück, das die vom Formicarium gebotenen
 * Umweltbedingungen beschreibt.
 */
public interface Formicarium extends FormicariumPart, Iterable<FormicariumPart> {
    // Pre: -
    // Post: returns the thermometer of the Formicarium
    Thermometer thermometer();
}
