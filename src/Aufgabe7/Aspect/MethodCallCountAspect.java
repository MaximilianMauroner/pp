package Aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

import java.util.HashMap;

@Aspect
public class MethodCallCountAspect {
    private static final HashMap<String, Integer> elementCallCount = new HashMap<>();
    private static final HashMap<String, Integer> visitorCallCount = new HashMap<>();
    private static final HashMap<String, Integer> assignFormCallCount = new HashMap<>();

    /**
     * Counts the calls of all methods in the package Formicarium and Colony and saves them in a HashMap
     * The HashMap is structured as follows:
     * Key: Method name
     * Value: Number of calls
     */
    // Pre: joinPoint != null
    // Post: increments counter for element methods
    @Before("execution(public * Formicarium.*.*(..))")
    public void elementPatternCallCount(JoinPoint joinPoint) {
        String methodName = joinPoint.getStaticPart().getSignature() + joinPoint.getSignature().getName();
        elementCallCount.put(methodName, elementCallCount.getOrDefault(methodName, 0) + 1);
    }

    /**
     * Counts the calls of all methods in the package Colony and saves them in a HashMap
     * The HashMap is structured as follows:
     * Key: Method name
     * Value: Number of calls
     */
    // Pre: joinPoint != null
    // Post: increments counter for visitor methods
    @Before("execution(public * Colony.*.*(..))")
    public void visitorPatternCallCount(JoinPoint joinPoint) {
        String methodName = joinPoint.getStaticPart().getSignature() + joinPoint.getSignature().getName();
        visitorCallCount.put(methodName, visitorCallCount.getOrDefault(methodName, 0) + 1);
    }

    /**
     * Counts the calls of all methods in the package Institute and saves them in a HashMap
     * The HashMap is structured as follows:
     * Key: Method name
     * Value: Number of calls
     */
    // Pre: joinPoint != null
    // Post: increments counter for assignForm method
    @Before("execution(public * Institute.assignForm(..))")
    public void countMethodCallAssignForm(JoinPoint joinPoint) {
        String methodName = joinPoint.getStaticPart().getSignature() + joinPoint.getSignature().getName();
        assignFormCallCount.put(methodName, assignFormCallCount.getOrDefault(methodName, 0) + 1);
    }

    // Getter for the HashMaps

    // Pre:
    // Post: returns the HashMap of all calls of the element methods
    public static HashMap<String, Integer> getElementCallCount() {
        return elementCallCount;
    }

    // Pre:
    // Post: returns the HashMap of all calls of the assignForm methods
    public static HashMap<String, Integer> getAssignFormCallCount() {
        return assignFormCallCount;
    }

    // Pre:
    // Post: returns the HashMap of all calls of the visitor methods
    public static HashMap<String, Integer> getVisitorCallCount() {
        return visitorCallCount;
    }

    /**
     * Returns the number of calls of all visitor patterns
     *
     * @param className  Name of the class
     * @param methodName Name of the method
     * @return HashMap<String, Integer> of calls of the method
     */
    // Pre: className != null && methodName != null
    // Post: returns the HashMap of all calls of the visitor class and method
    public static HashMap<String, Integer> getVisitorCallCount(String className, String methodName) {
        HashMap<String, Integer> antCallCount = new HashMap<>();
        MethodCallCountAspect.getVisitorCallCount().entrySet().stream().filter(entry -> (entry.getKey()).contains(className) && entry.getKey().contains(methodName)).forEach(entry -> MethodCallCountAspect.visitorCallCount.put(entry.getKey(), entry.getValue()));
        return antCallCount;
    }

    /**
     * Returns the number of calls of all visitor patterns
     *
     * @param className  Name of the class
     * @param methodName Name of the method
     * @return HashMap<String, Integer> of calls of the method
     */
    // Pre: className != null && methodName != null
    // Post: returns the HashMap of all calls of the element class and method
    public static HashMap<String, Integer> getElementCallCount(String className, String methodName) {
        HashMap<String, Integer> methodCallCount = new HashMap<>();
        MethodCallCountAspect.getElementCallCount().entrySet().stream().filter(entry -> (entry.getKey()).contains(className) && entry.getKey().contains(methodName)).forEach(entry -> methodCallCount.put(entry.getKey(), entry.getValue()));
        return methodCallCount;
    }

    /**
     * Returns the number of calls of the .assignForm method
     *
     * @param className  Name of the class
     * @param methodName Name of the method
     * @return HashMap<String, Integer> of calls of the method
     */
    // Pre: className != null && methodName != null
    // Post: returns the HashMap of all calls of the assignForm class and method (only one)
    public static HashMap<String, Integer> getAssignFormCallCount(String className, String methodName) {
        HashMap<String, Integer> methodCallCount = new HashMap<>();
        MethodCallCountAspect.getAssignFormCallCount().entrySet().stream().filter(entry -> (entry.getKey()).contains(className) && entry.getKey().contains(methodName)).forEach(entry -> methodCallCount.put(entry.getKey(), entry.getValue()));
        return methodCallCount;
    }

    /**
     * Returns the number of calls of all methods in the package Formicarium and Colony as a readable String
     *
     * @return all calls in a readable String format
     */
    // Pre:
    // Post: returns all calls of the element and visitor methods as a readable String
    public static String exportAsString() {
        StringBuilder s = new StringBuilder();
        s.append(exportAsString(assignFormCallCount));
        s.append(exportAsString(elementCallCount));
        s.append(exportAsString(visitorCallCount));
        return s.toString();
    }

    // Pre: map != null
    // Post: returns all calls of the methods in the map as a readable String
    private static String exportAsString(HashMap<String, Integer> map) {
        StringBuilder sb = new StringBuilder();
        map.forEach((key, value) -> sb.append(key, key.indexOf(" ") + 1, key.lastIndexOf(")") + 1).append(": ").append(value).append("\n"));
        return sb.toString();
    }
}
