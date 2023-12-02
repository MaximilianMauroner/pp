import Annotations.Author;
import Annotations.HistoryConstraint;
import Annotations.PostCondition;
import Annotations.PreCondition;

@Author(name = "Lukas Leskovar")
@HistoryConstraint(constraint = "client-controlled: getSandClayWeight, getAereatedConcreteHeight, getAereatedConcreteWidth can only be called after setFilling was called")
public interface Nest {

    double depth = 2;
    MyList allNests = new MyList();

    @PostCondition(condition = "returns the id of the nest")
    int id();

    @PostCondition(condition = "returns width")
    double width();

    @PostCondition(condition = "returns height")
    double height();

    // Auslesen der Leistung der Heizung oder des Volumens des Wasser- behälters.

    @PostCondition(condition = "returns the power if the nest is heated, otherwise 0")
    int getPower();

    @PostCondition(condition = "returns the volume of the water tank if the nest is air-conditioned, otherwise 0")
    double getTankVolume();

    @PostCondition(condition = "returns the weight of the sand-clay filling")
    @Author(name = "Lukas Leskovar")
    default double getSandClayWeight() {
        return getFilling().weight();
    }

    @PostCondition(condition = "returns the height of the concrete filling")
    @Author(name = "Lukas Leskovar")
    default double getAereatedConcreteHeight() {
        return getFilling().height();
    }

    @PostCondition(condition = "returns the width of the concrete filling")
    @Author(name = "Lukas Leskovar")
    default double getAereatedConcreteWidth() {
        return getFilling().width();
    }

    @PreCondition(condition = "filling != null")
    @PostCondition(condition = "this.filling() == filling, old filling is lost")
    void setFilling(Filling filling);


    // Helper
    @PostCondition(condition = "returns the filling of the nest")
    Filling getFilling();

    @PostCondition(condition = "returns true if the id is not used by another nest, otherwise false")
    @Author(name = "Lukas Leskovar")
    default boolean checkID(int id) {
        for (Object nest : Nest.allNests) {
            if (nest instanceof Nest) {
                if (((Nest) nest).id() == id) {
                    return false;
                }
            }
        }
        return true;
    }

    @PostCondition(condition = "returns a string representation of the nest")
    String toString();
}
