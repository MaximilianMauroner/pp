import Annotations.*;

@Invariant(invariant = "name != null && antSpecies != null")
public class Formicarium {
    private final static MyList allFormicariums = new MyList();
    private static final MyList allNests = Nest.allNests;

    private String name;
    private String antSpecies;
    private MyList formicariumNests;


    @PreCondition(condition = "name != null && antSpecies != null")
    @PostCondition(condition = "this.name.equals(name) && this.antSpecies.equals(antSpecies)")
    public Formicarium(String name, String antSpecies) {
        for (Object formicarium : allFormicariums) {
            if (formicarium instanceof Formicarium t) {
                if (t.name.equals(name)) {
                    throw new IllegalArgumentException("Name already exists");
                }
            }
        }
        this.name = name;
        this.antSpecies = antSpecies;
        Formicarium.allFormicariums.add(this);
        this.formicariumNests = new MyList();
    }

    @PreCondition(condition = "antSpecies != null")
    @PostCondition(condition = "this.antSpecies.equals(antSpecies)")
    public void setAntSpecies(String antSpecies) {
        this.antSpecies = antSpecies;
    }

    @PreCondition(condition = "true")
    @PostCondition(condition = "result.equals(this.antSpecies)")
    public String getAntSpecies() {
        return antSpecies;
    }

    @PreCondition(condition = "true")
    @PostCondition(condition = "this.antSpecies == null")
    public void removeAntSpecies() {
        antSpecies = null;
    }

    @PreCondition(condition = "id >= 0")
    @PostCondition(condition = "this.formicariumNests.contains(getNest(id, allNests))")
    public void addNest(int id) {
        Nest nest = getNest(id, allNests);
        this.formicariumNests.add(nest);
    }

    @PreCondition(condition = "id >= 0, id exists")
    @PostCondition(condition = "!this.formicariumNests.contains(getNest(id, formicariumNests))")
    public void removeNest(int id) {
        Nest nest = getNest(id, formicariumNests);
        this.formicariumNests.remove(nest);
    }

    @Override
    @PostCondition(condition = "result != null")
    public String toString() {
        return "Formicarium{" + "name='" + name + '\'' + ", antSpecies='" + antSpecies + '\'' + ", nest=" + formicariumNests;
    }

    @PostCondition(condition = "returns information about the fornicarium")
    public String print() {
        return "Formicarium{" + "name='" + name + '\'' + ", antSpecies='" + antSpecies + '\'' + ", nest=" + formicariumNests + ", averageVolume=" + averageVolume() + ", averageHeatedVolume=" + averageHeatedVolume() + ", averageAirConditionedVolume=" + averageAirConditionedVolume() + ", averagePerformance=" + averagePerformance() + ", averageTankVolume=" + averageTankVolume() + ", averageSandClayWeight=[" + averageSandClayWeight() + "]" + ", averageTankVolume=[" + averageAeriatedConcreteVolume() + "]" + '}';
    }


    //<editor-fold desc="Ändern der Informationen von Nestern wie oben beschrieben.">
    @PreCondition(condition = "id >= 0, id exists, filling != null")
    @PostCondition(condition = "the filling of the nest(id) is set to filling")
    public void setNestFilling(int id, Filling filling) {
        Nest nest = getNest(id, formicariumNests);
        if (nest != null) nest.setFilling(filling);
    }

    //</editor-fold>


    // <editor-fold desc="Statistics">

    //ToDo: Add statistics

    @PostCondition(condition = "returns the total count of nests")
    public String averageVolume() {
        double value = 0;
        int count = 0;
        for (Object o : formicariumNests) {
            Nest nest = (Nest) o;
            value += nest.depth * nest.height() * nest.width();
            count++;
        }
        return Double.toString(count == 0 ? 1 : count);
    }

    @PostCondition(condition = "returns the total count of heated nests")

    public String averageHeatedVolume() {
        double value = 0;
        int count = 0;
        for (Object o : formicariumNests) {
            Nest nest = (Nest) o;
            if (nest instanceof HeatedNest) {
                value += nest.depth * nest.height() * nest.width();
                count++;
            }
        }
        return Double.toString(count == 0 ? 1 : count);
    }

    @PostCondition(condition = "returns the total count of air conditioned nests")
    public String averageAirConditionedVolume() {
        double value = 0;
        int count = 0;
        for (Object o : formicariumNests) {
            Nest nest = (Nest) o;
            if (nest instanceof AirConditionedNest) {
                value += nest.depth * nest.height() * nest.width();
                count++;
            }
        }
        return Double.toString(count == 0 ? 1 : count);

    }

    @PostCondition(condition = "returns the total count of heated nests")
    public String averagePerformance() {
        double value = 0;
        int count = 0;
        for (Object o : formicariumNests) {
            Nest nest = (Nest) o;
            if (nest instanceof HeatedNest) {
                value += nest.getPower();
                count++;
            }
        }
        return Double.toString(count == 0 ? 1 : count);
    }

    @PostCondition(condition = "returns the total count of all nests")
    public String averageTankVolume() {
        double value = 0;
        int count = 0;
        for (Object o : formicariumNests) {
            Nest nest = (Nest) o;
            value += nest.getTankVolume();
            count++;
        }
        return Double.toString(count == 0 ? 1 : count);

    }

    @PostCondition(condition = "returns the average sand-clay nest weight")
    public String averageSandClayWeight() {
        double valueTotal = 0;
        int countTotal = 0;
        double valueAir = 0;
        int countAir = 0;
        double valueHeated = 0;
        int countHeated = 0;
        for (Object o : formicariumNests) {
            Nest nest = (Nest) o;
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
        return "[Average sand-clay weight: " + Double.toString(valueTotal / countTotal) + "kg, " + "Average heated sand-clay weight: " + Double.toString(valueHeated / countHeated) + "kg, " + "Average air conditioned sand-clay weight: " + Double.toString(valueAir / countAir) + "kg]";
    }

    @PostCondition(condition = "returns the average airated concrete volume")
    public String averageAeriatedConcreteVolume() {
        double valueTotal = 0;
        int countTotal = 0;
        double valueAir = 0;
        int countAir = 0;
        double valueHeated = 0;
        int countHeated = 0;
        for (Object o : formicariumNests) {
            Nest nest = (Nest) o;
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
        return "[Average sand-clay weight: " + Double.toString(valueTotal / countTotal) + "kg, " + "Average heated sand-clay weight: " + Double.toString(valueHeated / countHeated) + "kg, " + "Average air conditioned sand-clay weight: " + Double.toString(valueAir / countAir) + "kg]";

    }

    // </editor-fold>

    // <editor-fold desc="Helper">

    @PostCondition(condition = "returns all formicariums")
    public static MyList getFormicariums() {
        return allFormicariums;
    }


    @PostCondition(condition = "returns the name of the formicarium")
    public String getName() {
        return name;
    }

    @PostCondition(condition = "returns the id of the nest if it exists in list, else null")
    private Nest getNest(int id, MyList list) {
        for (Object nest : list) {
            if (nest instanceof Nest n) {
                if (n.id() == id) {
                    return n;
                }
            }
        }
        return null;
    }

    @PostCondition(condition = "returns the nest with the id if it exists in the formicarium, else null")
    public Nest getNest(int id) {
        return getNest(id, formicariumNests);
    }

    // </editor-fold>
}
