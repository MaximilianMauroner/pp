package Formicarium;

import Colony.AntColony;
import Colony.Compatability;

public class LargeConditionedFormicarium implements Formicarium {
    private final int price;
    private AntColony antType;

    public LargeConditionedFormicarium(int price) {
        this.price = price;
    }

    @Override
    public int price() {
        return price;
    }

    @Override
    public AntColony antType() {
        return antType;
    }

    @Override
    public void setAntType(AntColony antType) {
        this.antType = antType;
    }

    @Override
    public Compatability accept(AntColony visitor) {
        return visitor.visitLargeConditionedFormicarium(this);
    }

    @Override
    public String getType() {
        return "Large Conditioned Formicarium";
    }
}
