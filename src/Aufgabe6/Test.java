public class Test {
    public static void main(String[] args) {
        initializeNests();
        initializeFormacariums();


        Institute institute1 = new Institute();
        institute1.addFormicarium("Nest 1");
        institute1.addFormicarium("Nest 2");

        Institute institute2 = new Institute();
        institute2.addFormicarium("Nest 3");
        institute2.addFormicarium("Nest 4");

        Institute institute3 = new Institute();
        institute1.addFormicarium("Nest 5");
        institute1.addFormicarium("Nest 6");

        Institute institute4 = new Institute();
        institute2.addFormicarium("Nest 7");
        institute2.addFormicarium("Nest 8");

        testIncludes(institute1.toString(), "Nest 1", "Nest 2");
        testIncludes(institute2.toString(), "Nest 3", "Nest 4");
        testIncludes(institute3.toString(), "Nest 5", "Nest 6");
        testIncludes(institute4.toString(), "Nest 7", "Nest 8");


        // Test institutes:
        institute1.removeFormicarium("Nest 1");
        testIncludes(institute1.toString(), "Nest 2");
        testIncludesNot(institute1.toString(), "Nest 1");

        institute1.addFormicarium("Nest 9");
        institute1.addFormicarium("Nest 10");
        testIncludes(institute1.toString(), "Nest 2", "Nest 9", "Nest 10");

        institute1.removeFormicarium("Nest 2");
        institute1.removeFormicarium("Nest 9");
        testIncludes(institute1.toString(), "Nest 10");
        testIncludesNot(institute1.toString(), "Nest 2", "Nest 9");


        // Test formicariums:
        institute1.addFormicarium("Nest 1");
        testIncludes(institute1.toString(), "Nest 1");
        testIncludes(institute1.toString(), "id=1");

        Institute.getFormicarium("Nest 1").addNest(20);
        Institute.getFormicarium("Nest 1").addNest(21);
        testIncludes(institute1.toString(), "id=20", "id=21");

        Institute.getFormicarium("Nest 1").removeNest(20);
        Institute.getFormicarium("Nest 1").removeNest(21);
        testIncludesNot(institute1.toString(), "id=20", "id=21");


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
        Filling filling10 = new SandClayFilling(10);
        Filling filling11 = new SandClayFilling(11);
        Filling filling12 = new SandClayFilling(12);
        Filling filling13 = new SandClayFilling(13);
        Filling filling14 = new AeratedConcreteFilling(14, 14);
        Filling filling15 = new AeratedConcreteFilling(15, 15);
        Filling filling16 = new AeratedConcreteFilling(16, 16);
        Filling filling17 = new AeratedConcreteFilling(17, 17);
        Filling filling18 = new AeratedConcreteFilling(18, 18);
        Filling filling19 = new AeratedConcreteFilling(19, 19);
        Filling filling20 = new SandClayFilling(20);
        Filling filling21 = new SandClayFilling(21);
        Filling filling22 = new SandClayFilling(22);
        Filling filling23 = new SandClayFilling(23);
        Filling filling24 = new AeratedConcreteFilling(24, 24);
        Filling filling25 = new AeratedConcreteFilling(25, 25);
        Filling filling26 = new AeratedConcreteFilling(26, 26);
        Filling filling27 = new AeratedConcreteFilling(27, 27);
        Filling filling28 = new AeratedConcreteFilling(28, 28);
        Filling filling29 = new AeratedConcreteFilling(29, 29);

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
        AirConditionedNest nest15 = new AirConditionedNest(15, 15, 15, 15);
        AirConditionedNest nest16 = new AirConditionedNest(16, 16, 16, 16);
        AirConditionedNest nest17 = new AirConditionedNest(17, 17, 17, 17);
        AirConditionedNest nest18 = new AirConditionedNest(18, 18, 18, 18);
        AirConditionedNest nest19 = new AirConditionedNest(19, 19, 19, 19);
        AirConditionedNest nest20 = new AirConditionedNest(20, 20, 20, 20);
        AirConditionedNest nest21 = new AirConditionedNest(21, 21, 21, 21);
        AirConditionedNest nest22 = new AirConditionedNest(22, 22, 22, 22);
        AirConditionedNest nest23 = new AirConditionedNest(23, 23, 23, 23);
        AirConditionedNest nest24 = new AirConditionedNest(24, 24, 24, 24);
        AirConditionedNest nest25 = new AirConditionedNest(25, 25, 25, 25);
        AirConditionedNest nest26 = new AirConditionedNest(26, 26, 26, 26);
        AirConditionedNest nest27 = new AirConditionedNest(27, 27, 27, 27);
        AirConditionedNest nest28 = new AirConditionedNest(28, 28, 28, 28);
        AirConditionedNest nest29 = new AirConditionedNest(29, 29, 29, 29);

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
        nest10.setFilling(filling10);
        nest11.setFilling(filling11);
        nest12.setFilling(filling12);
        nest13.setFilling(filling13);
        nest14.setFilling(filling14);
        nest15.setFilling(filling15);
        nest16.setFilling(filling16);
        nest17.setFilling(filling17);
        nest18.setFilling(filling18);
        nest19.setFilling(filling19);
        nest20.setFilling(filling20);
        nest21.setFilling(filling21);
        nest22.setFilling(filling22);
        nest23.setFilling(filling23);
        nest24.setFilling(filling24);
        nest25.setFilling(filling25);
        nest26.setFilling(filling26);
        nest27.setFilling(filling27);
        nest28.setFilling(filling28);
        nest29.setFilling(filling29);
        addtoNest(nest0, nest1, nest2, nest3, nest4, nest5, nest6, nest7, nest8, nest9, nest10, nest11, nest12, nest13, nest14, nest15, nest16, nest17, nest18, nest19, nest20, nest21, nest22, nest23, nest24, nest25, nest26, nest27, nest28, nest29);
    }

    private static void initializeFormacariums() {
        Formicarium formacarium1 = new Formicarium("Nest 1", "Ant Species 1");
        formacarium1.addNest(1);
        formacarium1.addNest(2);

        Formicarium formacarium2 = new Formicarium("Nest 2", "Ant Species 2");
        formacarium2.addNest(3);
        formacarium2.addNest(4);

        Formicarium formacarium3 = new Formicarium("Nest 3", "Ant Species 3");
        formacarium3.addNest(5);
        formacarium3.addNest(6);

        Formicarium formacarium4 = new Formicarium("Nest 4", "Ant Species 4");
        formacarium4.addNest(7);
        formacarium4.addNest(8);

        Formicarium formacarium5 = new Formicarium("Nest 5", "Ant Species 5");
        formacarium5.addNest(9);
        formacarium5.addNest(10);

        Formicarium formacarium6 = new Formicarium("Nest 6", "Ant Species 6");
        formacarium6.addNest(11);
        formacarium6.addNest(12);

        Formicarium formacarium7 = new Formicarium("Nest 7", "Ant Species 7");
        formacarium7.addNest(13);
        formacarium7.addNest(14);

        Formicarium formacarium8 = new Formicarium("Nest 8", "Ant Species 8");
        formacarium8.addNest(15);
        formacarium8.addNest(16);

        Formicarium formacarium9 = new Formicarium("Nest 9", "Ant Species 9");
        formacarium9.addNest(17);
        formacarium9.addNest(18);

        Formicarium formacarium10 = new Formicarium("Nest 10", "Ant Species 10");
        formacarium10.addNest(19);
        formacarium10.addNest(20);
    }

    private static void addtoNest(Nest... nests) {
        for (Nest nest : nests) {
            Nest.nests.add(nest);
        }
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

    private static void testIncludesNot(String given, String... expected) {
        for (String e : expected) {
            if (given.contains(e)) {
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
