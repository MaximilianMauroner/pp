package Colony;

import Formicarium.*;

public interface TropicalColony extends AntColony {

    default Compatability visitSmallUnconditionedFormicarium(SmallUnconditionedFormicarium s) {
        return Compatability.BAD;
    }

    default Compatability visitMediumUnconditionedFormicarium(MediumUnconditionedFormicarium m) {
        return Compatability.BAD;
    }

    default Compatability visitLargeUnconditionedFormicarium(LargeUnconditionedFormicarium l) {
        return Compatability.BAD;
    }
}
