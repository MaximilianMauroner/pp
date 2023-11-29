package Colony;

import Formicarium.*;

public interface AntColony {

    Compatability visitSmallConditionedFormicarium(SmallConditionedFormicarium s);
    Compatability visitMediumConditionedFormicarium(MediumConditionedFormicarium m);
    Compatability visitLargeConditionedFormicarium(LargeConditionedFormicarium l);

    Compatability visitSmallUnconditionedFormicarium(SmallUnconditionedFormicarium s);
    Compatability visitMediumUnconditionedFormicarium(MediumUnconditionedFormicarium m);
    Compatability visitLargeUnconditionedFormicarium(LargeUnconditionedFormicarium l);

    String getType();

    default String showAntColony() {
        return "Ant Colony: " + getType();
    }
}
