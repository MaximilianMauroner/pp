public class Test {
    public static void main(String[] args) {
        initializeNests();


        Formicarium formacarium_initialize = new Formicarium("", "");
        Formicarium formacarium1 = new Formicarium("Nest 1", "Ant Species 1");
        formacarium1.addNest(0);
        formacarium1.addNest(1);
        formacarium1.addNest(2);

        Formicarium formacarium2 = new Formicarium("Nest 2", "Ant Species 2");
        formacarium2.addNest(0);
        formacarium2.addNest(3);
        formacarium2.addNest(4);

        Formicarium formacarium3 = new Formicarium("Nest 3", "Ant Species 3");
        formacarium3.addNest(0);
        formacarium3.addNest(5);
        formacarium3.addNest(6);

        Formicarium formacarium4 = new Formicarium("Nest 4", "Ant Species 4");
        formacarium4.addNest(0);
        formacarium4.addNest(7);
        formacarium4.addNest(8);


        Institute institute1 = new Institute();
        institute1.addFormicarium(formacarium1.getName());
        institute1.addFormicarium(formacarium2.getName());

        Institute institute2 = new Institute();
        institute2.addFormicarium(formacarium3.getName());
        institute2.addFormicarium(formacarium4.getName());

        testIncludes(institute1.toString(), "Nest 1", "Nest 2");
        testIncludes(institute2.toString(), "Nest 3", "Nest 4");

        System.out.println(institute1);
        System.out.println(institute2);
    }

    private static void initializeNests() {
        Filling filling0 = new SandClayFilling(0);
        Filling filling1 = new SandClayFilling(1);
        Filling filling2 = new SandClayFilling(2);
        Filling filling3 = new SandClayFilling(3);
        Filling filling4 = new AeratedConcreteFilling(4, 4);
        Filling filling5 = new AeratedConcreteFilling(5, 5);
        Filling filling6 = new AeratedConcreteFilling(6, 6);
        Filling filling7 = new AeratedConcreteFilling(7, 7);
        Filling filling8 = new AeratedConcreteFilling(8, 8);
        Filling filling9 = new AeratedConcreteFilling(9, 9);
        AirConditionedNest nest0 = new AirConditionedNest(0, 0, 0, 0);
        AirConditionedNest nest1 = new AirConditionedNest(1, 1, 1, 1);
        AirConditionedNest nest2 = new AirConditionedNest(2, 2, 2, 2);
        AirConditionedNest nest3 = new AirConditionedNest(3, 3, 3, 3);
        AirConditionedNest nest4 = new AirConditionedNest(4, 4, 4, 4);
        AirConditionedNest nest5 = new AirConditionedNest(5, 5, 5, 5);
        AirConditionedNest nest6 = new AirConditionedNest(6, 6, 6, 6);
        AirConditionedNest nest7 = new AirConditionedNest(7, 7, 7, 7);
        AirConditionedNest nest8 = new AirConditionedNest(8, 8, 8, 8);
        AirConditionedNest nest9 = new AirConditionedNest(9, 9, 9, 9);
        AirConditionedNest nest10 = new AirConditionedNest(10, 10, 10, 10);
        AirConditionedNest nest11 = new AirConditionedNest(11, 11, 11, 11);
        AirConditionedNest nest12 = new AirConditionedNest(12, 12, 12, 12);
        AirConditionedNest nest13 = new AirConditionedNest(13, 13, 13, 13);
        AirConditionedNest nest14 = new AirConditionedNest(14, 14, 14, 14);
        nest0.setFilling(filling0);
        nest1.setFilling(filling1);
        nest2.setFilling(filling2);
        nest3.setFilling(filling3);
        nest4.setFilling(filling4);
        nest5.setFilling(filling5);
        nest6.setFilling(filling6);
        nest7.setFilling(filling7);
        nest8.setFilling(filling8);
        nest9.setFilling(filling9);
        Nest.nests.add(nest0);
        Nest.nests.add(nest1);
        Nest.nests.add(nest2);
        Nest.nests.add(nest3);
        Nest.nests.add(nest4);
        Nest.nests.add(nest5);
        Nest.nests.add(nest6);
        Nest.nests.add(nest7);
        Nest.nests.add(nest8);
        Nest.nests.add(nest9);
//        HeatedNest nest10 = new HeatedNest(10, 10, 10, 10);
//        HeatedNest nest20 = new HeatedNest(20, 20, 20, 20);
//        HeatedNest nest30 = new HeatedNest(30, 30, 30, 30);
//        HeatedNest nest40 = new HeatedNest(40, 40, 40, 40);
//        HeatedNest nest50 = new HeatedNest(50, 50, 50, 50);
//        HeatedNest nest60 = new HeatedNest(60, 60, 60, 60);
//        Nest.nests.add(nest10);
//        Nest.nests.add(nest20);
//        Nest.nests.add(nest30);
//        Nest.nests.add(nest40);
//        Nest.nests.add(nest50);
//        Nest.nests.add(nest60);
    }


    private static void testTrue(boolean given, boolean expected) {
        if (given == expected) {
            System.out.println("Successful test");
        } else {
            System.out.println("Test NOT successful! Expected value: " + expected + " / Given value: " + given);
        }
    }

    private static void testIncludes(String given, String... expected) {
        for (String e : expected) {
            if (!given.contains(e)) {
                System.out.println("Test NOT successful! Expected value: ");
                throw new RuntimeException("Test NOT successful! Expected value: " + e);
            } else {
                System.out.println("Successful test");
            }
        }
    }

    public static void testIdentity(Object given, Object expected) {
        if (given == expected) {
            System.out.println("Successful test");
        } else {
            System.out.println("Test NOT successful! Expected value: " + expected + " / Given value: " + given);
        }
    }

    public static void testEquals(Object given, Object expected) {
        if (given.equals(expected)) {
            System.out.println("Successful test");
        } else {
            System.out.println("Test NOT successful! Expected value: " + expected.toString() + " / Given " + "value: " + given.toString());
            throw new RuntimeException("Test NOT successful! Expected value: " + expected + " / Given value: " + given);
        }
    }

    public static void testEquals(boolean given, boolean expected) {
        if (given == expected) {
            System.out.println("Successful test");
        } else {
            System.out.println("Test NOT successful! Expected value: " + expected + " / Given " + "value: " + given);
        }
    }

    public static void testClass(Object given, Object expected) {
        if (given.getClass() == expected.getClass()) {
            System.out.println("Successful test");
        } else {
            System.out.println("Test NOT successful! Expected value: " + expected.getClass().getSimpleName() + " / Given value: " + given.getClass().getSimpleName());
        }
    }
}
