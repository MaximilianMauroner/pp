import java.io.*;
import java.nio.file.Path;

public class Test {
    public static final String OUTPUT_DIR = "out/production/pp/";
    public static final Path CURR_DIR = Path.of("").toAbsolutePath();
    public static final Boolean DEBUG = false;


    public static void main(String[] args) {
        try {
            new FileWriter(CURR_DIR + "/" + OUTPUT_DIR + "/test.out", false).close();
        } catch (IOException e) {
            System.out.println("Error creating test.out: " + e.getMessage());
        }

        if (DEBUG) {
            startTest(5, 10, 50, 40, 5, 50);
        } else {
            startTest(25, 30, 15, 15, 5, 50);
            startTest(5, 10, 10, 10, 5, 50);
            startTest(5, 10, 50, 40, 5, 10);
        }
    }

    // Pre: ants >= 1, leafs >= 1, width >= 10, height >= 10, min_wait >= 5, max_wait >= 5
    // Post: starts a new Arena process with the given parameters
    public static void startTest(int ants, int leafs, int width, int height, int min_wait, int max_wait) {
        long startTime = System.currentTimeMillis();

        try {
            ProcessBuilder builder = new ProcessBuilder("java", "Arena",
                    Integer.toString(ants), Integer.toString(leafs),
                    Integer.toString(width), Integer.toString(height),
                    Integer.toString(min_wait), Integer.toString(max_wait));

            builder.directory(new File(CURR_DIR + "/" + OUTPUT_DIR));
            if (!DEBUG) {
                builder.redirectError(ProcessBuilder.Redirect.appendTo(new File(CURR_DIR + "/" + OUTPUT_DIR + "/test.err")));
                builder.redirectOutput(ProcessBuilder.Redirect.appendTo(new File(CURR_DIR + "/" + OUTPUT_DIR + "/test.out")));
            }
            Process p = builder.start();

            if (DEBUG) {
                p.getInputStream().transferTo(System.out);
                p.getErrorStream().transferTo(System.err);
            }

            p.waitFor();

        } catch (IOException e) {
            System.out.println("Stream not available: " + e.getMessage());
        } catch (InterruptedException e) {
            System.out.println("Arena process interrupted: " + e.getMessage());
        }

        long endTime = System.currentTimeMillis();
        System.out.println("Test finished in " + (endTime - startTime) + "ms");
    }
}
