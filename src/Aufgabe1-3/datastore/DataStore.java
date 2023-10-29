package datastore;

import java.util.ArrayList;
import java.util.List;

/**
 * A very generic data store that stores multiple simulations
 *
 * Modularization Units:
 * - Objects for the datastore and the simulations
 * - Module for all utility methods for accessing the data/simulations
 *
 * Abstraction: A simulation of a data storage system
 */

public class DataStore {
    private List<Simulation> simulationList = new ArrayList<>();

    /**
     * Adds a simulation to the datastore
     *
     * @param simulation the simulation to be added
     */
    public void addSimulation(Simulation simulation) {
        simulationList.add(simulation);
    }

    /**
     * Returns the simulation with the given id
     *
     * @param simulationId the id of the simulation
     * @return the simulation with the given id
     */
    public Simulation getSimulation(String simulationId) {
        for (Simulation simulation : simulationList) {
            if (simulation.getSimulationId().equals(simulationId)) {
                return simulation;
            }
        }
        return null;
    }

    /**
     * Returns the simulation list
     *
     * @return the simulation list
     */
    public List<Simulation> getSimulationList() {
        return simulationList;
    }

    /**
     * Removes the simulation with the given id
     *
     * @param simulationId the id of the simulation to be removed
     */
    public void removeSimulation(String simulationId) {
        //STYLE: Funktionale Programmierung (es wird hier eine Lambda Funktion verwendet)
        simulationList.removeIf(simulation -> simulation.getSimulationId().equals(simulationId));
    }

    /**
     * Removes the given simulation
     *
     * @param simulation the simulation to be removed
     */
    public void removeSimulation(Simulation simulation) {
        simulationList.remove(simulation);
    }

    /**
     * Clears the simulation list
     */
    public void clear() {
        simulationList.clear();
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Simulation simulation : simulationList) {
            stringBuilder.append(simulation.toString());
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }

}
