import java.util.AbstractMap;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.IntStream;

public class Parameters {
    private static Parameters instance = null; // client-side-history-constraint: call getInstance with args before using instance
    private final ConcurrentHashMap<String, Integer> parameters;

    // Pre: args.length == pattern.length
    // Post: creates a new Parameters object with the given args and pattern
    private Parameters(String[] args, String[] pattern) throws IllegalArgumentException {
        if (args.length != pattern.length) {
            throw new IllegalArgumentException("Invalid number of arguments. Expected " + pattern.length + " but got " + args.length + ". \n" +
                    "Usage: java Arena <ANTS> <LEAFS> <WIDTH> <HEIGHT> <MIN_WAIT> <MAX_WAIT>");
        }

        parameters = IntStream.range(0, pattern.length)
                .mapToObj(i -> new AbstractMap.SimpleEntry<>(pattern[i].toUpperCase(), Integer.valueOf(args[i])))
                .collect(ConcurrentHashMap::new, (m, v) -> m.put(v.getKey(), v.getValue()), ConcurrentHashMap::putAll);
    }

    // Pre: args.length == pattern.length
    // Post: returns a new Parameter Instance if not already created, otherwise returns the existing one
    public static Parameters getInstance(String[] args, String[] pattern) throws IllegalArgumentException {
        if (instance == null) {
            instance = new Parameters(args, pattern);
        }
        return instance;
    }

    // Pre: -
    // Post: returns the Parameter Instance if already created, otherwise returns null
    public static Parameters getInstance() {
        return instance;
    }

    // Pre: key != null
    // Post: returns the value of the given key
    public Integer get(String key) {
        return parameters.get(key.toUpperCase());
    }

    // Pre: key != null
    // Post: sets the value of the given key
    public void set(String key, Integer value) {
        parameters.put(key.toUpperCase(), value);
    }

    // Pre: key != null, min <= max
    // Post: bounds the value of the given key between min and max
    public void bound(String key, Integer min, Integer max) {
        if (parameters.get(key.toUpperCase()) < min) {
            parameters.put(key.toUpperCase(), min);
        } else if (parameters.get(key.toUpperCase()) > max) {
            parameters.put(key.toUpperCase(), max);
        }
    }

    @Override
    public String toString() {
        return parameters.toString();
    }

}
