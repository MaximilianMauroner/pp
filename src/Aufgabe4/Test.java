/*
Justification of for not implementing sub type relations

From the task description we can generally derive these groups:
- Formicarium(s): Formicarium, Nest, AntFarm, CompositeFormicarium
- Parts: Formicarium, Arena, Thermometer
- Items (basically everything)
- Tools: Instrument, Thermometer, Forceps, ...
- Collections: FormicariumSet
- Meta-Information: Compatability

Compatability:
This holds for all other Types. Compatability cannot be a physical object, therefore it is a meta-information.
Objects may have compatability information but it is in no subtype relationship with any other type.

FormicariumSet:
This is a collection of items. So no subtype relationship with any other type.

CompositeFormicarium:
While generally being a collection. The following reasons hold for CompositeFormicarium:
- A FormicariumPart may be a Formicarium or a Part of one
- CompositeFormicarium is a Formicarium
- CompisiteFormicarium holds Parts of Formicariums (so if something is not a Formicarium it also cannot be a CompositeFormicarium)
- CompositeFormicarium holds Formicariums
- CompositeFormicarium can hold other CompositeFormicariums

FormicariumItem:
- Formicarium: Item can be a Formicarium, which means Formicarium is a subtype (therefore not the other way around)
- Parts: Item can be parts of a Formicarium, which means Parts is a subtype (therefore not the other way around)
- Items: Subtype relation to itself
- Tools: Item can be a Tool, which means Tools is a subtype (therefore not the other way around)

Instrument:
- Formicarium: Instrument represents objects that may be used with a Formicarium but cannot be a Formicarium (so no subtype relation)
- Parts: Some Instruments are parts but not all of them (so no subtype relation)
- Items: Instrument is a subtype of Item
- Tools: Subtype relation to itself

Thermometer
- Formicarium: A Thermometer may be part of a Formicarium but cannot be a Formicarium (so no subtype relation)
- Part: Thermometer is a subtype of Part
- Items: Thermometer is a subtype of Item
- Tools: Thermometer is a subtype of Instrument

Forceps:
- Formicarium: A Forceps may be used with a Formicarium but cannot be a Formicarium (so no subtype relation)
- Parts: Forceps can be used with Formicariums but cannot be a part of it (so no subtype relation)
- Items: Forceps is a subtype of Item
- Tools: Forceps is a subtype of Instrument

FormicariumPart:
- Formicarium: A Part can be a Formicarium, which means Formicarium is a subtype (therefore not the other way around)
- Parts: Subtype relation to itself
- Items: Part is a subtype of Item
- Tools: Some Tools are Parts, which mean they can be subtypes of Part (therefore not the other way around)

Arena:
- Formicarium: Citation "A Arena alone cannot be a Formicarium" (so no subtype relation)
- Parts: Arena is a subtype of Part
- Items: Arena is a subtype of Item
- Tools: A Arena is part of a Formicarium and not a Tool to be used with one (so no subtype relation)

Formicarium:
- Formicarium: Subtype relation to itself
- Parts: Formicarium is a subtype of Part
- Items: Formicarium is a subtype of Item
- Tools: Tools may be used with a Formicarium or be part of it but a Formicarium cannot be a Tool (so no subtype relation)

CompositeFormicarium:
- Formicarium: A CompositeFormicarium is a Formicarium
- Parts: A CompositeFormicarium is a Part
- Items: A CompositeFormicarium is an Item
- Tools: Same reasoning as for Formicarium

Nest:
- Formicarium: A Nest is a Formicarium
- Parts: A Nest is a Part
- Items: A Nest is an Item
- Tools: Same reasoning as for Formicarium

AntFarm:
- Formicarium: An AntFarm is a Formicarium
- Parts: An AntFarm is a Part
- Items: An AntFarm is an Item
- Tools: Same reasoning as for Formicarium
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
