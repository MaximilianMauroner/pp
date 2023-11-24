public interface Nest {

    double depth = 2;
    MyList<Nest> nests = new MyList<>();

    int id();

    double width();

    double height();

    // Auslesen der Leistung der Heizung oder des Volumens des Wasser- behälters.

    // Pre: -
    // Post: returns the power if the nest is heated, otherwise 0
    int getPower();

    String toString();

    // Pre: -
    // Post: returns the volume of the water tank if the nest is air-conditioned, otherwise 0
    double getTankVolume();

    // Auslesen des Gewichts bei Füllung mit einem Sand-Lehmgemisch.
    default double getSandClayWeight() {
        return getFilling().weight();
    }

    // Auslesen der Höhe und der Breite bei Füllung mit einer Gasbetonplatte.
    default double getAereatedConcreteHeight() {
        return getFilling().height();
    }
    default double getAereatedConcreteWidth() {
        return getFilling().width();
    }

    // Ändern der Füllung eines Nests, wobei Informationen über frühere Füllungen verloren gehen.
    void setFilling(Filling filling);


    // Helper
    Filling getFilling();

    // "Vermeiden Sie mehrfach vorkommenden Code für gleiche oder ähnliche Programmteile"
    default boolean checkID() {
        for (Object nest : nests) {
            if (nest instanceof Nest) {
                if (((Nest) nest).id() == id()) {
                    return false;
                }
            }
        }
        return true;
    }

}
