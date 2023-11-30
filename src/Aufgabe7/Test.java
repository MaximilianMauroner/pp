import Colony.*;
import Formicarium.*;

public class Test {
    public static void main(String[] args) {
        System.out.println("Hello World!");
        Institute i = new Institute();
        Institute i2 = new Institute();
        Institute i3 = new Institute();

        i.addForm(new SmallUnconditionedFormicarium(1));
        i.addForm(new MediumUnconditionedFormicarium(2));
        i.addForm(new LargeUnconditionedFormicarium(3));
        i.addForm(new SmallConditionedFormicarium(9));
        i.addForm(new MediumConditionedFormicarium(10));
        i.addForm(new LargeConditionedFormicarium(11));

        AntColony ac = new SmallTropicalColony();
        AntColony ad = new SmallTropicalColony();
        AntColony ae = new SmallTropicalColony();
        AntColony ac2 = new MediumEuropeanColony();
        AntColony ac3 = new LargeTropicalColony();
        AntColony ac4 = new LargeEuropeanColony();
        System.out.println(i.assignForm(ac).showFormicarium() + "asdfasdfasdf ");
        System.out.println(i.assignForm(ad).showFormicarium() + "asdfasdfasdf ");
        System.out.println(i.assignForm(ae) + "asdfasdfasdf "); // expected null
        i.assignForm(ac2);
        i.assignForm(ac3);
        i.assignForm(ac4);

        // Test 1 - Add Formicarium & Remove Formicarium

        // Test 2 - Assign Formicarium

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
