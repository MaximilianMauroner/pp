import Aspect.MethodCallCountAspect;
import Colony.*;
import Formicarium.*;

public class Test {
    public static void main(String[] args) {

        testFornicarium();

        // Test 5 - Show Formicarium & Show Ants

        // Test 6 - Add Ant Colony & Remove Ant Colony
    }

    private static void testFornicarium() {
        Institute i = new Institute();
        Formicarium f1 = new LargeUnconditionedFormicarium(1);
        Formicarium f2 = new MediumUnconditionedFormicarium(20);
        Formicarium f3 = new SmallUnconditionedFormicarium(300);
        Formicarium f4 = new LargeConditionedFormicarium(4_000);
        Formicarium f5 = new MediumConditionedFormicarium(50_000);
        Formicarium f6 = new SmallConditionedFormicarium(600_000);

        AntColony ac1 = new LargeEuropeanColony();
        AntColony ac2 = new MediumEuropeanColony();
        AntColony ac3 = new SmallEuropeanColony();
        AntColony ac4 = new LargeTropicalColony();
        AntColony ac5 = new MediumTropicalColony();
        AntColony ac6 = new SmallTropicalColony();
        System.out.println("Test 1 - Add Formicarium & Remove Formicarium");

        i.addForm(f1);
        testEquals(i.containsForm(f1), true);
        i.removeForm(f1);
        testEquals(i.containsForm(f1), false);

        System.out.println("Test 2 & 3 - Assign Formicarium -  Return Formicarium");
        testFormicariumAssignNull(i, f1, ac1, false);
        testFormicariumAssignNull(i, f1, ac2, false);
        testFormicariumAssignNull(i, f1, ac3, true);
        testFormicariumAssignNull(i, f1, ac4, true);
        testFormicariumAssignNull(i, f1, ac5, true);
        testFormicariumAssignNull(i, f1, ac6, true);

        testFormicariumAssignNull(i, f2, ac1, true);
        testFormicariumAssignNull(i, f2, ac2, false);
        testFormicariumAssignNull(i, f2, ac3, false);
        testFormicariumAssignNull(i, f2, ac4, true);
        testFormicariumAssignNull(i, f2, ac5, true);
        testFormicariumAssignNull(i, f2, ac6, true);

        testFormicariumAssignNull(i, f3, ac1, true);
        testFormicariumAssignNull(i, f3, ac2, true);
        testFormicariumAssignNull(i, f3, ac3, false);
        testFormicariumAssignNull(i, f3, ac4, true);
        testFormicariumAssignNull(i, f3, ac5, true);
        testFormicariumAssignNull(i, f3, ac6, true);

        testFormicariumAssignNull(i, f4, ac1, true);
        testFormicariumAssignNull(i, f4, ac2, true);
        testFormicariumAssignNull(i, f4, ac3, true);
        testFormicariumAssignNull(i, f4, ac4, false);
        testFormicariumAssignNull(i, f4, ac5, false);
        testFormicariumAssignNull(i, f4, ac6, true);

        testFormicariumAssignNull(i, f5, ac1, true);
        testFormicariumAssignNull(i, f5, ac2, true);
        testFormicariumAssignNull(i, f5, ac3, true);
        testFormicariumAssignNull(i, f5, ac4, true);
        testFormicariumAssignNull(i, f5, ac5, false);
        testFormicariumAssignNull(i, f5, ac6, false);

        String before = MethodCallCountAspect.exportAsString();

        testFormicariumAssignNull(i, f6, ac1, true);
        testFormicariumAssignNull(i, f6, ac2, true);
        testFormicariumAssignNull(i, f6, ac3, true);
        testFormicariumAssignNull(i, f6, ac4, true);
        testFormicariumAssignNull(i, f6, ac5, true);
        testFormicariumAssignNull(i, f6, ac6, false);

        String after = MethodCallCountAspect.exportAsString();

        System.out.println("Test 4 - Price Free & Price Occupied");

        testEquals(i.priceOccupied(), 0.0);
        i.addForm(f1);
        i.addForm(f2);
        i.addForm(f3);
        i.addForm(f4);
        i.addForm(f5);
        i.addForm(f6);
        testEquals(i.priceFree(), 654321.0);
        i.assignForm(ac1);
        i.assignForm(ac2);
        i.assignForm(ac3);
        i.assignForm(ac4);
        i.assignForm(ac5);
        i.assignForm(ac6);
        testEquals(i.priceOccupied(), 654321.0);


        System.out.println("Test 5 - Show Formicarium & Show Ants");
        testEquals(f1.showFormicarium(), "FormicariumType: Large Unconditioned Formicarium\n" + "Price: 1\n" + "Status: Occupied\n" + "Ant Colony: Large European Colony\n");
        testEquals(ac1.showAntColony(), "Ant Colony: Large European Colony");

        System.out.println("Test 6 - Add Ant Colony & Remove Ant Colony");
        i.addAntColony(ac1);
        testEquals(i.containsAnt(ac1), true);
        i.removeAntColony(ac1);
        testEquals(i.containsAnt(ac1), false);

        System.out.println("Test 7 - Visitor");
        String expectedBefore = "Institute.assignForm(AntColony): 30\nFormicarium.LargeConditionedFormicarium.accept(AntColony): 6\nFormicarium.MediumConditionedFormicarium.antType(): 6\nFormicarium.LargeUnconditionedFormicarium.accept(AntColony): 6\nFormicarium.MediumConditionedFormicarium.accept(AntColony): 6\nFormicarium.LargeUnconditionedFormicarium.antType(): 7\nFormicarium.MediumConditionedFormicarium.setAntType(AntColony): 4\nFormicarium.MediumUnconditionedFormicarium.antType(): 6\nFormicarium.SmallUnconditionedFormicarium.setAntType(AntColony): 2\nFormicarium.MediumUnconditionedFormicarium.setAntType(AntColony): 4\nFormicarium.MediumUnconditionedFormicarium.accept(AntColony): 6\nFormicarium.LargeConditionedFormicarium.antType(): 6\nFormicarium.SmallUnconditionedFormicarium.antType(): 6\nFormicarium.LargeUnconditionedFormicarium.setAntType(AntColony): 4\nFormicarium.SmallUnconditionedFormicarium.accept(AntColony): 6\nFormicarium.LargeConditionedFormicarium.setAntType(AntColony): 4\n".formatted();
        testEquals(before, expectedBefore);
        String expectedAfter = "Institute.assignForm(AntColony): 36\nFormicarium.LargeConditionedFormicarium.accept(AntColony): 6\nFormicarium.MediumConditionedFormicarium.antType(): 6\nFormicarium.LargeUnconditionedFormicarium.accept(AntColony): 6\nFormicarium.SmallConditionedFormicarium.accept(AntColony): 6\nFormicarium.MediumConditionedFormicarium.accept(AntColony): 6\nFormicarium.LargeUnconditionedFormicarium.antType(): 7\nFormicarium.MediumConditionedFormicarium.setAntType(AntColony): 4\nFormicarium.MediumUnconditionedFormicarium.antType(): 6\nFormicarium.SmallUnconditionedFormicarium.setAntType(AntColony): 2\nFormicarium.MediumUnconditionedFormicarium.setAntType(AntColony): 4\nFormicarium.SmallConditionedFormicarium.antType(): 6\nFormicarium.MediumUnconditionedFormicarium.accept(AntColony): 6\nFormicarium.LargeConditionedFormicarium.antType(): 6\nFormicarium.SmallConditionedFormicarium.setAntType(AntColony): 2\nFormicarium.SmallUnconditionedFormicarium.antType(): 6\nFormicarium.LargeUnconditionedFormicarium.setAntType(AntColony): 4\nFormicarium.SmallUnconditionedFormicarium.accept(AntColony): 6\nFormicarium.LargeConditionedFormicarium.setAntType(AntColony): 4\n".formatted();
        testEquals(after, expectedAfter);

    }

    private static void testFormicariumAssignNull(Institute i, Formicarium f, AntColony ac, boolean expected) {
        i.addForm(f);
        Formicarium res = i.assignForm(ac);
        i.removeForm(f);
        testEquals(res == null, expected);
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

    public static void testEquals(int given, int expected) {
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
