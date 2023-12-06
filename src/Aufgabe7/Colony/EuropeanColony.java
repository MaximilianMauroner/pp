package Colony;

import Formicarium.*;

public interface EuropeanColony extends AntColony {

        // Pre: s != null
        // Post: returns BAD since European Ants are not compatible with Conditioned Formicarium
        default Compatability visitSmallConditionedFormicarium(SmallConditionedFormicarium s) {
            return Compatability.BAD;
        }

        // Pre: m != null
        // Post: returns BAD since European Ants are not compatible with Conditioned Formicarium
        default Compatability visitMediumConditionedFormicarium(MediumConditionedFormicarium m) {
            return Compatability.BAD;
        }

        // Pre: l != null
        // Post: returns BAD since European Ants are not compatible with Conditioned Formicarium
        default Compatability visitLargeConditionedFormicarium(LargeConditionedFormicarium l) {
            return Compatability.BAD;
        }
}
