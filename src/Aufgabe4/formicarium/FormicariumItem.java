package formicarium;

/**
 * Ein Formicarium oder ein Bestandteil eines Formicariums oder ein Messgerät bzw. Werkzeug, das zusammen mit
 * Formicarien verwendbar ist, unabhängig davon, ob es als Teil eines Formicariums angesehen werden kann oder nicht. Die Methode
 * compatibility gibt ein Objekt vom Typ Compatible zurück. Wenn
 * ein von Compatible beschriebenes Umweltkriterium nicht relevant
 * ist, wird dafür der größtmögliche Wertebereich angenommen.
 */
public interface FormicariumItem {
    // Pre: -
    // Post: returns the compatability of the FormicariumItem
    Compatability compatability();

    // Pre: -
    // Post: returns a clone of the FormicariumItem
    FormicariumItem clone();
}
