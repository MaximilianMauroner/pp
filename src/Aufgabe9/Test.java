import java.io.BufferedReader;

public class Test {
    public static void main(String[] args) {
        try {
            final String EXEC_ARGUMENT
                    = System.getProperty("java.home") +
                    java.io.File.separator +
                    "bin" +
                    java.io.File.separator +
                    "java" +
                    " " +
                    new java.io.File(".").getAbsolutePath() +
                    java.io.File.separator +
                    Arena.class;

            Process p = Runtime.getRuntime().exec(EXEC_ARGUMENT);
            p.waitFor();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
