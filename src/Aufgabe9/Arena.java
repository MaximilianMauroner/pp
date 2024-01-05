import Ant.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

public class Arena {

    static List<Thread> threadList = new ArrayList<>();
    static List<Ant> antList = new ArrayList<>();
    static Process nestProcess;
    static int hashCode = Runtime.getRuntime().hashCode();

    static ThreadGroup group = new ThreadGroup("Ants");

    public static void main(String[] args) {
        try {
            String[] inputPattern = {"ANTS", "LEAFS", "WIDTH", "HEIGHT", "MIN_WAIT", "MAX_WAIT"};

            Parameters params = Parameters.getInstance(args, inputPattern);
            params.set("LEAF_MIN_AREA", 1);
            params.set("LEAF_MAX_AREA", 10);
            params.set("WAITSTEPS", 64);
            params.bound("ANTS", 1, 100);
            params.bound("LEAFS", 1, 100);
            params.bound("WIDTH", 10, 80);
            params.bound("HEIGHT", 10, 80);
            params.bound("MIN_WAIT", 5, 10);
            params.bound("MAX_WAIT", 20, 50);

            System.out.println("Starting Arena " + hashCode + " with parameters: " + params);

            // start Nest Process

            ProcessBuilder builder = new ProcessBuilder("java", "Nest", Integer.toString(hashCode));
            builder.redirectError(new File("test.err"));
            //builder.directory(new File(Test.CURR_DIR + "/" + Test.OUTPUT_DIR));
            nestProcess = builder.start();

            Hive hive = new Hive(new ObjectOutputStream(nestProcess.getOutputStream()));

            Map map = new Map(params.get("WIDTH"), params.get("HEIGHT"), params.get("LEAFS"), hive);

            Stream.generate(Position::new)
                    .filter(position -> {
                        // make sure that the tail position is not on the border
                        if (position.getY() == params.get("HEIGHT") - 1)
                            return false;

                        char h = map.getPositions()[position.getY()][position.getX()].getType();
                        char t = map.getPositions()[position.getY() + 1][position.getX()].getType();
                        return h == ' ' && t == ' ';
                    }).limit(params.get("ANTS"))
                    .forEach(position -> {
                        int x = position.getX();
                        int y = position.getY();

                        Ant ant = new Ant(map, hive, map.getPositions()[y][x], Direction.NORTH, antList.isEmpty());
                        antList.add(ant);
                        threadList.add(new Thread(group, ant, "Ant " + antList.size(), 16000));
                    });

            threadList.forEach(Thread::start);

        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void stop() {
//        List<String> lines = Arrays.stream(ants).map(Ant::print).toList();
//
//        try {
//            Files.write(path, lines, StandardOpenOption.CREATE, StandardOpenOption.WRITE, StandardOpenOption.APPEND);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        System.out.println("Arena "+ hashCode + ": Stopping ants: ");
        group.interrupt();

        antList.forEach(a -> System.out.println("Arena " + hashCode + ": " + a.print()));

    }
}
