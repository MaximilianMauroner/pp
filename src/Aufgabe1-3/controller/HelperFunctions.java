package controller;

import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

public class HelperFunctions {
    public static ConcurrentHashMap<Integer, Integer> ids = new ConcurrentHashMap<>();

    public static int generateRandomId() {
        int id = new Random().nextInt();
        while (ids.containsKey(id)) {
            id = new Random().nextInt();
        }
        ids.put(id, id);
        return id;
    }
}
