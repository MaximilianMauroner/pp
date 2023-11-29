package datastore;

import java.util.ArrayList;
import java.util.List;

/**
 * A very generic data store that stores multiple simulations
 * <p>
 * Modularization Units:
 * - Objects for the datastore and the simulations
 * - Module for all utility methods for accessing the data/simulations
 * <p>
 * Abstraction: A simulation of a data storage system
 */

public class DataStore {
    private final List<Simulation> simulationList = new ArrayList<>();

    /**
     * Adds a simulation to the datastore list
     *
     * @param simulation the simulation to be added (precondition: simulation != null)
     */
    public void addSimulation(Simulation simulation) {
        simulationList.add(simulation);
    }

    /**
     * Returns the simulation with the given id
     *
     * @param simulationId the id of the simulation
     * @return the simulation with the given id. If no simulation with that id exists, null will be returned
     */
    public Simulation getSimulation(String simulationId) {
        return simulationList.stream().filter(
                simulation -> simulation.getSimulationId().equals(simulationId)
        ).findFirst().orElse(null);
    }

    /**
     *
     * @return the simulation list
     */
    public List<Simulation> getSimulationList() {
        return simulationList;
    }

    /**
     * Removes the simulation with the given id if there is a simulation with that id
     *
     * @param simulationId the id of the simulation to be removed
     */
    public void removeSimulation(String simulationId) {
        //STYLE: Funktionale Programmierung (es wird hier eine Lambda-Funktion verwendet)
        simulationList.removeIf(simulation -> simulation.getSimulationId().equals(simulationId));
    }

    /**
     * Removes the given simulation if it is in the simulation list
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

    /**
     * @return all data stored in all simulations
     */
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        simulationList.forEach(simulation -> stringBuilder.append(simulation.toString()).append("\n"));
        return stringBuilder.toString();
    }

}
