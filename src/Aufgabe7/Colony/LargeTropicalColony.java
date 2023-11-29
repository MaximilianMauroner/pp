package Colony;

import Formicarium.*;

public class LargeTropicalColony implements TropicalColony {
    @Override
    public Compatability visitSmallConditionedFormicarium(SmallConditionedFormicarium s) {
        return Compatability.BAD;
    }

    @Override
    public Compatability visitMediumConditionedFormicarium(MediumConditionedFormicarium m) {
        return Compatability.BAD;
    }

    @Override
    public Compatability visitLargeConditionedFormicarium(LargeConditionedFormicarium l) {
        return Compatability.GOOD;
    }

    @Override
    public String getType() {
        return "Large Tropical Colony";
    }
}
