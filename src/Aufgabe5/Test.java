import formicarium.*;

import java.util.Iterator;
import java.util.function.DoubleUnaryOperator;

public class Test {
    public static void main(String[] args) {
        System.out.println("Hello World!");

        CompatibilitySet<Numeric,Numeric> lmmmmao = new CompatibilitySet<Numeric,Numeric>();
        StatSet<Rated<Numeric, Numeric>,Numeric, Numeric> lmao = new StatSet<Numeric,Numeric,Numeric>();
//        lmao.add(new Object());
//        lmao.add(new Object());
//        lmao.add(new Object());
//        lmao.add(new Object());
//        lmao.add(new Object());
//        lmao.add(new Object());
//        lmao.add(new Object());
        Iterator<Numeric> iter = lmao.iterator();
        while(iter.hasNext()){
            Numeric t = iter.next();
            System.out.println(t);
        }
        StatSet<Part,Part,Quality>lmao2 = new StatSet<>();
        StatSet<Arena,Part,Quality> lmao3 = new StatSet<>();
        StatSet<Nest,Part,Quality> lmao4 = new StatSet<>();
//        StatSet<Part,Arena,Quality> lmao5 = new StatSet<>();
//        StatSet<Arena,Arena,Quality> lmao6 = new StatSet<>();
//        StatSet<Nest,Arena,Quality> lmao7 = new StatSet<>();
//        StatSet<Part,Nest,Quality> lmao8 = new StatSet<>();
//        StatSet<Arena,Nest,Quality> lmao9 = new StatSet<>();
//        StatSet<Nest,Nest,Quality> lmao10 = new StatSet<>();
//        CompatibilitySet<Numeric,Numeric>
//        CompatibilitySet<Part,Quality>
//        CompatibilitySet<Arena,Quality>
//        CompatibilitySet<Nest,Quality>

    }
}
