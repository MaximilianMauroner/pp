package Colony;

import Formicarium.*;

public class SmallEuropeanColony implements EuropeanColony {

    @Override
    public Compatability visitSmallUnconditionedFormicarium(SmallUnconditionedFormicarium s) {
        return Compatability.GOOD;
    }

    @Override
    public Compatability visitMediumUnconditionedFormicarium(MediumUnconditionedFormicarium m) {
        return Compatability.MEDIOCRE;
    }

    @Override
    public Compatability visitLargeUnconditionedFormicarium(LargeUnconditionedFormicarium l) {
        return Compatability.BAD;
    }

    @Override
    public String getType() {
        return "Small European Colony";
    }
}
