import Annotations.Author;
import Annotations.PostCondition;

@Author(name = "Lukas Leskovar")
public interface Filling {

    @PostCondition(condition = "returns weight double")
    double weight();

    @PostCondition(condition = "returns width double")
    double width();

    @PostCondition(condition = "returns height double")
    double height();

    @PostCondition(condition = "returns the string representation of the filling && return != null")
    String toString();
}
