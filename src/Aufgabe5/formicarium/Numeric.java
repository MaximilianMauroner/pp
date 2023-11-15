package formicarium;

import java.util.function.DoubleUnaryOperator;

public class Numeric implements Calc<Numeric>, Rated<DoubleUnaryOperator, Numeric>  {
    int t = 0;

    @Override
    public Numeric sum(Numeric numeric) {
        return null;
    }

    @Override
    public Numeric rated(DoubleUnaryOperator d) {
        return null;
    }

    @Override
    public void setCriterium(DoubleUnaryOperator d) {

    }

    @Override
    public Numeric ratio(int i) {
        return this;
    }

    @Override
    public boolean atleast(Numeric numeric) {
        return false;
    }

    @Override
    public Numeric rated() {
        return this;
    }
}
