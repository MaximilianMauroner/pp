package Colony;

import Formicarium.*;

public class LargeEuropeanColony implements EuropeanColony {

    // Pre: s != null
    // Post: returns BAD since large Colonies don't fit in small Formicariums
    @Override
    public Compatability visitSmallUnconditionedFormicarium(SmallUnconditionedFormicarium s) {
        return Compatability.BAD;
    }

    // Pre: m != null
    // Post: returns BAD since large Colonies don't fit in medium Formicariums
    @Override
    public Compatability visitMediumUnconditionedFormicarium(MediumUnconditionedFormicarium m) {
        return Compatability.BAD;
    }

    // Pre: l != null
    // Post: returns GOOD since large Colonies fit in large Formicariums
    @Override
    public Compatability visitLargeUnconditionedFormicarium(LargeUnconditionedFormicarium l) {
        return Compatability.GOOD;
    }

    // Pre:
    // Post: returns the dynamic type of the AntColony
    @Override
    public String getType() {
        return "Large European Colony";
    }
}
