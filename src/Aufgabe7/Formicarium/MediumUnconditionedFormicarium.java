package Formicarium;

import Colony.AntColony;
import Colony.Compatability;

public class MediumUnconditionedFormicarium implements Formicarium {
    private final int price;
    private AntColony antType;

    public MediumUnconditionedFormicarium(int price) {
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
        return visitor.visitMediumUnconditionedFormicarium(this);
    }

    @Override
    public String getType() {
        return "Medium Unconditioned Formicarium";
    }
}
