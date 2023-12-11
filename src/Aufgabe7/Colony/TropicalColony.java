package Colony;

import Formicarium.*;

public interface TropicalColony extends AntColony {

    // Pre: s != null
    // Post: returns BAD since Tropical Ants are not compatible with Unconditioned Formicarium
    default Compatability visitSmallUnconditionedFormicarium(SmallUnconditionedFormicarium s) {
        return Compatability.BAD;
    }

    // Pre: m != null
    // Post: returns BAD since Tropical Ants are not compatible with Unconditioned Formicarium
    default Compatability visitMediumUnconditionedFormicarium(MediumUnconditionedFormicarium m) {
        return Compatability.BAD;
    }

    // Pre: l != null
    // Post: returns BAD since Tropical Ants are not compatible with Unconditioned Formicarium
    default Compatability visitLargeUnconditionedFormicarium(LargeUnconditionedFormicarium l) {
        return Compatability.BAD;
    }
}
