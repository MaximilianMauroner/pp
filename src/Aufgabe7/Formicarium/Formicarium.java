package Formicarium;

import Colony.AntColony;
import Colony.Compatability;

public interface Formicarium {

    // Pre:
    // Post: returns the price of the Formicarium
    int price();

    // Pre:
    // Post: returns the AntColony that is currently in the Formicarium (null if free)
    AntColony antType();

    // Pre:
    // Post: sets the AntColony that is currently in the Formicarium (or null if free)
    void setAntType(AntColony antType);

    // Pre: visitor != null
    // Post: returns the Compatability of the Formicarium with the AntColony (visitor)
    Compatability accept(AntColony visitor);

    // Pre:
    // Post: returns the dynamic type of the Formicarium
    String getType();

    // Pre:
    // Post: returns true if the Formicarium is free
    default boolean free() {
        return antType() == null;
    }

    // Pre:
    // Post: returns a String representation of the Formicarium
    default String showFormicarium() {
        StringBuilder sb = new StringBuilder();
        sb.append("FormicariumType: ").append(getType()).append("\n");
        sb.append("Price: ").append(price()).append("\n");
        if (free()) {
            sb.append("Status: Free\n");
        } else {
            sb.append("Status: Occupied\n");
            sb.append(antType().showAntColony()).append("\n");
        }

        return sb.toString();
    }
}
