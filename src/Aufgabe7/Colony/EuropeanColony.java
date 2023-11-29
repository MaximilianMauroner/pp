package Colony;

import Formicarium.*;

public interface EuropeanColony extends AntColony {

        default Compatability visitSmallConditionedFormicarium(SmallConditionedFormicarium s) {
            return Compatability.BAD;
        }
        default Compatability visitMediumConditionedFormicarium(MediumConditionedFormicarium m) {
            return Compatability.BAD;
        }
        default Compatability visitLargeConditionedFormicarium(LargeConditionedFormicarium l) {
            return Compatability.BAD;
        }
}
