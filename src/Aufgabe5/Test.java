import formicarium.*;

import java.util.ArrayList;
import java.util.List;

/*
Distribution of Work:
- Maximilian: Hierarchie, MyList, MyStatisticsList
- Lukas: Hierarchie, StatSet, CompatibilitySet, Numeric
- Christopher: Hierarchie, UnitTest
 */


public class Test {
    public static void main(String[] args) {
        List<Numeric> numericsList = new ArrayList<>();
        numericsList.add(new Numeric(1));
        numericsList.add(new Numeric(2));
        numericsList.add(new Numeric(3));
        numericsList.add(new Numeric(4));
        numericsList.add(new Numeric(5));
        numericsList.add(new Numeric(6));


        List<Arena> arenaList = new ArrayList<>();
        arenaList.add(new Arena(1, "professional"));
        arenaList.add(new Arena(1, "semi-professional"));
        arenaList.add(new Arena(1, "hobby"));
        arenaList.add(new Arena(2, "professional"));
        arenaList.add(new Arena(2, "semi-professional"));
        arenaList.add(new Arena(2, "hobby"));

        List<Nest> nestList = new ArrayList<>();
        nestList.add(new Nest(1, "professional"));
        nestList.add(new Nest(1, "semi-professional"));
        nestList.add(new Nest(1, "hobby"));
        nestList.add(new Nest(2, "professional"));
        nestList.add(new Nest(2, "semi-professional"));
        nestList.add(new Nest(2, "hobby"));

        List<Part> partList = new ArrayList<>();
        partList.addAll(arenaList);
        partList.addAll(nestList);

        StatSet<Numeric, Numeric, Numeric> statSet0 = new StatSet<>();

        StatSet<Part, Part, Quality> statSet1 = new StatSet<>();
        StatSet<Arena, Part, Quality> statSet2 = new StatSet<>();
        StatSet<Nest, Part, Quality> statSet3 = new StatSet<>();
        StatSet<Part, Arena, Quality> statSet4 = new StatSet<>();
        StatSet<Arena, Arena, Quality> statSet5 = new StatSet<>();
        StatSet<Nest, Arena, Quality> statSet6 = new StatSet<>();
        StatSet<Part, Nest, Quality> statSet7 = new StatSet<>();
        StatSet<Arena, Nest, Quality> statSet8 = new StatSet<>();
        StatSet<Nest, Nest, Quality> statSet9 = new StatSet<>();
        CompatibilitySet<Numeric, Numeric> compatSet1 = new CompatibilitySet<>();
        CompatibilitySet<Part, Quality> compatSet2 = new CompatibilitySet<>();
        CompatibilitySet<Arena, Quality> compatSet3 = new CompatibilitySet<>();
        CompatibilitySet<Nest, Quality> compatSet4 = new CompatibilitySet<>();

        // Test 1: Befüllen der StatSets
        fill(statSet0, numericsList, numericsList);
        fill(statSet1, partList, partList);
        fill(statSet2, arenaList, partList);
        fill(statSet3, nestList, partList);
        fill(statSet4, partList, arenaList);
        fill(statSet5, arenaList, arenaList);
        fill(statSet6, nestList, arenaList);
        fill(statSet7, partList, nestList);
        fill(statSet8, arenaList, nestList);
        fill(statSet9, nestList, nestList);

        //Test 0: Statistics


        StringBuilder expected0 = prepareStatisticsTest(numericsList, numericsList);
        StringBuilder expected1 = prepareStatisticsTest(partList, partList);
        StringBuilder expected2 = prepareStatisticsTest(arenaList, partList);
        StringBuilder expected3 = prepareStatisticsTest(nestList, partList);
        StringBuilder expected4 = prepareStatisticsTest(partList, arenaList);
        StringBuilder expected5 = prepareStatisticsTest(arenaList, arenaList);
        StringBuilder expected6 = prepareStatisticsTest(nestList, arenaList);
        StringBuilder expected7 = prepareStatisticsTest(partList, nestList);
        StringBuilder expected8 = prepareStatisticsTest(arenaList, nestList);
        StringBuilder expected9 = prepareStatisticsTest(nestList, nestList);
        expected0.append("statistics");
        expected1.append("statistics");
        expected2.append("statistics");
        expected3.append("statistics");
        expected4.append("statistics");
        expected5.append("statistics");
        expected6.append("statistics");
        expected7.append("statistics");
        expected8.append("statistics");
        expected9.append("statistics");
        testEquals(statSet0.statistics(), expected0.toString());
        testEquals(statSet1.statistics(), expected1.toString());
        testEquals(statSet2.statistics(), expected2.toString());
        testEquals(statSet3.statistics(), expected3.toString());
        testEquals(statSet4.statistics(), expected4.toString());
        testEquals(statSet5.statistics(), expected5.toString());
        testEquals(statSet6.statistics(), expected6.toString());
        testEquals(statSet7.statistics(), expected7.toString());
        testEquals(statSet8.statistics(), expected8.toString());
        testEquals(statSet9.statistics(), expected9.toString());

        //Test 2: Iterator
        StatSet<Part, Arena, Quality> a = new StatSet<>();
        StatSet<Arena, Part, Quality> b = new StatSet<>();
        StatSet<Arena, Nest, Quality> c = new StatSet<>();
        fill(c, arenaList, nestList);

        c.forEach(a::add);
        c.forEach(b::addCriterion);

        //Test 3. Untertyp-beziehung
        fill(compatSet1, numericsList, numericsList);
        fill(compatSet2, partList, partList);
        fill(compatSet3, arenaList, arenaList);
        fill(compatSet4, nestList, nestList);

        // Test 3.1: Equals
        Arena A = new Arena(1, "professional");
        StatSet<Arena, Arena, Quality> test = new StatSet<>();
        CompatibilitySet<Arena, Quality> test2 = new CompatibilitySet<>();
        test.add(A);
        test2.add(A);

        testEquals(test2.equals(test), false);
        testEquals(test.equals(test2), true);

        // Test 4: Funktionalität
        statSet0.forEach(element -> testContains(element, numericsList));
        statSet1.forEach(element -> testContains(element, partList));
        statSet2.forEach(element -> testContains(element, arenaList));
        statSet3.forEach(element -> testContains(element, nestList));
        statSet4.forEach(element -> testContains(element, partList));
        statSet5.forEach(element -> testContains(element, arenaList));
        statSet6.forEach(element -> testContains(element, nestList));
        statSet7.forEach(element -> testContains(element, partList));
        statSet8.forEach(element -> testContains(element, arenaList));
        statSet9.forEach(element -> testContains(element, nestList));

        statSet0.criterions().forEachRemaining(element -> testContains(element, numericsList));
        statSet1.criterions().forEachRemaining(element -> testContains(element, partList));
        statSet2.criterions().forEachRemaining(element -> testContains(element, partList));
        statSet3.criterions().forEachRemaining(element -> testContains(element, partList));
        statSet4.criterions().forEachRemaining(element -> testContains(element, arenaList));
        statSet5.criterions().forEachRemaining(element -> testContains(element, arenaList));
        statSet6.criterions().forEachRemaining(element -> testContains(element, arenaList));
        statSet7.criterions().forEachRemaining(element -> testContains(element, nestList));
        statSet8.criterions().forEachRemaining(element -> testContains(element, nestList));
        statSet9.criterions().forEachRemaining(element -> testContains(element, nestList));

        testNotNull(statSet0);
        testNotNull(statSet1);
        testNotNull(statSet2);
        testNotNull(statSet3);
        testNotNull(statSet4);
        testNotNull(statSet5);
        testNotNull(statSet6);
        testNotNull(statSet7);
        testNotNull(statSet8);
        testNotNull(statSet9);


        StatSet<Nest, Nest, Quality> statSet9_2 = new StatSet<Nest, Nest, Quality>();
        fill(statSet9_2, nestList, nestList);
        testEquals(statSet9_2.criterions().next().toString(), new Nest(1, "professional").toString());
        testEquals(statSet9.equals(statSet9_2), true);
        testEquals(statSet9.equals(statSet8), false);

        testEquals(statSet1.iterator(new Quality("professional")).hasNext(), false);
        testEquals(statSet1.iterator(new Arena(1, "hobby"), new Quality("professional")).hasNext(), false);

        compatSet1.identical().forEachRemaining(element -> testContains(element, numericsList));
        compatSet2.identical().forEachRemaining(element -> testContains(element, partList));
        compatSet3.identical().forEachRemaining(element -> testContains(element, arenaList));
        compatSet4.identical().forEachRemaining(element -> testContains(element, nestList));

        CompatibilitySet<Numeric, Numeric> compatSet1_2 = new CompatibilitySet<Numeric, Numeric>();
        fill(compatSet1_2, numericsList, numericsList);
        testEquals(compatSet1.equals(compatSet1_2), true);
        testEquals(compatSet1.equals(compatSet3), false);


        testNumeric(); //Test 5: Funktionalität von Numeric
        testQuality(); //Test 5: Funktionalität von Quality
        testArena(); // Test 5: Funktionalität von Arena
        testNest();// Test 5: Funktionalität von Nest


        expected0 = prepareStatisticsTest(numericsList, numericsList);
        expected0.append("statistics\n");
        expected0.append("iterator X\n");
        expected0.append("criterions P\n");
        expected0.append("iterator X\n");
        expected0.append("criterions P\n");
        expected0.append("statistics");
        testEquals(statSet0.statistics(), expected0.toString());


        StringBuilder compExpect = prepareStatisticsTest(nestList, nestList);
        compExpect.append("identical\n");
        compExpect.append("iterator X\n");
        compExpect.append("criterions P\n");
        compExpect.append("identical.hasNext\n");
        compExpect.append("identical.next\n");
        compExpect.append("identical.hasNext\n");
        compExpect.append("identical.next\n");
        compExpect.append("identical.hasNext\n");
        compExpect.append("identical.next\n");
        compExpect.append("identical.hasNext\n");
        compExpect.append("identical.next\n");
        compExpect.append("identical.hasNext\n");
        compExpect.append("identical.next\n");
        compExpect.append("identical.hasNext\n");
        compExpect.append("identical.next\n");
        compExpect.append("identical.hasNext\n");
        compExpect.append("statistics");
        testEquals(compatSet4.statistics(), compExpect.toString());
    }

    private static <X extends Rated<? super P, R>, P, R extends Calc<R>> StringBuilder prepareStatisticsTest(List<? extends X> listX, List<? extends P> listP) {
        StringBuilder expected = new StringBuilder();
        for (var x : listX) expected.append("add X\n");
        for (var p : listP) expected.append("addCriterion P\n");
        return expected;
    }

    private static <X extends Rated<? super P, R>, P, R extends Calc<R>> void testNotNull(StatSet<X, P, R> set) {
        if (set != null && set.iterator().hasNext() && set.criterions().hasNext()) {
            System.out.println("Successful test");
        } else {
            System.out.println("Test NOT successful! Expected value: not null / Given value: null");
        }
    }

    private static void testNumeric() {
        testTrue(new Numeric(1).ratio(5).atleast(new Numeric(0.2)), true);
        testTrue(new Numeric(1).ratio(5).atleast(new Numeric(0.1)), true);
        testTrue(new Numeric(1).ratio(10).atleast(new Numeric(0.1)), true);
        testTrue(new Numeric(2).ratio(8).atleast(new Numeric(0.25)), true);
        testTrue(new Numeric(3).ratio(6).atleast(new Numeric(0.5)), true);
        testTrue(new Numeric(4).ratio(4).atleast(new Numeric(1)), true);

        testEquals(new Numeric(1).rated(operand -> 0).toString(), "0.0");
        testEquals(new Numeric(2).rated(operand -> 1).toString(), "1.0");
        testEquals(new Numeric(40).rated(operand -> 2).toString(), "2.0");
        testEquals(new Numeric(3).rated(operand -> 3).toString(), "3.0");
        testEquals(new Numeric(4).rated(operand -> 4).toString(), "4.0");
        testEquals(new Numeric(5).rated(operand -> 5).toString(), "5.0");
        testEquals(new Numeric(6).rated(operand -> 6).toString(), "6.0");

        testEquals(new Numeric(1).toString(), "1.0");
        testEquals(new Numeric(2).toString(), "2.0");
        testEquals(new Numeric(3).toString(), "3.0");
        testEquals(new Numeric(4).toString(), "4.0");
        testEquals(new Numeric(5).toString(), "5.0");
        testEquals(new Numeric(6).toString(), "6.0");

        testEquals(new Numeric(1).sum(new Numeric(1)).toString(), "2.0");
        testEquals(new Numeric(1).sum(new Numeric(2)).toString(), "3.0");
        testEquals(new Numeric(2).sum(new Numeric(3)).toString(), "5.0");
        testEquals(new Numeric(3).sum(new Numeric(4)).toString(), "7.0");
        testEquals(new Numeric(4).sum(new Numeric(5)).toString(), "9.0");
        testEquals(new Numeric(5).sum(new Numeric(6)).toString(), "11.0");

        testEquals(new Numeric(1).applyAsDouble(1), 1.0);
        testEquals(new Numeric(2).applyAsDouble(1), 2.0);
        testEquals(new Numeric(3).applyAsDouble(1), 3.0);
        testEquals(new Numeric(4).applyAsDouble(1), 4.0);
        testEquals(new Numeric(5).applyAsDouble(1), 5.0);
        testEquals(new Numeric(6).applyAsDouble(1), 6.0);

        testEquals(new Numeric(1).ratio(5).toString(), "0.2");
        testEquals(new Numeric(1).ratio(2).toString(), "0.5");
        testEquals(new Numeric(1).ratio(4).toString(), "0.25");
        testEquals(new Numeric(1).ratio(3).toString(), "0.3333333333333333");
        testEquals(new Numeric(2).ratio(4).toString(), "0.5");
        testEquals(new Numeric(2).ratio(3).toString(), "0.6666666666666666");

        testTrue(new Numeric(1).atleast(new Numeric(1)), true);
        testTrue(new Numeric(1).atleast(new Numeric(2)), false);
        testTrue(new Numeric(2).atleast(new Numeric(1)), true);
        testTrue(new Numeric(2).atleast(new Numeric(2)), true);
        testTrue(new Numeric(2).atleast(new Numeric(3)), false);
        testTrue(new Numeric(3).atleast(new Numeric(2)), true);

        Numeric n3 = new Numeric(3);
        Numeric n4 = new Numeric(4);
        Numeric n5 = new Numeric(5);
        Numeric n6 = new Numeric(6);
        Numeric n1 = new Numeric(1);
        Numeric n2 = new Numeric(2);
        n1.setCriterion(operand -> operand * 2);
        n2.setCriterion(operand -> operand * 2);
        n3.setCriterion(operand -> operand * 3);
        n4.setCriterion(operand -> operand * 4);
        n5.setCriterion(operand -> operand * 5);
        n6.setCriterion(operand -> operand * 6);
        testEquals(n1.rated().toString(), "2.0");
        testEquals(n2.rated().toString(), "4.0");
        testEquals(n3.rated().toString(), "9.0");
        testEquals(n4.rated().toString(), "16.0");
        testEquals(n5.rated().toString(), "25.0");
        testEquals(n6.rated().toString(), "36.0");
    }

    private static void testQuality() {
//        Konstruktor Tests:
        testEquals(new Quality("professional").toString(), "professional");
        testEquals(new Quality("semi-professional").toString(), "semi-professional");
        testEquals(new Quality("hobby").toString(), "hobby");
        testEquals(new Quality("unknown").toString(), "not applicable");

//        sum() Tests:
        testEquals(new Quality("professional").sum(new Quality("professional")).toString(), "professional");
        testEquals(new Quality("professional").sum(new Quality("semi-professional")).toString(), "semi-professional");
        testEquals(new Quality("professional").sum(new Quality("hobby")).toString(), "hobby");
        testEquals(new Quality("semi-professional").sum(new Quality("semi-professional")).toString(), "semi-professional");
        testEquals(new Quality("semi-professional").sum(new Quality("hobby")).toString(), "hobby");
        testEquals(new Quality("hobby").sum(new Quality("hobby")).toString(), "hobby");
        testEquals(new Quality("unknown").sum(new Quality("professional")).toString(), "not applicable");
        testEquals(new Quality("professional").sum(new Quality("unknown")).toString(), "not applicable");
        testEquals(new Quality("unknown").sum(new Quality("unknown")).toString(), "not applicable");

        testEquals(new Quality("professional").ratio(1).toString(), "professional");
        testEquals(new Quality("semi-professional").ratio(1).toString(), "semi-professional");
        testEquals(new Quality("hobby").ratio(1).toString(), "hobby");

        testEquals(new Quality("professional").atleast(new Quality("professional")), true);
        testEquals(new Quality("professional").atleast(new Quality("hobby")), true);
        testEquals(new Quality("professional").atleast(new Quality("semi-professional")), true);
        testEquals(new Quality("hobby").atleast(new Quality("professional")), false);
        testEquals(new Quality("semi-professional").atleast(new Quality("professional")), false);
    }

    private static void testArena() {
        testEquals(new Arena(1, "professional").usage(), "professional");
        testEquals(new Arena(2, "semi-professional").usage(), "semi-professional");
        testEquals(new Arena(3, "hobby").usage(), "hobby");
        testEquals(new Arena(4, "semi-professional").usage(), "semi-professional");
        testEquals(new Arena(5, "hobby").usage(), "hobby");

        testEquals(new Arena(1, "professional").volume(), 1.0);
        testEquals(new Arena(2, "semi-professional").volume(), 2.0);
        testEquals(new Arena(3, "hobby").volume(), 3.0);
        testEquals(new Arena(4, "semi-professional").volume(), 4.0);
        testEquals(new Arena(5, "hobby").volume(), 5.0);

        testEquals(new Arena(1, "professional").rated().toString(), "not applicable");
        testEquals(new Arena(2, "semi-professional").rated().toString(), "not applicable");
        testEquals(new Arena(3, "hobby").rated().toString(), "not applicable");
        testEquals(new Arena(4, "semi-professional").rated().toString(), "not applicable");
        testEquals(new Arena(5, "hobby").rated().toString(), "not applicable");

        testEquals(new Arena(1, "professional").rated(new Arena(1, "professional")).toString(), "professional");
        testEquals(new Arena(2, "semi-professional").rated(new Arena(2, "semi-professional")).toString(), "semi-professional");
        testEquals(new Arena(3, "hobby").rated(new Arena(3, "hobby")).toString(), "hobby");
        testEquals(new Arena(4, "semi-professional").rated(new Arena(4, "semi-professional")).toString(), "semi-professional");
        testEquals(new Arena(5, "hobby").rated(new Arena(5, "hobby")).toString(), "hobby");

        Arena a1 = new Arena(1, "professional");
        Arena a2 = new Arena(2, "semi-professional");
        Arena a3 = new Arena(3, "hobby");
        Arena a4 = new Arena(4, "semi-professional");
        Arena a5 = new Arena(5, "hobby");
        a1.setCriterion(new Nest(1, "hobby"));
        a2.setCriterion(new Nest(2, "leisure"));
        a3.setCriterion(new Nest(3, "competition"));
        a4.setCriterion(new Nest(4, "tournament"));
        a5.setCriterion(new Nest(5, "practice"));
        testEquals(a1.toString(), "Arena");
        testEquals(a2.toString(), "Arena");
        testEquals(a3.toString(), "Arena");
        testEquals(a4.toString(), "Arena");
        testEquals(a5.toString(), "Arena");

        testEquals(new Arena(1, "professional").rated().toString(), "not applicable");
    }

    private static void testNest() {
        testEquals(new Nest(1, "professional").usage(), "professional");
        testEquals(new Nest(2, "hobby").usage(), "hobby");
        testEquals(new Nest(3, "semi-professional").usage(), "semi-professional");
        testEquals(new Nest(4, "professional").usage(), "professional");
        testEquals(new Nest(5, "hobby").usage(), "hobby");

        testEquals(new Nest(1, "professional").rated(new Nest(1, "professional")).toString(), "not applicable");
        testEquals(new Nest(1, "professional").rated(new Arena(1, "professional")).toString(), "professional");
        testEquals(new Nest(2, "hobby").rated(new Nest(2, "hobby")).toString(), "not applicable");
        testEquals(new Nest(2, "hobby").rated(new Arena(2, "hobby")).toString(), "hobby");
        testEquals(new Nest(3, "semi-professional").rated(new Nest(3, "semi-professional")).toString(), "not applicable");
        testEquals(new Nest(3, "semi-professional").rated(new Arena(3, "semi-professional")).toString(), "semi-professional");

        Nest nest1 = new Nest(1, "professional");
        Nest nest2 = new Nest(2, "hobby");
        Nest nest3 = new Nest(3, "semi-professional");
        Nest nest4 = new Nest(4, "professional");
        Nest nest5 = new Nest(5, "hobby");
        nest1.setCriterion(new Nest(1, "hobby"));
        nest2.setCriterion(new Nest(2, "leisure"));
        nest3.setCriterion(new Nest(3, "competition"));
        nest4.setCriterion(new Nest(4, "tournament"));
        nest5.setCriterion(new Nest(5, "practice"));
        testEquals(nest1.toString(), "Nest");
        testEquals(nest2.toString(), "Nest");
        testEquals(nest3.toString(), "Nest");
        testEquals(nest4.toString(), "Nest");
        testEquals(nest5.toString(), "Nest");
    }

    private static <X extends Rated<? super P, R>, P, R extends Calc<R>> void fill(StatSet<X, P, R> set1, List<? extends X> listX, List<? extends P> listP) {
        for (X x : listX) set1.add(x);
        for (P p : listP) set1.addCriterion(p);
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

    public static <T> void testContains(T given, List<T> expected) {
        if (expected.contains(given)) {
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
