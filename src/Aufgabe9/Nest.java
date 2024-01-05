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
            if (args.length != 1) {
                throw new IllegalArgumentException("Invalid number of arguments");
            }

            Path path = Path.of("test.out");

            int hashCode = Integer.parseInt(args[0]);
            InputStream input = System.in;
            BufferedInputStream bufferedInput = new BufferedInputStream(input);
            ObjectInputStream stream = new ObjectInputStream(bufferedInput);

            List<Leaf> leafs = new ArrayList<>();

            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                try {
                    stream.close();
                    bufferedInput.close();

                    // write leafs to file
                    List<String> lines = leafs.stream()
                            .map(l -> "Nest of " + hashCode + ": " + l).toList();
                    Files.write(path, lines, StandardOpenOption.CREATE, StandardOpenOption.WRITE, StandardOpenOption.APPEND);
                } catch (IOException e) {
                    System.out.println("Stream not available");
                }
            }));

            while (true) {
                try {
                    Leaf leaf = (Leaf) stream.readObject();
                    leafs.add(leaf);
                    System.out.println("Nest of " + hashCode + ": Received " + leaf.getArea() + " units of food");
                } catch (ClassNotFoundException e) {
                    System.out.println("Class not found");
                } catch (IOException e) {
                    System.out.println("Stream closed: " + e.getMessage());
                    break;
                }
            }

        } catch (IOException e) {
            System.out.println("Stream not available");
        }
    }
}
