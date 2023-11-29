package Formicarium;

import Colony.AntColony;
import Colony.Compatability;

public class LargeUnconditionedFormicarium implements Formicarium {
    private final int price;
    private AntColony antType;

    public LargeUnconditionedFormicarium(int price) {
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
        return visitor.visitLargeUnconditionedFormicarium(this);
    }

    @Override
    public String getType() {
        return "Large Unconditioned Formicarium";
    }
}
