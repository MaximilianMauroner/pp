package Colony;

import Formicarium.*;

public class MediumEuropeanColony implements EuropeanColony {

    @Override
    public Compatability visitSmallUnconditionedFormicarium(SmallUnconditionedFormicarium s) {
        return Compatability.BAD;
    }

    @Override
    public Compatability visitMediumUnconditionedFormicarium(MediumUnconditionedFormicarium m) {
        return Compatability.GOOD;
    }

    @Override
    public Compatability visitLargeUnconditionedFormicarium(LargeUnconditionedFormicarium l) {
        return Compatability.MEDIOCRE;
    }

    @Override
    public String getType() {
        return "Medium European Colony";
    }
}
