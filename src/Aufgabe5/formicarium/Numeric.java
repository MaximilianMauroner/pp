package formicarium;

import java.util.function.DoubleUnaryOperator;

public class Numeric implements Calc<Numeric>, Rated<DoubleUnaryOperator, Numeric>, DoubleUnaryOperator {
    private double value;
    private DoubleUnaryOperator ratingCriterion;

    // Pre: -
    // Post: initializes this object with the given parameters
    public Numeric(double value) {
        this.value = value;
    }

    // Pre: -
    // Post: returns a new Numeric object containing the result of the division of this and i
    @Override
    public Numeric ratio(int i) {
        return new Numeric(value / i);
    }

    // Pre: -
    // Post: returns true if the value of this is at least as big as numerics value
    @Override
    public boolean atleast(Numeric numeric) {
        return this.value >= numeric.value;
    }

    // Pre: -
    // Post: returns a new Numeric object containing the sum of this and numeric
    @Override
    public Numeric sum(Numeric numeric) {
        return new Numeric(value + numeric.value);
    }

    // Pre: -
    // Post: returns a new Numeric object containing the result of the application of d to this
    @Override
    public Numeric rated(DoubleUnaryOperator d) {
        return new Numeric(
                d.applyAsDouble(value)
        );
    }

    // Pre: -
    // Post: sets the rating criterion in this to d
    @Override
    public void setCriterion(DoubleUnaryOperator d) {
        ratingCriterion = d;
    }

    // Pre: -
    // Post: returns a Numeric object with the rating of this and the rating criterion (if set, otherwise the value of this)
    @Override
    public Numeric rated() {
        if (ratingCriterion == null) {
            return new Numeric(value);
        }
        return rated(ratingCriterion);
    }

    // Pre: -
    // Post: returns the result of the application of the rating criterion to operand (if set, otherwise the value of this)
    @Override
    public double applyAsDouble(double operand) {
        if (ratingCriterion == null) {
            return value;
        }
        return ratingCriterion.applyAsDouble(operand);
    }

    // Pre: -
    // Post: returns the value of this as a String
    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
