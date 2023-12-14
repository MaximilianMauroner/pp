public class Distance {
    public Distance(int i, int j, int distance) {
        if (j > i) {
            this.i = j;
            this.j = i;
        } else {
            this.i = i;
            this.j = j;
        }
        this.distance = distance;
    }

    public final int i;
    public final int j;
    public final int distance;
}
