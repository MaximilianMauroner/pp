import java.io.*;

public class Hive {
    private final double hiveX;
    private final double hiveY;
    private final ObjectOutputStream stream;

    public Hive(ObjectOutputStream stream) {
        this.stream = stream;
        Parameters parameters = Parameters.getInstance();
        if (parameters != null) {
            int size = parameters.get("SIZE");
            this.hiveX = size / 2.0;
            this.hiveY = size / 2.0;
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

    public void receiveFood(Leaf leaf) {
        System.out.println("Received " + leaf.getArea() + " units of food");
        synchronized (stream) {
            try {
                // TODO: send leaf to nest
                stream.writeObject(leaf);
                stream.flush();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
