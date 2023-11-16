package formicarium;

/**
 * Ein Messgerät oder ein Werkzeug, unabhängig davon, ob
 * es Bestandteil eines Formicariums sein kann oder nicht. Nicht jedes
 * Objekt von Instrument ist zusammen mit Formicarien verwendbar. Das Ergebnis eines Aufrufs der Methode quality besagt,
 * ob das Objekt qualitativ für die professionelle Verwendung ausgelegt
 * ist, semiprofessionellen Ansprüchen genügt oder eher nur für die
 * gelegentliche Verwendung gedacht ist.
 */
public interface Instrument extends FormicariumItem {
    String quality();
}
