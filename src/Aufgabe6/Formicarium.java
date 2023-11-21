public class Formicarium {
    private String name;
    private String antSpecies;
    private List<Nest> nestRoot;

    public Formicarium(String name, String antSpecies) {
        this.name = name;
        this.antSpecies = antSpecies;
    }

    public void setAntSpecies(String antSpecies) {
        this.antSpecies = antSpecies;
    }

    public String getAntSpecies() {
        return antSpecies;
    }

    public void removeAntSpecies() {
        antSpecies = null;
    }

    public void addNest(Nest nest) {
    }

    public void removeNest(Nest nest) {
    }


    // Ändern der Informationen von Nestern wie oben beschrieben.


    // statistics

    public double averageVolume() {
        return 0;
    }

    public double averageHeatedVolume() {
        return 0;
    }

    public double averageAirConditionedVolume() {
        return 0;
    }

    public double averagePerformance() {
        return 0;
    }

    public double averageTankVolume() {
        return 0;
    }

    public double averageSandClayWeight() {
        return 0;
    }

    public double averageAeriatedConcreteVolume() {
        return 0;
    }
}
