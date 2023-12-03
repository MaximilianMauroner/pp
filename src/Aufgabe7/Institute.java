import Colony.AntColony;
import Colony.Compatability;
import Formicarium.Formicarium;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Institute {

    private final List<Formicarium> inventory = new ArrayList<>();
    private final HashMap<AntColony, Formicarium> antColonies = new HashMap<>();

    public void addForm(Formicarium form) {
        this.inventory.add(form);
    }

    public boolean containsForm(Formicarium formicarium) {
        return this.inventory.contains(formicarium) || this.antColonies.containsValue(formicarium);
    }

    public void removeForm(Formicarium form) {
        AntColony colony = form.antType();
        if (colony != null) {
            this.antColonies.put(colony, null);
            form.setAntType(null);
        }
        this.inventory.remove(form);
    }

    public Formicarium assignForm(AntColony antColony) {

        Formicarium best = inventory.stream()
                .filter(form -> form.accept(antColony) != Compatability.BAD)
                .min((form1, form2) -> {
                    Compatability comp1 = form1.accept(antColony);
                    Compatability comp2 = form2.accept(antColony);
                    return comp1.compareTo(comp2);
                })
                .orElse(null);

        if (best != null) {
            best.setAntType(antColony);
            antColonies.put(antColony, best);
            inventory.remove(best);
        }
        return best;
    }

    public void returnForm(Formicarium form) {
        if (antColonies.containsValue(form)) {
            AntColony colony = form.antType();
            form.setAntType(null);
            antColonies.put(colony, null);
            inventory.add(form);
        }
    }

    public double priceFree() {
        return inventory.stream().mapToInt(Formicarium::price).sum();
    }

    public double priceOccupied() {
        return antColonies.values().stream().mapToDouble(Formicarium::price).sum();
    }

    public String showFormicarium() {
        StringBuilder sb = new StringBuilder();

        List<Formicarium> li = new ArrayList<Formicarium>(antColonies.values().stream().toList());
        li.addAll(inventory);

        li.forEach(form -> sb.append(form.showFormicarium()));
        return sb.toString();
    }

    public String showAnts() {
        StringBuilder sb = new StringBuilder();
        sb.append("Ant Colonies: [ \n");

        antColonies.forEach((antColony, form) -> {

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
        this.antColonies.put(antColony, null);
    }

    public void removeAntColony(AntColony antColony) {
        Formicarium form = this.antColonies.get(antColony);
        if (form != null) {
            form.setAntType(null);
            this.inventory.add(form);
            this.antColonies.remove(antColony);
        }
    }
}
