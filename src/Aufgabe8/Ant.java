import java.util.Stack;
import java.util.function.BiFunction;

public class Ant {
    public Ant(int ID, int node) {
        this.ID = ID;
        this.node.push(node);
    }

    public final int ID;
    public final Stack<Integer> node = new Stack<>();

    public Double L_local;

    public void move(Iteration iter, BiFunction<Iteration, Ant, Integer> selector) {
        int nextNode = selector.apply(iter, this);
        node.push(nextNode);
    }
}
