package Colony;

import Formicarium.*;

public class SmallEuropeanColony implements EuropeanColony {

    // Pre: s != null
    // Post: returns GOOD since small Colonies fit in small Formicariums
    @Override
    public Compatability visitSmallUnconditionedFormicarium(SmallUnconditionedFormicarium s) {
        return Compatability.GOOD;
    }

    // Pre: m != null
    // Post: returns MEDIOCRE since small Colonies occasionally occupy medium Formicariums
    @Override
    public Compatability visitMediumUnconditionedFormicarium(MediumUnconditionedFormicarium m) {
        return Compatability.MEDIOCRE;
    }

    // Pre: l != null
    // Post: returns BAD since small Colonies are too small for large Formicariums
    @Override
    public Compatability visitLargeUnconditionedFormicarium(LargeUnconditionedFormicarium l) {
        return Compatability.BAD;
    }

    // Pre:
    // Post: returns the dynamic type of the AntColony
    @Override
    public String getType() {
        return "Small European Colony";
    }
}
