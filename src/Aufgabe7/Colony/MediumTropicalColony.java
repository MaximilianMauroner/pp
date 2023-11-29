package Colony;

import Formicarium.*;

public class MediumTropicalColony implements TropicalColony {
    @Override
    public Compatability visitSmallConditionedFormicarium(SmallConditionedFormicarium s) {
        return Compatability.BAD;
    }

    @Override
    public Compatability visitMediumConditionedFormicarium(MediumConditionedFormicarium m) {
        return Compatability.GOOD;
    }

    @Override
    public Compatability visitLargeConditionedFormicarium(LargeConditionedFormicarium l) {
        return Compatability.MEDIOCRE;
    }

    @Override
    public String getType() {
        return "Medium Tropical Colony";
    }
}
