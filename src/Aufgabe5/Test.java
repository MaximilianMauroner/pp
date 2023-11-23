import formicarium.*;

import java.util.Iterator;
import java.util.function.DoubleUnaryOperator;

public class Test {
    public static void main(String[] args) {
        System.out.println("Hello World!");

        Numeric op = new Numeric(5);
        op.setCriterion(operand -> operand / 5);

        StatSet<Numeric, Numeric, Numeric> StatSet0 = new StatSet<Numeric, Numeric, Numeric>();
        StatSet0.add(new Numeric(1));
        StatSet0.add(new Numeric(2));
        StatSet0.add(new Numeric(3));
        StatSet0.add(new Numeric(4));
        StatSet0.add(new Numeric(5));
        StatSet0.addCriterion(op);

        Iterator<Numeric> iter = StatSet0.iterator(new Numeric(1));
        while(iter.hasNext()){
            Numeric t = iter.next();
            System.out.println(t);
        }

        StatSet<Part, Nest, Quality> a = new StatSet<Part, Nest, Quality>();
        StatSet<Arena, Part, Quality> b = new StatSet<Arena, Part, Quality>();
        StatSet<Arena, Nest, Quality> c = new StatSet<Arena, Nest, Quality>();

        c.add(new Arena(1, "professional"));
        c.add(new Arena(2, "semi-professional"));
        c.add(new Arena(3, "hobby"));
        c.add(new Arena(4, "professional"));
        c.addCriterion(new Nest(2, "semi-professional"));
        c.addCriterion(new Nest(3, "hobby"));

        Iterator<Arena> iter2 = c.iterator();
        while(iter2.hasNext()){
            Arena t = iter2.next();
            System.out.println(t.volume());
            a.add(t);
        }
        Iterator<Nest> iter3 = c.criterions();
        while(iter3.hasNext()){
            Nest t = iter3.next();
            System.out.println(t.antSize());
            b.addCriterion(t);
        }



//        CompatibilitySet<Numeric,Numeric> lmmmmao = new CompatibilitySet<Numeric,Numeric>();
//        StatSet<Numeric,Numeric, Numeric> lmao = new StatSet<Numeric,Numeric,Numeric>();
////        lmao.add(new Object());
////        lmao.add(new Object());
////        lmao.add(new Object());
////        lmao.add(new Object());
////        lmao.add(new Object());
////        lmao.add(new Object());
////        lmao.add(new Object());
//        Iterator<Numeric> iter = lmao.iterator();
//        while(iter.hasNext()){
//            Numeric t = iter.next();
//            System.out.println(t);
//        }
//        StatSet<Part,Part,Quality>lmao2 = new StatSet<>();
//        StatSet<Arena,Part,Quality> lmao3 = new StatSet<>();
//        StatSet<Nest,Part,Quality> lmao4 = new StatSet<>();
////        StatSet<Part,Arena,Quality> lmao5 = new StatSet<>();
////        StatSet<Arena,Arena,Quality> lmao6 = new StatSet<>();
////        StatSet<Nest,Arena,Quality> lmao7 = new StatSet<>();
////        StatSet<Part,Nest,Quality> lmao8 = new StatSet<>();
////        StatSet<Arena,Nest,Quality> lmao9 = new StatSet<>();
////        StatSet<Nest,Nest,Quality> lmao10 = new StatSet<>();
////        CompatibilitySet<Numeric,Numeric>
////        CompatibilitySet<Part,Quality>
////        CompatibilitySet<Arena,Quality>
////        CompatibilitySet<Nest,Quality>

    }

}
