package formicarium;

import java.util.function.DoubleUnaryOperator;

public class Numeric implements Calc<Numeric>, Rated<DoubleUnaryOperator, Numeric>, DoubleUnaryOperator {
    private double value;
    private DoubleUnaryOperator ratingCriterion;

    public Numeric(double value) {
        this.value = value;
    }

    @Override
    public Numeric ratio(int i) {
        return new Numeric(value / i);
    }

    @Override
    public boolean atleast(Numeric numeric) {
        return this.value >= numeric.value;
    }

    @Override
    public Numeric sum(Numeric numeric) {
        return new Numeric(value + numeric.value);
    }

    @Override
    public Numeric rated(DoubleUnaryOperator d) {
        return new Numeric(
                d.applyAsDouble(value)
        );
    }

    @Override
    public void setCriterion(DoubleUnaryOperator d) {
        ratingCriterion = d;
    }

    @Override
    public Numeric rated() {
        return rated(ratingCriterion);
    }

    @Override
    public double applyAsDouble(double operand) {
        if (ratingCriterion == null) {
            return value; // return default value if no criterion is set
        }
        return ratingCriterion.applyAsDouble(operand);
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
