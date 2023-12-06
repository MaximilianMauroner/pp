package Colony;

import Formicarium.*;

public interface AntColony {

    // Pre: s != null
    // Post: returns Compatability with Formicarium s
    Compatability visitSmallConditionedFormicarium(SmallConditionedFormicarium s);

    // Pre: m != null
    // Post: returns Compatability with Formicarium m
    Compatability visitMediumConditionedFormicarium(MediumConditionedFormicarium m);

    // Pre: l != null
    // Post: returns Compatability with Formicarium l
    Compatability visitLargeConditionedFormicarium(LargeConditionedFormicarium l);

    // Pre: s != null
    // Post: returns Compatability with Formicarium s
    Compatability visitSmallUnconditionedFormicarium(SmallUnconditionedFormicarium s);

    // Pre: m != null
    // Post: returns Compatability with Formicarium m
    Compatability visitMediumUnconditionedFormicarium(MediumUnconditionedFormicarium m);

    // Pre: l != null
    // Post: returns Compatability with Formicarium l
    Compatability visitLargeUnconditionedFormicarium(LargeUnconditionedFormicarium l);

    // Pre:
    // Post: returns the dynamic type of the AntColony
    String getType();

    // Pre:
    // Post: returns a String representation of the AntColony
    default String showAntColony() {
        return "Ant Colony: " + getType();
    }
}
