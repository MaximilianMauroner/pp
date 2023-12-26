public class Hive {
    private final double hiveX;
    private final double hiveY;

    public Hive() {
        Parameters parameters = Parameters.getInstance();
        if (parameters != null) {
            int size = parameters.get("SIZE");
            this.hiveX = Position.getRandomCoordinate(size);
            this.hiveY = Position.getRandomCoordinate(size);
        } else {
            this.hiveX = 0;
            this.hiveY = 0;
        }
    }

    public double getX() {
        return hiveX;
    }

    public double getY() {
        return hiveY;
    }

}
