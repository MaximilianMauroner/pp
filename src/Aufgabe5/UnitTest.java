import formicarium.*;

public class UnitTest {
    public static void main(String[] args) {
        StatSet<Numeric, Numeric, Numeric> StatSet0 = new StatSet<>();
        StatSet<Part,Part, Quality> StatSet1 = new StatSet<>();
        StatSet<Arena,Part,Quality> StatSet2 = new StatSet<>();
        StatSet<Nest,Part,Quality> StatSet3 = new StatSet<>();
        StatSet<Part,Arena,Quality> StatSet4 = new StatSet<>();
        StatSet<Arena,Arena,Quality> StatSet5 = new StatSet<>();
        StatSet<Nest,Arena,Quality> StatSet6 = new StatSet<>();
        StatSet<Part,Nest,Quality> StatSet7 = new StatSet<>();
        StatSet<Arena,Nest,Quality> StatSet8 = new StatSet<>();
        StatSet<Nest,Nest,Quality> StatSet9 = new StatSet<>();
        CompatibilitySet<Numeric,Numeric> compatSet1 = new CompatibilitySet<>();
        CompatibilitySet<Part,Quality> compatSet2 = new CompatibilitySet<>();
        CompatibilitySet<Arena,Quality> compatSet3 = new CompatibilitySet<>();
        CompatibilitySet<Nest,Quality> compatSet4 = new CompatibilitySet<>();


    }
}
