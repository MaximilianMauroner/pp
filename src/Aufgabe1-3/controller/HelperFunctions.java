package controller;

import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

//BAD (object oriented): While this might be only a class for later extention. It is too small and its functinality might be better placed in the class that uses it.

/**
 * Class for helper functions
 * Contains functions that are used in multiple places throughout the program
 * <p>
 * Modularization Units:
 * - Module for helper functions
 * <p>
 * Abstraction: A representation of utility used in the simulation
 */
public class HelperFunctions {
    public static final ConcurrentHashMap<Integer, Integer> ids = new ConcurrentHashMap<>();

    public static int generateRandomId() {
        int id = new Random().nextInt();
        while (ids.containsKey(id)) {
            id = new Random().nextInt();
        }
        ids.put(id, id);
        return id;
    }
}
