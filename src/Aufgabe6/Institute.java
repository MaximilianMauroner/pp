public class Institute {

    private static final MyList allFormicariums = Formicarium.getFormicariums();

    private final MyList instituteFormicariums = new MyList();

    public void addFormicarium(String name) {
        Formicarium formicarium = getFormicarium(name, allFormicariums);
        instituteFormicariums.add(formicarium);
    }

    public void removeFormicarium(String name) {
        Formicarium formicarium = getFormicarium(name, instituteFormicariums);
        instituteFormicariums.remove(formicarium);
    }

    //Anzeigen aller Formicarien eines Instituts mit allen Informationen
    //auf dem Bildschirm
    public void print() {
        System.out.println("Institute{formicariums=" + instituteFormicariums.print() + '}');
    }


    //<editor-fold desc="Helper">

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

    public Formicarium getFormicarium(String name) {
        return getFormicarium(name, instituteFormicariums);
    }

    @Override
    public String toString() {
        return "Institute{" +
                "formicariums=" + instituteFormicariums +
                '}';
    }

    //</editor-fold>
}
