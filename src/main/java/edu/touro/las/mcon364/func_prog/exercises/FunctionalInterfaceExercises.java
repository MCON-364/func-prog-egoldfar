package edu.touro.las.mcon364.func_prog.exercises;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * Functional Interface Practice
 *
 * In this assignment you will:
 *  - Create and return different functional interfaces
 *  - Apply them
 *  - Practice chaining where appropriate
 *
 * IMPORTANT:
 *  - Use lambdas
 *  - Do NOT use anonymous classes
 */
public class FunctionalInterfaceExercises {

    // =========================================================
    // PART 1 — SUPPLIERS
    // =========================================================

    /**
     * 1) Create a Supplier that returns the current year.
     *
     * Hint:
     * You can get the current date using:
     *     LocalDate.now()
     *
     * Then extract the year using:
     *     getYear()
     *
     * Example (not the solution):
     *
     */
    public static Supplier<Integer> currentYearSupplier() {
        return () -> LocalDate.now().getYear();
    }

    /**
     * 2) Create a Supplier that generates a random number
     * between 1 and 100.
     */
    public static Supplier<Integer> randomScoreSupplier() {
        Random random = new Random();
        return () -> random.nextInt(1, 101);
    }

    // =========================================================
    // PART 2 — PREDICATES
    // =========================================================

    /**
     * 3) Create a Predicate that checks whether
     * a string is all uppercase.
     */
    public static Predicate<String> isAllUpperCase() {
        return string -> {
            if (string == null || string.isEmpty())
                return false;
            return string.equals(string.toUpperCase());
        };
    }

    /**
     * 4) Create a Predicate that checks whether
     * a number is positive AND divisible by 5.
     *
     * Hint: consider chaining.
     */
    public static Predicate<Integer> positiveAndDivisibleByFive() {
        Predicate<Integer> isPositive = num -> num > 0;
        Predicate<Integer> divisibleByFive = num -> num % 5 == 0;
        return isPositive.and(divisibleByFive);
    }

    // =========================================================
    // PART 3 — FUNCTIONS
    // =========================================================

    /**
     * 5) Create a Function that converts
     * a temperature in Celsius to Fahrenheit.
     *
     * Formula: F = C * 9/5 + 32
     */
    public static Function<Double, Double> celsiusToFahrenheit() {

        return celsius -> celsius * 1.8 + 32;
    }

    /**
     * 6) Create a Function that takes a String
     * and returns the number of vowels in it.
     *
     * Bonus: Make it case-insensitive.
     */
    public static Function<String, Integer> countVowels() {
        return string -> {
            if (string == null || string.isEmpty())
                return 0;
            int vowels =0;
            for (char c : string.toUpperCase().toCharArray()) {
                switch (c) {
                    case 'A':
                    case 'E':
                    case 'I':
                    case 'O':
                    case 'U':
                        vowels++;
                }
            }
            return vowels;
        };
    }

    // =========================================================
    // PART 4 — CONSUMERS
    // =========================================================

    /**
     * 7) Create a Consumer that prints a value
     * surrounded by "***"
     *
     * Example output:
     * *** Hello ***
     */
    public static Consumer<String> starPrinter() {
        return string -> System.out.println("*** " + string + " ***");
    }

    /**
     * 8) Create a Consumer that prints the square
     * of an integer.
     */
    public static Consumer<Integer> printSquare() {
        return num -> System.out.println(num * num);
    }

    // =========================================================
    // PART 5 — APPLYING FUNCTIONAL INTERFACES
    // =========================================================

    /**
     * 9) Apply:
     *  - A Predicate
     *  - A Function
     *  - A Consumer
     *
     * Process the list as follows:
     *  - Keep only strings longer than 3 characters
     *  - Convert them to lowercase
     *  - Print them
     */
    public static void processStrings(List<String> values) {
        Predicate<String> longerThanThree = string -> string != null && string.length() > 3;
        Function<List<String>, List<String>> toLower = stringList -> {
            if(stringList == null || stringList.isEmpty())
                return Collections.emptyList();
            List<String> list = new ArrayList<>();
            for (String s : stringList) {
                list.add(s.toLowerCase());
            }
            return list;
        };
        Consumer<List<String>> printStrings = stringList -> {
            for (String s : stringList) {
                System.out.println(s);
            }
        };

        List<String> filtered = values.stream().filter(longerThanThree).collect(Collectors.toList());

        printStrings.accept(toLower.apply(filtered));

    }

    /**
     * 10) Apply:
     *  - A Supplier
     *  - A Predicate
     *  - A Consumer
     *
     * Generate 5 random scores.
     * Print only those above 70.
     */
    public static void generateAndFilterScores() {
        Supplier<List<Integer>> getScores = () -> {
            Random random = new Random();
            List<Integer> scores = new ArrayList<>();
            for (int i = 0; i < 5; i++)
                scores.add(random.nextInt(1, 101));
            return scores;
        };
        Predicate<Integer> aboveSeventy = num -> num > 70;
        Consumer<List<Integer>> printScores = scores -> {
            for (int s : scores) {
                System.out.println(s);
            }
        };

        List<Integer> gradesAboveSeventy = getScores.get().stream().filter(aboveSeventy).toList();
        printScores.accept(gradesAboveSeventy);

    }
}
