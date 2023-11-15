package formicarium;

public interface Calc<R extends Calc<R>> {
    R sum(R r);

    R ratio(int i);

    boolean atleast(R r);
}
