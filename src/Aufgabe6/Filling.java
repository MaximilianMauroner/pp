import Annotations.Author;
import Annotations.PostCondition;

@Author(name = "Lukas Leskovar")
public interface Filling {

    @PostCondition(condition = "returns weight of the filling if it is sand-clay, otherwise 0")
    double weight();

    @PostCondition(condition = "returns width of the filling if it is concrete, otherwise 0")
    double width();

    @PostCondition(condition = "returns height of the filling if it is concrete, otherwise 0")
    double height();

    @PostCondition(condition = "returns the string representation of the filling")
    String toString();
}
