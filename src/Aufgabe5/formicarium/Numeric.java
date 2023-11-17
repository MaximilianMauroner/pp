package formicarium;

import java.util.function.DoubleUnaryOperator;

public class Numeric implements Calc<Numeric>, Rated<DoubleUnaryOperator, Numeric>, DoubleUnaryOperator {
    double t = 0;

    public Numeric(double t) {
        this.t = t;
    }

    @Override
    public Numeric sum(Numeric numeric) {
        return null;
    }

    @Override
    public Numeric rated(DoubleUnaryOperator d) {
        return new Numeric(
                d.applyAsDouble(t)
        );
    }

    @Override
    public void setCriterion(DoubleUnaryOperator d) {

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

    @Override
    public double applyAsDouble(double operand) {
        return t;
    }
}
