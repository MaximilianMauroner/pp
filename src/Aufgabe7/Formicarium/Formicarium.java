package Formicarium;

import Colony.AntColony;
import Colony.Compatability;

public interface Formicarium {

    int price();

    AntColony antType();

    void setAntType(AntColony antType);

    Compatability accept(AntColony visitor);

    String getType();

    default boolean free() {
        return antType() == null;
    }

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
