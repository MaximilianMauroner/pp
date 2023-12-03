import Colony.*;
import Formicarium.*;

public class Test {
    public static void main(String[] args) {
        Institute i = new Institute();

        // Test 1 - Add Formicarium & Remove Formicarium
        Formicarium f = new LargeUnconditionedFormicarium(4);
        i.addForm(f);
        testEquals(i.containsForm(f), true);
        i.removeForm(f);
        testEquals(i.containsForm(f), false);

        // Test 2 - Assign Formicarium
        AntColony lec = new MediumEuropeanColony();
        AntColony lec2 = new LargeEuropeanColony();
        Formicarium res = i.assignForm(lec);
        testEquals(res == null, true);

        i.addForm(f);
        res = i.assignForm(lec);
        Formicarium res2 = res;
        testEquals(res == null, false);
        testEquals(res.antType(), lec);
        res = i.assignForm(lec2);
        testEquals(res == null, true);

        i.returnForm(res2);
        res = i.assignForm(lec2);
        testEquals(res == null, false);
        testEquals(res.antType(), lec2);

        // Test 3 - Return Formicarium

        // Test 4 - Price Free & Price Occupied

        // Test 5 - Show Formicarium & Show Ants

        // Test 6 - Add Ant Colony & Remove Ant Colony
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
