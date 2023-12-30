import java.util.AbstractMap;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.IntStream;

public class Parameters {
    private static Parameters instance = null;
    private final ConcurrentHashMap<String, Integer> parameters;

    private Parameters(String[] args, String[] pattern) {
        if (args.length != pattern.length) {
            throw new IllegalArgumentException("Invalid number of arguments");
        }
        parameters = IntStream.range(0, pattern.length)
                .mapToObj(i -> new AbstractMap.SimpleEntry<>(pattern[i].toUpperCase(), Integer.valueOf(args[i])))
                .collect(ConcurrentHashMap::new, (m, v) -> m.put(v.getKey(), v.getValue()), ConcurrentHashMap::putAll);
    }

    public static Parameters getInstance(String[] args, String[] pattern) {
        if (instance == null) {
            instance = new Parameters(args, pattern);
        }
        return instance;
    }

    public static Parameters getInstance() {
        return instance;
    }

    public Integer get(String key) {
        return parameters.get(key.toUpperCase());
    }

}