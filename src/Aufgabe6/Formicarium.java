import java.text.Normalizer;
import java.util.Iterator;

public class Formicarium {
    private final static MyList<Formicarium> formicariums = new MyList<>();
    private static final MyList<Nest> nests = Nest.nests;

    private String name;
    private String antSpecies;
    private MyList<Nest> nestRoot;

    public Formicarium(String name, String antSpecies) {
        for (Object formicarium : formicariums) {
            if (formicarium instanceof Formicarium t) {
                if (t.name.equals(name)) {
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

    //ToDo: Add statistics

    public String averageVolume() {
        return Integer.toString(0);
    }

    public String averageHeatedVolume() {
        return "String here";
    }

    public String averageAirConditionedVolume() {
        return "String here";
    }

    public String averagePerformance() {
        return "String here";
    }

    public String averageTankVolume() {
        return "String here";
    }

    public String averageSandClayWeight() {
        return "String here";
    }

    public String averageAeriatedConcreteVolume() {
        return "String there";
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
            if (nest instanceof Nest n) {
                if (n.id() == id) {
                    return  n;
                }
            }
        }
        return null;
    }

    // </editor-fold>
}
