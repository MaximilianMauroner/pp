import Colony.AntColony;
import Colony.Compatability;
import Formicarium.Formicarium;

import java.util.ArrayList;
import java.util.List;

public class Institute {

    private final List<Formicarium> inventory = new ArrayList<>();
    private final List<AntColony> antColonies = new ArrayList<>();

    public void addForm(Formicarium form) {
        this.inventory.add(form);
    }

    public void removeForm(Formicarium form) {
        this.inventory.remove(form);
    }

    public Formicarium assignForm(AntColony antColony) {

        Formicarium best = inventory.stream()
                .filter(form -> form.free())
                .max((form1, form2) -> {
                    Compatability comp1 = form1.accept(antColony);
                    Compatability comp2 = form2.accept(antColony);
                    return comp1.compareTo(comp2);
                })
                .orElse(null);

        if (best != null) {
            best.setAntType(antColony);
            antColonies.add(antColony);
        }
        return best;
    }

    public void returnForm(Formicarium form) {
        if (inventory.contains(form)) {
            form.setAntType(null);
        }
    }

    public double priceFree() {
        return inventory.stream().filter(Formicarium::free).mapToInt(Formicarium::price).sum();
    }

    public double priceOccupied() {
        return inventory.stream().filter(form -> !form.free()).mapToInt(Formicarium::price).sum();
    }

    public String showFormicarium() {
        StringBuilder sb = new StringBuilder();
        inventory.forEach(form -> sb.append(form.showFormicarium()));
        return sb.toString();
    }

    public String showAnts() {
        StringBuilder sb = new StringBuilder();
        sb.append("Ant Colonies: [ \n");

        antColonies.forEach(antColony -> {
            Formicarium form = inventory.stream().filter(formicarium -> formicarium.antType() == antColony).findFirst().orElse(null);

            sb.append(antColony.showAntColony());
            sb.append("Formicarium: ");
            if (form != null) {
                sb.append("[ ").append(form.showFormicarium()).append(" ]\n");
            } else {
                sb.append("None\n");
            }
            sb.append(", \n");
        });

        sb.append("]\n");
        return sb.toString();
    }

    public void addAntColony(AntColony antColony) {
        this.antColonies.add(antColony);
    }

    public void removeAntColony(AntColony antColony) {
        this.antColonies.remove(antColony);
    }
}
