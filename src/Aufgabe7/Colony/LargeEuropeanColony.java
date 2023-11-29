package Colony;

import Formicarium.*;

public class LargeEuropeanColony implements EuropeanColony {

    @Override
    public Compatability visitSmallUnconditionedFormicarium(SmallUnconditionedFormicarium s) {
        return Compatability.BAD;
    }

    @Override
    public Compatability visitMediumUnconditionedFormicarium(MediumUnconditionedFormicarium m) {
        return Compatability.BAD;
    }

    @Override
    public Compatability visitLargeUnconditionedFormicarium(LargeUnconditionedFormicarium l) {
        return Compatability.GOOD;
    }

    @Override
    public String getType() {
        return "Large European Colony";
    }
}
