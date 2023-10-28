package datastore;

import java.util.Vector;


/**
 * Manages the data of the simulation
 * STYLE: Objektorientiertes Programmierung (es wird hier das Singleton Pattern verwendet was klar auf Objektorientierung hinweist)
 */
@SuppressWarnings("unchecked")
public class DataManager {
    private static DataManager instance;
    private DataStore dataStore;
    private Simulation simulation;

    private DataManager() {
        dataStore = new DataStore();
    }

    public static DataManager getInstance(Simulation simulation) {
        if (instance == null) {
            instance = new DataManager();
        }

        instance.simulation = simulation;
        return instance;
    }

    public static DataManager getInstance() {
        if (instance == null) {
            instance = new DataManager();
        }

        return instance;
    }

    public boolean containsField(String key) {
        return simulation != null && simulation.getData(key) != null;
    }

    /**
     * Adds a simple field to the simulation. If field already exists under key, it will be overwritten.
     * @param key identifier of the datafield
     * @param value value of the datafield
     */
    public void addSimpleField(String key, Object value) {
        if (simulation != null)
            simulation.addData(key, value);
    }

    public Object getSimpleField(String key) {
        if (simulation == null) {
            return null;
        }
        return simulation.getData(key);
    }

    public void incrementSimpleField(String key) {
        if (simulation != null) {
            Object value = simulation.getData(key);
            if (value == null) {
                value = 0;
            }

            simulation.addData(key, (int) value + 1);
        }
    }

    public void decrementSimpleField(String key) {
        if (simulation != null) {
            Object value = simulation.getData(key);
            if (value == null) {
                value = 0;
            }

            simulation.addData(key, (int) value - 1);
        }
    }

    public void addComplexField(String key, Object value) {
        if (simulation != null) {
            Vector<Object> values = new Vector<>();
            values.add(value);
            simulation.addData(key, values);
        }
    }

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

    public void summarizeComplexField(String key, Operation operation) {
        if (simulation != null && simulation.getData(key) != null && simulation.getData(key) instanceof Vector) {
            Vector<Object> values = (Vector<Object>) simulation.getData(key);
            addSimpleField(key + "-" + operation.toString(), operation.compute(values));
        }
    }

    public void saveSimulation() {
        dataStore.addSimulation(simulation);
    }

    @Override
    public String toString() {
        return dataStore.toString();
    }
}
