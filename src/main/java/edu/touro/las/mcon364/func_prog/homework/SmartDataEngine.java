package edu.touro.las.mcon364.func_prog.homework;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.function.*;

/**
 * ============================================================
 *  Smart Data Processing Engine
 * ============================================================
 *
 * Goal:
 *  - Build a reusable functional pipeline engine
 *  - Use Consumer, Supplier, Predicate, Function
 *  - Use Optional
 *  - Use chaining
 *  - Use switch expression
 *
 * Estimated time: ~2 hours
 *
 * Rules:
 *  - Use lambdas (no anonymous classes)
 *  - Use Optional where requested
 *  - Use functional interface chaining where appropriate
 */
public class SmartDataEngine {

    // ============================================================
    // PART 1 — GENERIC PIPELINE ENGINE
    // ============================================================

    /**
     * Implement a generic pipeline.
     *
     * Behavior:
     *  1. Filter using Predicate
     *  2. Transform using Function
     *  3. Pass result to Consumer
     */
    public static <T, R> void pipeline(
            List<T> input,
            Predicate<T> filter,
            Function<T, R> mapper,
            Consumer<R> consumer
    ) {
        input.stream().filter(filter).map(mapper).forEach(consumer);
    }

    // ============================================================
    // PART 2 — OPTIONAL PROCESSING
    // ============================================================

    /**
     * Implement a safe divide method.
     *
     * - If denominator is 0 → return Optional.empty()
     * - Otherwise return Optional.of(result)
     */
    public static Optional<Double> safeDivide(double a, double b) {
        if (b == 0) {
            return Optional.empty();
        }
        return Optional.of(a / b);
    }

    /**
     * Use Optional chaining:
     *
     *  - Divide two numbers using safeDivide(...)
     *  - If a value is present, multiply the result by 10
     *  - If empty, return -1.0
     *
     * Reminder:
     *  - Optional.map(...) transforms the value ONLY if it is present.
     *  - If the Optional is empty, map() does nothing and the empty
     *    Optional continues down the chain.
     *  - Use orElse(...) to provide a default value when empty.
     */
    public static double processDivision(double a, double b) {
        return safeDivide(a, b).map(num -> num * 10).orElse(-1.0);
    }

    // ============================================================
    // PART 3 — DATA TYPE ROUTER (Pattern Matching Switch)
    // ============================================================

    /**
     * Use switch expression with pattern matching.
     *
     * Behavior:
     *  - If Integer → return square
     *  - If String → return uppercase
     *  - If Double → return rounded value
     *  - Otherwise → return "Unsupported"
     *
     * Must use switch expression (arrow syntax).
     */
    public static Object transformObject(Object input) {

        // Example structure (not solution):

        // return switch (input) {
        //     case Integer i -> ...
        //     case String s  -> ...
        //     case Double d  -> ...
        //     default -> ...
        // };

        return switch (input) {
            case Integer i -> i*i;
            case String s -> s.toUpperCase();
            case Double d -> Math.round(d);
            default -> "Unsupported";
        };
    }

    // ============================================================
    // PART 4 — FUNCTION CHAINING
    // ============================================================

    /**
     * Create and return a Function<String, Integer>
     * that performs the following transformations in order:
     *
     *   1. Trim leading and trailing whitespace
     *   2. Convert the string to lowercase
     *   3. Return the length of the final string
     *
     * Guidance:
     *   - You should NOT write one large lambda block.
     *   - Instead, create smaller Function variables
     *     and combine them using function chaining.
     *
     *   - Use:
     *         andThen(...)  → left function runs first
     *         compose(...)  → right function runs first
     *
     *   - Think carefully about types:
     *       trim:        String → String
     *       toLowerCase: String → String
     *       length:      String → Integer
     *
     *   - The final returned function must be:
     *       Function<String, Integer>
     *
     * This exercise reinforces how data flows through a functional pipeline.
     */

    public static Function<String, Integer> buildStringLengthPipeline() {
        Function<String, String> trim = String::trim;
        Function<String, String> toLowerCase = String::toLowerCase;
        Function<String, Integer> stringLengthFunction = String::length;

        return s -> trim.andThen(toLowerCase).andThen(stringLengthFunction).apply(s);
    }

    // ============================================================
    // PART 5 — MINI APPLICATION
    // ============================================================

   /**
     * Implement this method using ALL four functional interfaces:
     *
     *  - Supplier  → generate random integers
     *  - Predicate → filter numbers > 50
     *  - Function  → convert Integer → "Score: X"
     *  - Consumer  → print the final result
     *
     * Required Behavior:
     *  1. Generate 10 random integers between 1 and 100.
     *     (Call supplier.get() multiple times.)
     *  2. Keep only numbers greater than 50.
     *  3. Convert each remaining number into a formatted string.
     *  4. Print each formatted string.
     *
     * Important Guidance:
     *  - Do NOT use Streams.
     *  - Do NOT hardcode logic directly inside the loop.
     *  - First define the functional interfaces.
     *  - Then generate a List<Integer>.
     *  - Then pass everything into your pipeline() method.
     *
     * Think in terms of behavior injection:
     *  - The pipeline should NOT know how random numbers are created.
     *  - The pipeline should NOT know how formatting works.
     *
     * It should only orchestrate the behavior passed to it.
     *
     * This method integrates everything you learned in this project.
     */

    public static void runScoreProcessor() {
        Supplier<Integer> getNum = () -> {
                Random random = new Random();
                return random.nextInt(1, 101);
        };
        Predicate<Integer> greaterThanFifty = i -> i > 50;
        Function<Integer, String> scoreToString = i -> String.format("Score: %d", i);
        Consumer<String> printString = System.out::println;

        List<Integer> scores = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            scores.add(getNum.get());
        }
        pipeline(scores, greaterThanFifty, scoreToString, printString);
    }

}
