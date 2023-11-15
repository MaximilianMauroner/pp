import formicarium.*;

public class UnitTest {
    public static void main(String[] args) {
        StatSet<Numeric, Numeric, Numeric> StatSet0 = new StatSet<Numeric, Numeric, Numeric>();
        StatSet<Part,Part, Quality> StatSet1 = new StatSet<Part,Part, Quality>();
        StatSet<Arena,Part,Quality> StatSet2 = new StatSet<Arena,Part,Quality>();
        StatSet<Nest,Part,Quality> StatSet3 = new StatSet<Nest,Part,Quality>();
        StatSet<Part,Arena,Quality> StatSet4 = new StatSet<Part,Arena,Quality>();
        StatSet<Arena,Arena,Quality> StatSet5 = new StatSet<Arena,Arena,Quality>();
        StatSet<Nest,Arena,Quality> StatSet6 = new StatSet<Nest,Arena,Quality>();
        StatSet<Part,Nest,Quality> StatSet7 = new StatSet<Part, Nest,Quality>();
        StatSet<Arena,Nest,Quality> StatSet8 = new StatSet<Arena,Nest,Quality>();
        StatSet<Nest,Nest,Quality> StatSet9 = new StatSet<Nest,Nest,Quality>();
        CompatibilitySet<Numeric,Numeric> compatSet1 = new CompatibilitySet<Numeric,Numeric>();
        CompatibilitySet<Part,Quality> compatSet2 = new CompatibilitySet<Part,Quality>();
        CompatibilitySet<Arena,Quality> compatSet3 = new CompatibilitySet<Arena,Quality>();
        CompatibilitySet<Nest,Quality> compatSet4 = new CompatibilitySet<Nest,Quality>();
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
