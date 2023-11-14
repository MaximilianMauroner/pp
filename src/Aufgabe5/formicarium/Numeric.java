package formicarium;

import java.util.function.DoubleUnaryOperator;

public class Numeric implements Calc<Numeric>, Rated<DoubleUnaryOperator, Numeric> {
    double val;

    @Override
    public Numeric sum(Numeric numeric) {
        return null;
    }

    @Override
    public Numeric ratio(int i) {
        return null;
    }

    @Override
    public boolean atleast(Numeric numeric) {
        return false;
    }

    @Override
    public Numeric rated(DoubleUnaryOperator doubleUnaryOperator) {
        return null;
    }

    @Override
    public void setCriterium(DoubleUnaryOperator doubleUnaryOperator) {

    }

    @Override
    public Numeric rated() {
        return null;
    }
}
