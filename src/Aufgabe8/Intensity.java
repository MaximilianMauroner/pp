public class Intensity {
    public Intensity(int i, int j, double intensity) {
        if (j > i) {
            this.i = j;
            this.j = i;
        } else {
            this.i = i;
            this.j = j;
        }
        this.intensity = intensity;
    }

    public final int i;
    public final int j;
    public final double intensity;
}
