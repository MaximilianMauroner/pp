package Colony;

import Formicarium.*;

public class SmallTropicalColony implements TropicalColony {

    // Pre: s != null
    // Post: returns GOOD since small Colonies fit in small Formicariums
    @Override
    public Compatability visitSmallConditionedFormicarium(SmallConditionedFormicarium s) {
        return Compatability.GOOD;
    }

    // Pre: m != null
    // Post: returns MEDIOCRE since small Colonies occasionally occupy medium Formicariums
    @Override
    public Compatability visitMediumConditionedFormicarium(MediumConditionedFormicarium m) {
        return Compatability.MEDIOCRE;
    }

    // Pre: l != null
    // Post: returns BAD since small Colonies are too small for large Formicariums
    @Override
    public Compatability visitLargeConditionedFormicarium(LargeConditionedFormicarium l) {
        return Compatability.BAD;
    }

    // Pre:
    // Post: returns the dynamic type of the AntColony
    @Override
    public String getType() {
        return "Small Tropical Colony";
    }
}
