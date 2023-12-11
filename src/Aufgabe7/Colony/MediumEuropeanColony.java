package Colony;

import Formicarium.*;

public class MediumEuropeanColony implements EuropeanColony {

    // Pre: s != null
    // Post: returns BAD since medium Colonies don't fit in small Formicariums
    @Override
    public Compatability visitSmallUnconditionedFormicarium(SmallUnconditionedFormicarium s) {
        return Compatability.BAD;
    }

    // Pre: m != null
    // Post: returns GOOD since medium Colonies fit in medium Formicariums
    @Override
    public Compatability visitMediumUnconditionedFormicarium(MediumUnconditionedFormicarium m) {
        return Compatability.GOOD;
    }

    // Pre: l != null
    // Post: returns MEDIOCRE since medium Colonies occasionally occupy large Formicariums
    @Override
    public Compatability visitLargeUnconditionedFormicarium(LargeUnconditionedFormicarium l) {
        return Compatability.MEDIOCRE;
    }

    // Pre:
    // Post: returns the dynamic type of the AntColony
    @Override
    public String getType() {
        return "Medium European Colony";
    }
}
