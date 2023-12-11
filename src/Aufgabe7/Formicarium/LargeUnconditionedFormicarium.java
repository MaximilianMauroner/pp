package Formicarium;

import Colony.AntColony;
import Colony.Compatability;

public class LargeUnconditionedFormicarium implements Formicarium {
    private final int price;
    private AntColony antType;

    // Pre: price >= 0
    // Post: constructs a new LargeUnconditionedFormicarium with the given price
    public LargeUnconditionedFormicarium(int price) {
        this.price = price;
    }

    // Pre:
    // Post: returns the price of the Formicarium
    @Override
    public int price() {
        return price;
    }

    // Pre:
    // Post: returns the AntColony that is currently in the Formicarium (null if free)
    @Override
    public AntColony antType() {
        return antType;
    }

    // Pre:
    // Post: sets the AntColony that is currently in the Formicarium (or null if free)
    @Override
    public void setAntType(AntColony antType) {
        this.antType = antType;
    }

    // Pre: visitor != null
    // Post: returns the Compatability of the Formicarium with the AntColony (visitor)
    @Override
    public Compatability accept(AntColony visitor) {
        return visitor.visitLargeUnconditionedFormicarium(this);
    }

    // Pre:
    // Post: returns the dynamic type of the Formicarium
    @Override
    public String getType() {
        return "Large Unconditioned Formicarium";
    }
}
