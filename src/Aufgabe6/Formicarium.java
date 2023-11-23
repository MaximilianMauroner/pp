import java.text.Normalizer;
import java.util.Iterator;

public class Formicarium {
    private final static MyList<Formicarium> formicariums = new MyList<>();
    private static MyList<Nest> nests = Nest.nests;

    private String name;
    private String antSpecies;
    private MyList<Nest> nestRoot;

    public Formicarium(String name, String antSpecies) {
        for (Object formicarium : formicariums) {
            if (formicarium instanceof Formicarium) {
                if (((Formicarium) formicarium).name.equals(name)) {
                    throw new IllegalArgumentException("Name already exists");
                }
            }

        }
        this.name = name;
        this.antSpecies = antSpecies;
        Formicarium.formicariums.add(this);
        this.nestRoot = new MyList<>();
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

    public void addNest(int id) {
        Nest nest = getNest(id, nests);
        this.nestRoot.add(nest);
    }

    public void removeNest(int id) {
        Nest nest = getNest(id, nestRoot);
        this.nestRoot.remove(nest);
    }


    //<editor-fold desc="Ändern der Informationen von Nestern wie oben beschrieben.">
    public void setNestFilling(int id, Filling filling) {
        Nest nest = getNest(id, nestRoot);
        if (nest != null)
            nest.setFilling(filling);
    }

    //</editor-fold>





    // <editor-fold desc="Statistics">

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

    // </editor-fold>

    // <editor-fold desc="Helper">

    public static MyList<Formicarium> getFormicariums() {
        return formicariums;
    }

    public String getName() {
        return name;
    }

    private Nest getNest(int id, MyList<Nest> list) {
        for (Object nest : list) {
            if (nest instanceof Nest) {
                if (((Nest) nest).id() == id) {
                    return (Nest) nest;
                }
            }
        }
        return null;
    }

    // </editor-fold>
}
