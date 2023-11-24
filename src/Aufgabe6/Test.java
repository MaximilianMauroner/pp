public class Test {
    public static void main(String[] args) {
        intitializeNests();


        Formicarium formacarium_initialize = new Formicarium("", "");
        Formicarium formacarium1 = new Formicarium("Nest 1", "Ant Species 1");
        formacarium1.addNest(1);
//        formacarium1.addNest(2);
//        formacarium1.addNest(3);

        Institute institute1 = new Institute();
//
        institute1.addFormicarium(formacarium1.getName());

        System.out.println(formacarium1);
    }

    private static void intitializeNests() {
        Filling filling0 = new SandClayFilling(0);
        Filling filling1 = new SandClayFilling(1);
        Filling filling2 = new SandClayFilling(2);
        Filling filling3 = new SandClayFilling(3);
        Filling filling4 = new AeratedConcreteFilling(4, 4);
        Filling filling5 = new AeratedConcreteFilling(5, 5);
        Filling filling6 = new AeratedConcreteFilling(6, 6);
        AirConditionedNest nest0 = new AirConditionedNest(0, 0, 0, 0);
        AirConditionedNest nest1 = new AirConditionedNest(1, 1, 1, 1);
        AirConditionedNest nest2 = new AirConditionedNest(2, 2, 2, 2);
        AirConditionedNest nest3 = new AirConditionedNest(3, 3, 3, 3);
        AirConditionedNest nest4 = new AirConditionedNest(4, 4, 4, 4);
        AirConditionedNest nest5 = new AirConditionedNest(5, 5, 5, 5);
        AirConditionedNest nest6 = new AirConditionedNest(6, 6, 6, 6);
        nest0.setFilling(filling0);
        nest1.setFilling(filling1);
        nest2.setFilling(filling2);
        nest3.setFilling(filling3);
        nest4.setFilling(filling4);
        nest5.setFilling(filling5);
        nest6.setFilling(filling6);
        Nest.nests.add(nest0);
        Nest.nests.add(nest1);
        Nest.nests.add(nest2);
        Nest.nests.add(nest3);
        Nest.nests.add(nest4);
        Nest.nests.add(nest5);
        Nest.nests.add(nest6);
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
