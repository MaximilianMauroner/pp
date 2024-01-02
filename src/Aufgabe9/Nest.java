import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public class Nest {
    public static void main(String[] args) {

        try {
            InputStream input = System.in;
            BufferedInputStream bufferedInput = new BufferedInputStream(input);
            ObjectInputStream stream = new ObjectInputStream(bufferedInput);

            List<Leaf> leafs = new ArrayList<>();
            Path path = Path.of("/Users/lessi/PP/pp/test.out");

            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                try {
                    stream.close();
                    bufferedInput.close();

                    // write leafs to file
                    List<String> lines = leafs.stream().map(Leaf::getArea).map(String::valueOf).toList();
                    Files.write(path, lines, StandardOpenOption.CREATE, StandardOpenOption.WRITE, StandardOpenOption.APPEND);

                    System.out.println("Nest stopped");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }));

            while (true) {
                try {
                    Leaf leaf = (Leaf) stream.readObject();
                    leafs.add(leaf);
                    System.out.println("Received " + leaf.getArea() + " units of food");
                } catch (ClassNotFoundException e) {
                    System.out.println("Class not found");
                } catch (IOException e) {
                    System.out.println("Stream closed");
                    break;
                }
            }

        } catch (IOException e) {
            System.out.println("Stream not available");
        }
    }
}
