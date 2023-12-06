import Colony.AntColony;
import Colony.Compatability;
import Formicarium.Formicarium;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class Institute {

    private final List<Formicarium> inventory = new ArrayList<>();
    private final HashMap<AntColony, Formicarium> antColonies = new HashMap<>();

    // Pre: form != null
    // Post: adds the Formicarium to the inventory
    public void addForm(Formicarium form) {
        this.inventory.add(form);
    }

    // Pre: form != null
    // Post: returns true if the inventory contains the Formicarium or if the Formicarium is assigned to an AntColony
    public boolean containsForm(Formicarium formicarium) {
        return this.inventory.contains(formicarium) || this.antColonies.containsValue(formicarium);
    }

    // Pre: form != null
    // Post: removes the Formicarium from the inventory and removes the assignment from the AntColony
    public void removeForm(Formicarium form) {
        AntColony colony = form.antType();
        if (colony != null) {
            this.antColonies.put(colony, null);
            form.setAntType(null);
        }
        this.inventory.remove(form);
    }

    // Pre: antColony != null
    // Post: returns the Formicarium fitting the AntColony with the best Compatability (null if none, first if multiple)
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

    // Pre: form != null
    // Post: removes the assignment from the AntColony and adds the Formicarium back to the inventory
    public void returnForm(Formicarium form) {
        if (antColonies.containsValue(form)) {
            AntColony colony = form.antType();
            form.setAntType(null);
            antColonies.put(colony, null);
            inventory.add(form);
        }
    }

    // Pre:
    // Post: returns the price of all Formicaria in the inventory
    public double priceFree() {
        return inventory.stream().mapToInt(Formicarium::price).sum();
    }

    // Pre:
    // Post: returns the price of all Formicaria assigned to an AntColony
    public double priceOccupied() {
        return antColonies.values().stream().filter(Objects::nonNull).mapToDouble(Formicarium::price).sum();
    }

    // Pre:
    // Post: returns a String representation of the inventory
    public String showFormicarium() {
        StringBuilder sb = new StringBuilder();

        List<Formicarium> li = new ArrayList<Formicarium>(antColonies.values().stream().toList());
        li.addAll(inventory);

        li.stream().filter(Objects::nonNull).forEach(form -> sb.append(form.showFormicarium()));
        return sb.toString();
    }

    // Pre:
    // Post: returns a String representation of the AntColonies and their assigned Formicaria
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

    // Pre: antColony != null
    // Post: adds the AntColony to the Institute (if not already in it)
    public void addAntColony(AntColony antColony) {
        if (!this.antColonies.containsKey(antColony))
            this.antColonies.put(antColony, null);
    }

    // Pre: antColony != null
    // Post: returns true if the AntColony is in the Institute
    public boolean containsAnt(AntColony antColony) {
        return this.antColonies.containsKey(antColony);
    }

    // Pre: antColony != null
    // Post: removes the AntColony from the Institute (frees the Formicarium if assigned)
    public void removeAntColony(AntColony antColony) {
        if (this.antColonies.containsKey(antColony)) {
            Formicarium form = this.antColonies.get(antColony);
            if (form != null) {
                form.setAntType(null);
                this.inventory.add(form);
            }
            this.antColonies.remove(antColony);
        }
    }
}
