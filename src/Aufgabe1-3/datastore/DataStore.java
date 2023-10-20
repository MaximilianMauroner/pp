package datastore;

import java.util.ArrayList;
import java.util.List;

public class DataStore {
    private List<Simulation> simulationList = new ArrayList<>();

    public void addSimulation(Simulation simulation) {
        simulationList.add(simulation);
    }

    public Simulation getSimulation(String simulationId) {
        for (Simulation simulation : simulationList) {
            if (simulation.getSimulationId().equals(simulationId)) {
                return simulation;
            }
        }
        return null;
    }

    public List<Simulation> getSimulationList() {
        return simulationList;
    }

    public void removeSimulation(String simulationId) {
        simulationList.removeIf(simulation -> simulation.getSimulationId().equals(simulationId));
    }

    public void removeSimulation(Simulation simulation) {
        simulationList.remove(simulation);
    }

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
