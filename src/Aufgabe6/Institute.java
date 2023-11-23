public class Institute {

    private static MyList<Formicarium> formicariums = Formicarium.getFormicariums();

    private MyList<Formicarium> formicariumRoot = new MyList<>();

    public void addFormicarium(String name) {
        Formicarium formicarium = getFormicarium(name, formicariums);
        formicariumRoot.add(formicarium);
    }

    public void removeFormicarium(String name) {
        Formicarium formicarium = getFormicarium(name, formicariumRoot);
        formicariumRoot.remove(formicarium);
    }

    public void print() {
        // ToDo: Add print of statistics
        System.out.println(formicariumRoot.toString());
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

    //</editor-fold>
}
