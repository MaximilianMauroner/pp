package controller;

import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Class for helper functions
 * Contains functions that are used in multiple places throughout the program
 *
 * Modularization Units:
 * - Module for helper functions
 *
 * Abstraction: A representation of utility used in the simulation
 */
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
