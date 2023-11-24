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

    @Override
    public String toString() {
        return "Formicarium{" +
                "name='" + name + '\'' +
                ", antSpecies='" + antSpecies + '\'' +
                ", nest=" + nestRoot +
                ", averageVolume=" + averageVolume() +
                ", averageHeatedVolume=" + averageHeatedVolume() +
                ", averageAirConditionedVolume=" + averageAirConditionedVolume() +
                ", averagePerformance=" + averagePerformance() +
                ", averageTankVolume=" + averageTankVolume() +
                ", averageSandClayWeight=[" + averageSandClayWeight() + "]" +
                ", averageTankVolume=[" + averageAeriatedConcreteVolume() + "]" +
                '}';
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
        double value = 0;
        int count = 0;
        for (Nest nest : nestRoot) {
            value += nest.depth * nest.height() * nest.width();
            count++;
        }
        return Double.toString(value / count);
    }

    public String averageHeatedVolume() {
        double value = 0;
        int count = 0;
        for (Nest nest : nestRoot) {
            if (nest instanceof HeatedNest) {
                value += nest.depth * nest.height() * nest.width();
                count++;
            }
        }
        return Double.toString(value / count);
    }

    public String averageAirConditionedVolume() {
        double value = 0;
        int count = 0;
        for (Nest nest : nestRoot) {
            if (nest instanceof AirConditionedNest) {
                value += nest.depth * nest.height() * nest.width();
                count++;
            }
        }
        return Double.toString(value / count);

    }

    public String averagePerformance() {
        double value = 0;
        int count = 0;
        for (Nest nest : nestRoot) {
            if (nest instanceof HeatedNest) {
                value += nest.getPower();
                count++;
            }
        }
        return Double.toString(value / count);
    }

    public String averageTankVolume() {
        double value = 0;
        int count = 0;
        for (Nest nest : nestRoot) {
            value += nest.getTankVolume();
            count++;
        }
        return Double.toString(value / count);

    }

    public String averageSandClayWeight() {
        double valueTotal = 0;
        int countTotal = 0;
        double valueAir = 0;
        int countAir = 0;
        double valueHeated = 0;
        int countHeated = 0;
        for (Nest nest : nestRoot) {
            if (nest instanceof HeatedNest) {
                valueHeated += nest.getFilling().weight();
                countHeated++;
            }
            if (nest instanceof AirConditionedNest) {
                valueAir += nest.getFilling().weight();
                countAir++;
            }
            valueTotal += nest.getFilling().weight();
            countTotal++;
        }
        return "[Average sand-clay weight: " + Double.toString(valueTotal / countTotal) + "kg, " +
                "Average heated sand-clay weight: " + Double.toString(valueHeated / countHeated) + "kg, " +
                "Average air conditioned sand-clay weight: " + Double.toString(valueAir / countAir) + "kg]";
    }

    public String averageAeriatedConcreteVolume() {
        double valueTotal = 0;
        int countTotal = 0;
        double valueAir = 0;
        int countAir = 0;
        double valueHeated = 0;
        int countHeated = 0;
        for (Nest nest : nestRoot) {
            if (nest instanceof HeatedNest) {
                valueHeated += nest.getAereatedConcreteWidth() * nest.getAereatedConcreteHeight() * nest.depth;
                countHeated++;
            }
            if (nest instanceof AirConditionedNest) {
                valueAir += nest.getAereatedConcreteWidth() * nest.getAereatedConcreteHeight() * nest.depth;
                countAir++;
            }
            valueTotal += nest.getAereatedConcreteWidth() * nest.getAereatedConcreteHeight() * nest.depth;
            countTotal++;
        }
        return "[Average sand-clay weight: " + Double.toString(valueTotal / countTotal) + "kg, " +
                "Average heated sand-clay weight: " + Double.toString(valueHeated / countHeated) + "kg, " +
                "Average air conditioned sand-clay weight: " + Double.toString(valueAir / countAir) + "kg]";

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
                    return n;
                }
            }
        }
        return null;
    }

    // </editor-fold>
}
