/* ToDo: add distinction between the types (and maybe sort them according to hierarchy)
Justification of for not implementing sub type relations

AntFarm:
    - AntFarm:
    - Arena:
    - Compatability:
    - CompositeFormicarium:
    - Forceps:
    - Formicarium:
    - FormicariumItem:
    - FormicariumPart:
    - FormicariumSet:
    - Instrument:
    - Nest:
    - Thermometer:

Arena:
    - AntFarm:
    - Arena:
    - Compatability:
    - CompositeFormicarium:
    - Forceps:
    - Formicarium:
    - FormicariumItem:
    - FormicariumPart:
    - FormicariumSet:
    - Instrument:
    - Nest:
    - Thermometer:

Compatability:
    - AntFarm:
    - Arena:
    - Compatability:
    - CompositeFormicarium:
    - Forceps:
    - Formicarium:
    - FormicariumItem:
    - FormicariumPart:
    - FormicariumSet:
    - Instrument:
    - Nest:
    - Thermometer:

CompositeFormicarium:
    - AntFarm:
    - Arena:
    - Compatability:
    - CompositeFormicarium:
    - Forceps:
    - Formicarium:
    - FormicariumItem:
    - FormicariumPart:
    - FormicariumSet:
    - Instrument:
    - Nest:
    - Thermometer:

Forceps:
    - AntFarm:
    - Arena:
    - Compatability:
    - CompositeFormicarium:
    - Forceps:
    - Formicarium:
    - FormicariumItem:
    - FormicariumPart:
    - FormicariumSet:
    - Instrument:
    - Nest:
    - Thermometer:

Formicarium:
    - AntFarm:
    - Arena:
    - Compatability:
    - CompositeFormicarium:
    - Forceps:
    - Formicarium:
    - FormicariumItem:
    - FormicariumPart:
    - FormicariumSet:
    - Instrument:
    - Nest:
    - Thermometer:

FormicariumItem:
    - AntFarm:
    - Arena:
    - Compatability:
    - CompositeFormicarium:
    - Forceps:
    - Formicarium:
    - FormicariumItem:
    - FormicariumPart:
    - FormicariumSet:
    - Instrument:
    - Nest:
    - Thermometer:

FormicariumPart:
    - AntFarm:
    - Arena:
    - Compatability:
    - CompositeFormicarium:
    - Forceps:
    - Formicarium:
    - FormicariumItem:
    - FormicariumPart:
    - FormicariumSet:
    - Instrument:
    - Nest:
    - Thermometer:

FormicariumSet:
    - AntFarm:
    - Arena:
    - Compatability:
    - CompositeFormicarium:
    - Forceps:
    - Formicarium:
    - FormicariumItem:
    - FormicariumPart:
    - FormicariumSet:
    - Instrument:
    - Nest:
    - Thermometer:

Instrument:
    - AntFarm:
    - Arena:
    - Compatability:
    - CompositeFormicarium:
    - Forceps:
    - Formicarium:
    - FormicariumItem:
    - FormicariumPart:
    - FormicariumSet:
    - Instrument:
    - Nest:
    - Thermometer:

Nest:
    - AntFarm:
    - Arena:
    - Compatability:
    - CompositeFormicarium:
    - Forceps:
    - Formicarium:
    - FormicariumItem:
    - FormicariumPart:
    - FormicariumSet:
    - Instrument:
    - Nest:
    - Thermometer:

Thermometer:
    - AntFarm:
    - Arena:
    - Compatability:
    - CompositeFormicarium:
    - Forceps:
    - Formicarium:
    - FormicariumItem:
    - FormicariumPart:
    - FormicariumSet:
    - Instrument:
    - Nest:
    - Thermometer:
 */

import formicarium.*;

public class Test {
    public static void main(String[] args) {
        System.out.println("Test Cases: ");

        Thermometer thermometer = new Thermometer("hobby");

        CompositeFormicarium comp = new CompositeFormicarium(thermometer);

        Formicarium antFarm = new AntFarm(thermometer, "Sand", 10);
        comp.add(antFarm);

        Arena arena = new Arena("Sand", 10);
        comp.add(arena);

        System.out.println("Test CompositeFormicarium & Compatability");
        testEquals(comp.get(0), thermometer);
        testEquals(comp.get(1), antFarm);
        try {
            comp.get(2);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Successful test");
        }

        FormicariumPartIterator iter = (FormicariumPartIterator) comp.iterator();
        System.out.println("Test FormicariumPartIterator");

        testClass(iter, comp.iterator());
        int i = 0;
        while (iter.hasNext()) {
            FormicariumPart part = iter.next();

            if (i == 0) {
                testEquals(part, thermometer);
            } else if (i == 1) {
                testEquals(part, antFarm);
            } else if (i == 2) {
                testEquals(part, arena);
            }
            i++;
            iter.remove();
        }

        System.out.println("Test FormicariumSet");


        FormicariumSet set = new FormicariumSet();
        set.add(antFarm);
        set.add(new AntFarm("Sand", 10));
        set.add(new AntFarm("Sand", 10));
        set.add(new AntFarm("Sand", 10));
        set.add(new AntFarm("Sand", 10));
        set.add(new AntFarm("Sand", 10));
        set.add(new AntFarm("Sand", 10));
        set.add(new AntFarm("Sand", 10));

        testEquals(set.getItems().contains(antFarm), true);
        testEquals(set.getItems().contains(new AntFarm("Sand", 10)), false);

        FormicariumItemIterator iter2 = (FormicariumItemIterator) set.iterator();
        System.out.println("Test FormicariumItemIterator");

        testClass(iter2, set.iterator());
        if (iter2.hasNext()) {
            FormicariumItem item = iter2.next();
            testEquals(iter2.count() + "", 8 + "");
            iter2.remove(4);
            testEquals(iter2.count() + "", 4 + "");
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
