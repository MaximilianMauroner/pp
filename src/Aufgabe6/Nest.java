public interface Nest {

    double depth = 2;
    MyList<Integer> ids = new MyList<>();

    int id();

    double width();

    double height();

    // Auslesen der Leistung der Heizung oder des Volumens des Wasser- behälters.

    // Pre: -
    // Post: returns the power if the nest is heated, otherwise 0
    int getPower();

    // Pre: -
    // Post: returns the volume of the water tank if the nest is air-conditioned, otherwise 0
    double getTankVolume();


    // Einmaliges Setzen des Gewichts, wenn die Füllung aus einem Sand-Lehmgemisch besteht
    // maybe in Filling

    // Einmaliges Setzen der Höhe und der Breite bei Füllung mit einer Gasbetonplatte
    // maybe in Filling

    Filling getFilling();

    // Auslesen des Gewichts bei Füllung mit einem Sand-Lehmgemisch.
    // maybe in Filling

    default double getSandClayWeight() {
        return getFilling().weight();
    }

    // Auslesen der Höhe und der Breite bei Füllung mit einer Gasbetonplatte.
    // maybe in Filling
    default double getAereatedConcreteHeight() {
        return getFilling().height();
    }

    default double getAereatedConcreteWidth() {
        return getFilling().width();
    }

    // Ändern der Füllung eines Nests, wobei Informationen über frühere Füllungen verloren gehen.
    void setFilling(Filling filling);


    // "Vermeiden Sie mehrfach vorkommenden Code für gleiche oder ähnliche Programmteile"
    default int calcID() {
        int id;
        do {
            id = (int)(Math.random() * 1000);
        } while (ids.contains(id));
        ids.add(id);
        return id;
    }
}
