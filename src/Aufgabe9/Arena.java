import Ant.*;

import java.io.InputStream;
import java.io.ObjectOutputStream;

public class Arena {
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
            Process nestProcess = builder.start();

            Hive hive = new Hive(new ObjectOutputStream(nestProcess.getOutputStream()));

            Map map = new Map(params.get("WIDTH"), params.get("HEIGHT"), params.get("LEAFS"), hive);
//            map.print();

//            Runnable rs[] = new Runnable[20000];
//            for (int i = 0; i < rs.length; i++) {
//                final int finalI = i;
//                rs[i] = new Runnable() {
//                    @Override
//                    public void run() {
//                        Transaction t = new Transaction(map);
//                        t.setValueByID(1, 1, Integer.toString(finalI).charAt(0));
//                        t.setValueByID(2, 2, Integer.toString(finalI).charAt(0));
//                        char x  = t.getValueByID(3, 3);
//                        try {
//                            int sleepTimer =(int) (Math.random() * 100);
//                            Thread.sleep(sleepTimer);
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
//                        t.commit();
//                    }
//                };
//            }
//            for (int i = 0; i < rs.length; i++) {
//                rs[i].run();
//            }

            Ant[] ants =new Ant[params.get("ANTS")];
            Thread[] threads = new Thread[ants.length];

            for (int i = 0; i < ants.length; i++) {
                ants[i] = new Ant(map, 10, map.getPositions()[i][i], Direction.WEST,i == 0);
                threads[i] =  new Thread(ants[i]);

            }
            map.print();

            for (int i = 0; i < ants.length; i++) {
                threads[i].start();
            }



//            System.out.println("--------------------");
////            map.print();
//            System.out.println(a.getPosition());
//            for (int i = 0; i < 4; i++) {
//                System.out.println(a.getPosition());
//            }
//
//            System.out.println("--------------------");
//            map.print();


        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
