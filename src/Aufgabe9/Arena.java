import Ant.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Stream;

public class Arena {

    private static final List<Thread> threadList = new ArrayList<>();
    private static final List<Ant> antList = new ArrayList<>();
    public static int hashCode = Runtime.getRuntime().hashCode();

    private static final ThreadGroup group = new ThreadGroup("Ants");

    private static final AtomicBoolean stopInvoked = new AtomicBoolean(false);

    public static void main(String[] args) {
        try {
            String[] inputPattern = {"ANTS", "LEAFS", "WIDTH", "HEIGHT", "MIN_WAIT", "MAX_WAIT"};

            Parameters params = Parameters.getInstance(args, inputPattern);
            params.set("LEAF_MIN_AREA", 1);
            params.set("LEAF_MAX_AREA", 10);
            params.set("WAITSTEPS", 64);
            params.bound("ANTS", 1, 100);
            params.bound("LEAFS", 1, 100);
            params.bound("WIDTH", 3, 80);
            params.bound("HEIGHT", 3, 80);
            params.bound("MIN_WAIT", 5, 10);
            params.bound("MAX_WAIT", 20, 50);

            System.out.println("Starting Arena " + hashCode + " with parameters: " + params);

            // start Nest Process

            ProcessBuilder builder = new ProcessBuilder("java", "Nest", Integer.toString(hashCode));
            builder.redirectError(new File("test.err"));
            //builder.directory(new File(Test.CURR_DIR + "/" + Test.OUTPUT_DIR));
            Process nestProcess = builder.start();

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
                    .parallel()
                    .forEach(position -> {
                        int x = position.getX();
                        int y = position.getY();

                        Ant ant = new Ant(map, hive, map.getPositions()[y][x], Direction.NORTH, antList.isEmpty());
                        antList.add(ant);
                        threadList.add(new Thread(group, ant, "Ant " + antList.size(), 16000));
                    });

            long startTime = System.currentTimeMillis();

            threadList.forEach(Thread::start);

            // god forgive me for I have sinned
            while (!stopInvoked.get()) {
                Thread.sleep(100);
            }

            System.out.println("Arena "+ hashCode + ": Stopping ants: ");
            group.interrupt();

            antList.forEach(a -> System.out.println("Arena " + hashCode + ": " + a.print()));

            long endTime = System.currentTimeMillis();
            System.out.println("Arena " + hashCode + ": All ants stopped in " + (endTime - startTime) + "ms");

            System.exit(group.activeCount() == 0 ? 0 : 1);

        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println("Arena " + hashCode + ": " + e.getMessage());
        }
    }

    public static void invokeStop() {
        System.out.println("Arena " + hashCode + ": Stop invoked");
        Arena.stopInvoked.set(true);
    }
}
