public class Institute {

    private static final MyList<Formicarium> formicariums = Formicarium.getFormicariums();

    private final MyList<Formicarium> formicariumRoot = new MyList<>();

    public void addFormicarium(String name) {
        Formicarium formicarium = getFormicarium(name, formicariums);
        formicariumRoot.add(formicarium);
    }

    public void removeFormicarium(String name) {
        Formicarium formicarium = getFormicarium(name, formicariumRoot);
        formicariumRoot.remove(formicarium);
    }

    //Anzeigen aller Formicarien eines Instituts mit allen Informationen
    //auf dem Bildschirm
    public void print() {
        System.out.println("Institute{formicariums=" + formicariumRoot.print() + '}');
    }


    //<editor-fold desc="Helper">

    private static Formicarium getFormicarium(String name, MyList<Formicarium> list) {
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
        return getFormicarium(name, formicariumRoot);
    }

    @Override
    public String toString() {
        return "Institute{" +
                "formicariums=" + formicariumRoot +
                '}';
    }

    //</editor-fold>
}
