package datastore;

import java.util.HashMap;

@SuppressWarnings("unchecked")
public class Simulation {
    private String simulationId;
    private String simulationName;

    private HashMap<String, Object> data;

    public Simulation(String simulationId, String simulationName) {
        this.simulationId = simulationId;
        this.simulationName = simulationName;
        this.data = new HashMap<>();
    }

    public String getSimulationId() {
        return simulationId;
    }

    public String getSimulationName() {
        return simulationName;
    }

    public void addData(String key, Object value) {
        data.put(key, value);
    }

    public Object getData(String key) {
        return data.get(key);
    }

    public HashMap<String, Object> getData() {
        return (HashMap<String, Object>) this.data.clone();
    }

    @Override
    public String toString() {
        return "Simulation{" +
                "simulationId=" + simulationId +
                ", simulationName='" + simulationName + '\'' +
                ", data=" + data.toString() +
                '}';
    }
}
