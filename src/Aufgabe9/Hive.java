import java.io.*;

public class Hive {
    private final double hiveX;
    private final double hiveY;
    private final ObjectOutputStream stream;

    // Pre: stream != null
    // Post: creates a new Hive object with the given stream
    public Hive(ObjectOutputStream stream) {
        this.stream = stream;

        Parameters parameters = Parameters.getInstance();
        int width = 0;
        int height = 0;

        try {
            width = parameters.get("WIDTH");
            height = parameters.get("HEIGHT");
        } catch(NullPointerException e) {
            System.out.println("Parameters not initialized. Using default values (not recommended)");
        } finally {
            if (width % 2 == 0) width++;
            if (height % 2 == 0) height++;

            this.hiveX = width / 2.0;
            this.hiveY = height / 2.0;
        }

//        if (parameters != null) {
//            int width = parameters.get("WIDTH");
//            int height = parameters.get("HEIGHT");
//
//            if (width % 2 == 0) width++;
//            if (height % 2 == 0) height++;
//
//            this.hiveX = width / 2.0;
//            this.hiveY = height / 2.0;
//        } else {
//            this.hiveX = 0;
//            this.hiveY = 0;
//        }
    }

    // Pre: -
    // Post: returns the x coordinate of the hive
    public double getX() {
        return hiveX;
    }

    // Pre: -
    // Post: returns the y coordinate of the hive
    public double getY() {
        return hiveY;
    }

    // Pre: leaf != null
    // Post: sends the leaf to the nest
    public synchronized void receiveFood(Leaf leaf) {
        System.out.println("Arena " + Arena.hashCode + ": Received " + leaf.getArea() + " units of food");
        try {
            // send leaf to nest
            stream.writeObject(leaf);
            stream.flush();
        } catch (IOException e) {
            System.out.println("Error sending leaf to nest: " + e.getMessage());
        }
    }

}
