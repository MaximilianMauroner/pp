public interface Nest {

    double depth = 2;

    int id();

    double width();

    double height();

    // Auslesen der Leistung der Heizung oder des Volumens des Wasser- behälters.
    // Visitor-Pattern

    // Einmaliges Setzen des Gewichts, wenn die Füllung aus einem Sand- Lehmgemisch besteht
    // maybe in Filling

    // Einmaliges Setzen der Höhe und der Breite bei Füllung mit einer Gasbetonplatte
    // maybe in Filling

    // Auslesen des Gewichts bei Füllung mit einem Sand-Lehmgemisch.
    // maybe in Filling

    // Auslesen der Höhe und der Breite bei Füllung mit einer Gasbetonplatte.
    // maybe in Filling

    // Ändern der Füllung eines Nests, wobei Informationen über frühere Füllungen verloren gehen.
    void setFilling(Filling filling);
}
