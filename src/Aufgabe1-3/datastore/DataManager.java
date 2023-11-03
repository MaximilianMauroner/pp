package datastore;

import java.util.Vector;

// NOTE: Generally with this whole DataStore concept, I think choosing Object as the type for all data entries is a bad idea.
// We are really just dealing with numbers and vectors of numbers. So we should use Number and Vector<Number> instead.

// GOOD (object oriented): Coupling: We can mostly change the DataStore and Simulation without affecting the DataManager.
// As long as the interface for getting and setting data looks the same, we can change the implementation.

/**
 * Manages the data of the simulation
 * <p>
 * Modularization Units:
 * - Objects for the datastore and the (current) simulation
 * - A object for the singleton instance of the DataManager
 * - Module for all the methods/operations that store/access data for a specific simulation (useful for making sure the data is stored in a consistent/uniform way)
 * - Sort of represents a Component as it is a standalone piece of software and can be used regardless of the data it stores
 * <p>
 * Abstraction: If we follow the slides, the DataManager is a simulation of a data storage system. But in reality it is just a wrapper for the DataStore and the Simulation.
 * <p>
 * STYLE: Objektorientierte Programmierung (es wird hier das Singleton Pattern verwendet was klar auf Objektorientierung hinweist)
 */
@SuppressWarnings("unchecked")
public class DataManager {
    private static DataManager instance;
    private final DataStore dataStore;
    private Simulation simulation; // (invariant) should be non-null

    private DataManager() {
        dataStore = new DataStore();
    }

    /**
     * Returns the instance of the DataManager and sets the simulation
     *
     * @param simulation the current simulation object that stores current data. Should be non-null
     * @return the singleton instance of the DataManager
     */
    public static DataManager getInstance(Simulation simulation) {
        if (instance == null) {
            instance = new DataManager();
        }

        instance.simulation = simulation;
        return instance;
    }

    /**
     * Returns the instance of the DataManager without setting the simulation
     *
     * @return the singleton instance of the DataManager
     */
    public static DataManager getInstance() {
        if (instance == null) {
            instance = new DataManager();
        }

        return instance;
    }

    /**
     * Checks whether the simulation contains a field with the given key
     *
     * @return boolean for "is the key in the simulation"
     */
    public boolean containsField(String key) {
        return simulation != null && simulation.getData(key) != null;
    }

    /**
     * Adds a simple field (e.g. a number) to the simulation. If field already exists under key, it will be overwritten.
     *
     * @param key   identifier of the data-field
     * @param value value of the data-field
     */
    public void addSimpleField(String key, Object value) {
        if (simulation != null)
            simulation.addData(key, value);
    }

    /**
     * Returns the value of a simple field (e.g. a number) in the simulation
     *
     * @param key identifier of the data-field
     * @return value of the data-field under that identifier. if the simulation is null, then null will be returned
     */
    public Object getSimpleField(String key) {
        if (simulation == null) {
            return null;
        }
        return simulation.getData(key);
    }

    /**
     * Increments a simple field (e.g. a number) in the simulation. If the field does not exist under the key, it will be created.
     *
     * @param key identifier of the data-field
     */
    public void incrementSimpleField(String key) {
        if (simulation != null) {
            Object value = simulation.getData(key);
            if (value == null) {
                value = 0;
            }

            simulation.addData(key, (int) value + 1);
        }
    }

    /**
     * Decrements a simple field (e.g. a number) in the simulation. If the field does not exist under the key, it will be created.
     *
     * @param key identifier of the data-field
     */
    public void decrementSimpleField(String key) {
        if (simulation != null) {
            Object value = simulation.getData(key);
            if (value == null) {
                value = 0;
            }

            simulation.addData(key, (int) value - 1);
        }
    }

    /**
     * Adds a complex field (e.g. a vector) to the simulation. If field already exists under key, it will be overwritten.
     * Complex fields contain multiple values which can be summarized by instances of the Operation interface.
     *
     * @param key   identifier of the data-field
     * @param value first value to be added to the data-field
     */
    public void addComplexField(String key, Object value) {
        if (simulation != null) {
            Vector<Object> values = new Vector<>();
            values.add(value);
            simulation.addData(key, values);
        }
    }

    /**
     * Adds a value to a complex field (e.g. a vector) in the simulation. If field does not exist under key, it will be created.
     * Complex fields contain multiple values which can be summarized by instances of the Operation interface.
     *
     * @param key   identifier of the data-field
     * @param value value to be added to the data-field
     */
    public void updateComplexField(String key, Object value) {
        if (simulation != null) {
            if (simulation.getData(key) == null) {
                addComplexField(key, value);
            } else if (simulation.getData(key) instanceof Vector) {
                Vector<Object> values = (Vector<Object>) simulation.getData(key);
                values.add(value);
            }
        }
    }

    // ERROR: We do not explicitly store comparables, nor do we sort the vector. So Median will not work here.
    /**
     * Summarizes a complex field (e.g. a vector) in the simulation. It will save the result under the complex fields key plus the operation.
     * Complex fields contain multiple values which can be summarized by instances of the Operation interface.
     *
     * @param key       identifier of the data-field
     * @param operation operation to be used to summarize the values
     */
    public void summarizeComplexField(String key, Operation operation) {
        if (simulation != null && simulation.getData(key) != null && simulation.getData(key) instanceof Vector) {
            Vector<Object> values = (Vector<Object>) simulation.getData(key);
            addSimpleField(key + "-" + operation.toString(), operation.compute(values));
        }
    }

    /**
     * Saves the simulation to the datastore.
     */
    public void saveSimulation() {
        dataStore.addSimulation(simulation);
    }

    /**
     *
     * @return all data stored in the datastore
     */
    @Override
    public String toString() {
        return dataStore.toString();
    }
}
