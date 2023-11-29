package Colony;

import Formicarium.*;

public class SmallTropicalColony implements TropicalColony {
    @Override
    public Compatability visitSmallConditionedFormicarium(SmallConditionedFormicarium s) {
        return Compatability.GOOD;
    }

    @Override
    public Compatability visitMediumConditionedFormicarium(MediumConditionedFormicarium m) {
        return Compatability.MEDIOCRE;
    }

    @Override
    public Compatability visitLargeConditionedFormicarium(LargeConditionedFormicarium l) {
        return Compatability.BAD;
    }

    @Override
    public String getType() {
        return "Small Tropical Colony";
    }
}
