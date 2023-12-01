import Annotations.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Author(name = "Christopher Scherling")
public class Test {
    @Author(name = "Christopher Scherling")
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
        institute3.addFormicarium("Nest 5");
        institute3.addFormicarium("Nest 6");

        Institute institute4 = new Institute();
        institute4.addFormicarium("Nest 7");
        institute4.addFormicarium("Nest 8");

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

        institute1.getFormicarium("Nest 1").addNest(20);
        institute1.getFormicarium("Nest 1").addNest(21);
        testIncludes(institute1.toString(), "id=20", "id=21");

        institute1.getFormicarium("Nest 1").removeNest(20);
        institute1.getFormicarium("Nest 1").removeNest(21);
        testIncludes(institute1.toString(), "id=20");
        testIncludesNot(institute1.toString(), "id=21");


        //Test print():
        String s = "";
        PrintStream ORIGINAL_OUT = System.out;
        ByteArrayOutputStream NEW_OUT = new java.io.ByteArrayOutputStream();
        PrintStream PRINT_STREAM = new PrintStream(NEW_OUT);
        System.setOut(PRINT_STREAM);

        institute1.print();

        System.setOut(ORIGINAL_OUT);
        testIncludes(NEW_OUT.toString(), "name='Nest 10', antSpecies='Ant Species 10', nest=[AirConditionedNest{filling=AeratedConcreteFilling{width=19.0, height=19.0}, id=19, width=19.0, height=19.0, depth=2.0, tankVolume=19.0}, AirConditionedNest{filling=SandClayFilling{weight=20.0}, id=20, width=20.0, height=20.0, depth=2.0, tankVo");


        //Test toString():
        testEquals(institute1.toString(), "Institute{formicariums=[Formicarium{name='Nest 10', antSpecies='Ant Species 10', nest=[AirConditionedNest{filling=AeratedConcreteFilling{width=19.0, height=19.0}, id=19, width=19.0, height=19.0, depth=2.0, tankVolume=19.0}, AirConditionedNest{filling=SandClayFilling{weight=20.0}, id=20, width=20.0, height=20.0, depth=2.0, tankVolume=20.0}], Formicarium{name='Nest 1', antSpecies='Ant Species 1', nest=[AirConditionedNest{filling=SandClayFilling{weight=1.0}, id=1, width=1.0, height=1.0, depth=2.0, tankVolume=1.0}, AirConditionedNest{filling=SandClayFilling{weight=2.0}, id=2, width=2.0, height=2.0, depth=2.0, tankVolume=2.0}]]}");


        //Test Formicarium Species:
        testEquals(institute1.getFormicarium("Nest 1").getAntSpecies(), "Ant Species 1");
        institute1.getFormicarium("Nest 1").removeAntSpecies();
        testEquals(institute1.getFormicarium("Nest 1").getAntSpecies() == null, true);
        institute1.getFormicarium("Nest 1").setAntSpecies("Ant Species 1");
        testEquals(institute1.getFormicarium("Nest 1").getAntSpecies(), "Ant Species 1");

        //Test setNestFilling():
        testEquals(institute1.getFormicarium("Nest 1").getNest(1).getFilling().toString(), "SandClayFilling{weight=1.0}");
        institute1.getFormicarium("Nest 1").setNestFilling(1, new AeratedConcreteFilling(1, 1));
        testEquals(institute1.getFormicarium("Nest 1").getNest(1).getFilling().toString(), "AeratedConcreteFilling{width=1.0, height=1.0}");

        prettyPrintAnnotations();
    }

    @Author(name = "Christopher Scherling")
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
    }

    @Author(name = "Christopher Scherling")
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

    @Author(name = "Christopher Scherling")
    private static void addtoNest(Nest... nests) {
        for (Nest nest : nests) {
            Nest.allNests.add(nest);
        }
    }

    @Author(name = "Christopher Scherling")
    private static void testTrue(boolean given, boolean expected) {
        if (given == expected) {
            System.out.println("Successful test");
        } else {
            System.out.println("Test NOT successful! Expected value: " + expected + " / Given value: " + given);
        }
    }

    @Author(name = "Christopher Scherling")
    private static void testIncludes(String given, String... expected) {
        for (String e : expected) {
            if (!given.contains(e)) {
                System.out.println("Test NOT successful! Expected value: " + e);
            } else {
                System.out.println("Successful test");
            }
        }
    }

    @Author(name = "Christopher Scherling")
    private static void testIncludesNot(String given, String... expected) {
        for (String e : expected) {
            if (given.contains(e)) {
                System.out.println("Test NOT successful! Expected value: " + e);
            } else {
                System.out.println("Successful test");
            }
        }
    }

    @Author(name = "Christopher Scherling")
    public static void testIdentity(Object given, Object expected) {
        if (given == expected) {
            System.out.println("Successful test");
        } else {
            System.out.println("Test NOT successful! Expected value: " + expected + " / Given value: " + given);
        }
    }

    @Author(name = "Christopher Scherling")
    public static void testEquals(Object given, Object expected) {
        if (given.equals(expected)) {
            System.out.println("Successful test");
        } else {
            System.out.println("Test NOT successful! Expected value: " + expected.toString() + " / Given " + "value: " + given.toString());
        }
    }

    @Author(name = "Christopher Scherling")
    public static void testEquals(boolean given, boolean expected) {
        if (given == expected) {
            System.out.println("Successful test");
        } else {
            System.out.println("Test NOT successful! Expected value: " + expected + " / Given " + "value: " + given);
        }
    }

    @Author(name = "Christopher Scherling")
    public static void testClass(Object given, Object expected) {
        if (given.getClass() == expected.getClass()) {
            System.out.println("Successful test");
        } else {
            System.out.println("Test NOT successful! Expected value: " + expected.getClass().getSimpleName() + " / Given value: " + given.getClass().getSimpleName());
        }
    }

    @Author(name = "Maximilian Mauroner")
    public static void prettyPrintAnnotations() {
        handleAnnotations(Test.class);
        handleAnnotations(AeratedConcreteFilling.class);
        handleAnnotations(AirConditionedNest.class);
        handleAnnotations(Filling.class);
        handleAnnotations(Formicarium.class);
        handleAnnotations(HeatedNest.class);
        handleAnnotations(Institute.class);
        handleAnnotations(MyList.class);
        handleAnnotations(Nest.class);
        handleAnnotations(SandClayFilling.class);
        handleAnnotations(Author.class);
        handleAnnotations(HistoryConstraint.class);
        handleAnnotations(Invariant.class);
        handleAnnotations(PostCondition.class);
        handleAnnotations(PreCondition.class);

        countAuthors();
    }

    @Author(name = "Maximilian Mauroner")
    public static void countAuthors(){

    }

    @Author(name = "Maximilian Mauroner")
    public static void handleAnnotations(Class<?> clazz) {
        List<String> annotations = new ArrayList<>();
        addAuthorAnn(annotations, clazz);
        addInvariantAnn(annotations, clazz);
        addPreAnn(annotations, clazz);
        addPostAnn(annotations, clazz);
        addHistoryAnn(annotations, clazz);
        System.out.println("Class:" + clazz.getName() + ": \t annotations:" + annotations);

    }

    @Author(name = "Maximilian Mauroner")
    public static void addAuthorAnn(List<String> annotations, Class<?> clazz) {
        Author annotation = clazz.getAnnotation(Author.class);

        if (annotation != null) {
            annotations.add("Author:" + annotation.name());
        }

        Arrays.stream(clazz.getMethods()).toList().forEach(method -> {
            if(!Modifier.isPrivate(method.getModifiers())){
                Author methodAnnotation = method.getAnnotation(Author.class);
                if (methodAnnotation != null) {
                    annotations.add("\n\t Method: " + method.getName() + "\t\t Annotation: " + methodAnnotation.name());
                }
            }
        });
    }

    @Author(name = "Maximilian Mauroner")
    public static void addHistoryAnn(List<String> annotations, Class<?> clazz) {
        HistoryConstraint annotation = clazz.getAnnotation(HistoryConstraint.class);

        if (annotation != null) {
            annotations.add("History Constraint:" + annotation.constraint());
        }
    }

    @Author(name = "Maximilian Mauroner")
    public static void addInvariantAnn(List<String> annotations, Class<?> clazz) {
        Invariant annotation = clazz.getAnnotation(Invariant.class);

        if (annotation != null) {
            annotations.add("Invariant:" + annotation.invariant());
        }
    }

    @Author(name = "Maximilian Mauroner")
    public static void addPreAnn(List<String> annotations, Class<?> clazz) {
        PreCondition annotation = clazz.getAnnotation(PreCondition.class);

        if (annotation != null) {
            annotations.add("Precondition:" + annotation.condition());
        }

        Arrays.stream(clazz.getMethods()).toList().forEach(method -> {
            PreCondition methodAnnotation = method.getAnnotation(PreCondition.class);
            if (methodAnnotation != null) {
                annotations.add("\n\t Method: " + method.getName() + "\t\t Annotation: " + methodAnnotation.condition());
            }
        });

    }

    @Author(name = "Maximilian Mauroner")
    public static void addPostAnn(List<String> annotations, Class<?> clazz) {
        PostCondition annotation = clazz.getAnnotation(PostCondition.class);
        if (annotation != null) {
            annotations.add("Postcondition:" + annotation.condition());
        }
        Arrays.stream(clazz.getMethods()).toList().forEach(method -> {
            PostCondition methodAnnotation = method.getAnnotation(PostCondition.class);
            if (methodAnnotation != null) {
                annotations.add("\n\t Method: " + method.getName() + "\t\t Annotation: " + methodAnnotation.condition());
            }
        });

    }
}
