import Ant.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.List;

public class Arena {

    static Thread[] threads;
    static Ant[] ants;
    static Path path = Path.of("./test.out");
    static Process nestProcess;

    public static void main(String[] args) {
        try {
            String[] inputPattern = {"ANTS", "LEAFS", "WIDTH", "HEIGHT", "WAITSTEPS"};
            args = new String[]{"5", "10", "50", "40", "5"};

            Parameters params = Parameters.getInstance(args, inputPattern);
            params.set("LEAF_MIN_AREA", 1);
            params.set("LEAF_MAX_AREA", 10);
            params.bound("ANTS", 1, 100);
            params.bound("LEAFS", 1, 100);
            params.bound("WIDTH", 10, 80);
            params.bound("HEIGHT", 10, 80);


            System.out.println("ANTS: " + params.get("ANTS"));

            // start Nest Process
            ProcessBuilder builder = new ProcessBuilder("java", "Nest");
            builder.directory(new File("./out/production/pp/"));
            nestProcess = builder.start();

            Hive hive = new Hive(new ObjectOutputStream(nestProcess.getOutputStream()));

            Map map = new Map(params.get("WIDTH"), params.get("HEIGHT"), params.get("LEAFS"), hive);

            Arena.ants = new Ant[params.get("ANTS")];
            Arena.threads = new Thread[ants.length];

            for (int i = 0; i < ants.length; i++) {
                ants[i] = new Ant(map, 10, map.getPositions()[i][i], Direction.WEST, i == ants.length - 1);
                threads[i] = new Thread(ants[i]);

            }
//            map.print();

            for (int i = 0; i < ants.length; i++) {
                threads[i].start();
            }


        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void stop() {
        List<String> lines = Arrays.stream(ants).map(Ant::print).toList();

        try {
            Files.write(path, lines, StandardOpenOption.CREATE, StandardOpenOption.WRITE, StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Stopping ants: ");
        for (Thread thread : Arena.threads) {
            thread.interrupt();
        }
    }
}
