package datastore;

import java.util.HashMap;

/**
 * Also generic data store that stores the data of a singe simulation run
 * <p>
 * Modularization Units:
 * - Objects for the datastore and the simulation
 * - Module for all data and utility methods for accessing the data
 * <p>
 * Abstraction: A abstraction of all data points that can be associated with a simulation.
 */
@SuppressWarnings("unchecked")
public class Simulation {
    private final String simulationId; // (client-controlled history-constraint: simulationId is unique, call UUID.randomUUID().toString() to generate a unique id)
    private final String simulationName;

    private final HashMap<String, Object> data;


    /**
     * Creates a new Simulation object
     *
     * @param simulationId   the id of the simulation (should be unique) (precondition: simulationId != null)
     * @param simulationName the name of the simulation (precondition: simulationName != null)
     */
    public Simulation(String simulationId, String simulationName) {
        this.simulationId = simulationId;
        this.simulationName = simulationName;
        this.data = new HashMap<>();
    }

    /**
     * Returns the id of the simulation
     *
     * @return the id of the simulation
     */
    public String getSimulationId() {
        return simulationId;
    }

    /**
     * Returns the name of the simulation
     *
     * @return the name of the simulation
     */
    public String getSimulationName() {
        return simulationName;
    }

    /**
     * Adds a value to the simulations data map. If the key already exists, the value will be overwritten
     *
     * @param key   the identifier of the value (precondition: key != null)
     * @param value the value to be added
     */
    public void addData(String key, Object value) {
        data.put(key, value);
    }

    /**
     * Returns the value of the given key
     *
     * @param key the identifier of the value (precondition: key != null)
     * @return the value of the given key. If no key exists or the objects stored at that key is null, null will be returned
     */
    public Object getData(String key) {
        return data.get(key);
    }

    /**
     * Export the simulation data
     *
     * @return clone of the simulation data hashmap
     */
    public HashMap<String, Object> getData() {
        return (HashMap<String, Object>) this.data.clone();
    }

    /**
     * @return the simulation data as a string
     */
    @Override
    public String toString() {
        return "Simulation{" +
                "simulationId=" + simulationId +
                ", simulationName='" + simulationName + '\'' +
                ", data=" + data.toString() +
                '}';
    }
}
