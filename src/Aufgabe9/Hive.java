import java.io.*;

public class Hive {
    private final double hiveX;
    private final double hiveY;
    private final ObjectOutputStream stream;

    public Hive(ObjectOutputStream stream) {
        this.stream = stream;
        Parameters parameters = Parameters.getInstance();
        if (parameters != null) {
            int width = parameters.get("WIDTH");
            int height = parameters.get("HEIGHT");

            if (width % 2 == 0) width++;
            if (height % 2 == 0) height++;

            this.hiveX = width / 2.0;
            this.hiveY = height / 2.0;
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
        System.out.println("Arena " + Arena.hashCode + ": Received " + leaf.getArea() + " units of food");
        synchronized (stream) {
            try {
                // send leaf to nest
                stream.writeObject(leaf);
                stream.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
