import Annotations.Author;
import Annotations.PostCondition;
import Annotations.PreCondition;

@Author(name = "Christopher Scherling")
public class Institute {

    private static final MyList allFormicariums = Formicarium.getFormicariums();

    private final MyList instituteFormicariums = new MyList();

    @PreCondition(condition = "name != null")
    @PostCondition(condition = "adds the formicarium with the given name to the institute")
    public void addFormicarium(String name) {
        Formicarium formicarium = getFormicarium(name, allFormicariums);
        instituteFormicariums.add(formicarium);
    }

    @PreCondition(condition = "name != null")
    @PostCondition(condition = "returns the formicarium with the given name, if it exists, otherwise null")
    public void removeFormicarium(String name) {
        Formicarium formicarium = getFormicarium(name, instituteFormicariums);
        instituteFormicariums.remove(formicarium);
    }

    @PostCondition(condition = "prints all formicariums of the institute")
    @Author(name = "Christopher Scherling")
    public void print() {
        System.out.println("Institute{formicariums=" + instituteFormicariums.print() + '}');
    }


    //<editor-fold desc="Helper">

    @PreCondition(condition = "name != null, list != null")
    @PostCondition(condition = "returns the formicarium with the given name in the list, if it exists, otherwise null")
    @Author(name = "Lukas Leskovar")
    private static Formicarium getFormicarium(String name, MyList list) {
        for (Object formicarium : list) {
            if (formicarium instanceof Formicarium) {
                if (((Formicarium) formicarium).getName().equals(name)) {
                    return (Formicarium) formicarium;
                }
            }
        }
        return null;
    }

    @PreCondition(condition = "name != null")
    @PostCondition(condition = "returns the formicarium with the given name, if it exists, otherwise null")
    @Author(name = "Christopher Scherling")
    public Formicarium getFormicarium(String name) {
        return getFormicarium(name, instituteFormicariums);
    }

    @PostCondition(condition = "returns a string representation of the institute")
    @Author(name = "Christopher Scherling")
    @Override
    public String toString() {
        return "Institute{" +
                "formicariums=" + instituteFormicariums +
                '}';
    }

    //</editor-fold>
}
