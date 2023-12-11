package Colony;

import Formicarium.*;

public class LargeTropicalColony implements TropicalColony {
    // Pre: s != null
    // Post: returns BAD since large Colonies don't fit in small Formicariums
    @Override
    public Compatability visitSmallConditionedFormicarium(SmallConditionedFormicarium s) {
        return Compatability.BAD;
    }

    // Pre: m != null
    // Post: returns BAD since large Colonies don't fit in medium Formicariums
    @Override
    public Compatability visitMediumConditionedFormicarium(MediumConditionedFormicarium m) {
        return Compatability.BAD;
    }

    // Pre: l != null
    // Post: returns GOOD since large Colonies fit in large Formicariums
    @Override
    public Compatability visitLargeConditionedFormicarium(LargeConditionedFormicarium l) {
        return Compatability.GOOD;
    }

    // Pre:
    // Post: returns the dynamic type of the AntColony
    @Override
    public String getType() {
        return "Large Tropical Colony";
    }
}
