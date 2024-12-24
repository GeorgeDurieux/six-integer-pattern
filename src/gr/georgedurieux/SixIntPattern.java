package gr.georgedurieux;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.TreeSet;

public class SixIntPattern {

    private static final int COMBINATION_SIZE = 6;
    private static final int MAX_EVENS = 4;
    private static final int MAX_ODDS = 4;
    private static final int MAX_CONSECUTIVE = 2;
    private static final int MAX_SAME_FINALS = 3;
    private static final int MAX_SAME_DECADE = 3;

    public static void main(String[] args) {
        int[] sortedArray = getSortedUniqueArray();
        System.out.println("Sorted array: " + Arrays.toString(sortedArray));

        List<int[]> patterns = generateCombinations(sortedArray, COMBINATION_SIZE);
        System.out.println("Generated patterns of " + COMBINATION_SIZE + " numbers:");
        for (int[] pattern : patterns) {
            System.out.println(Arrays.toString(pattern));
        }
    }

    public static int[] getSortedUniqueArray() {
        Scanner scanner = new Scanner(System.in);
        TreeSet<Integer> uniqueNumbers = new TreeSet<>();

        System.out.println("Enter at least 7 and no more than 49 unique integers between 1 and 49.");
        System.out.println("Type 'done' to finish after entering enough numbers.");

        while (uniqueNumbers.size() < 49) {
            System.out.print("Enter a number (or 'done' if finished): ");
            String input = scanner.next();

            if (input.equalsIgnoreCase("done")) {
                if (uniqueNumbers.size() < 7) {
                    System.out.println("You must enter at least 7 numbers. Keep going!");
                } else {
                    break;
                }
            } else {
                try {
                    int num = Integer.parseInt(input);

                    if (num < 1 || num > 49) {
                        System.out.println("Number out of range! Please enter a number between 1 and 49.");
                    } else if (!uniqueNumbers.add(num)) {
                        System.out.println("Number already entered! Please enter a unique number.");
                    } else {
                        System.out.println("Number accepted.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input! Please enter an integer or 'done'.");
                }
            }
        }

        return uniqueNumbers.stream().mapToInt(Integer::intValue).toArray();
    }

    public static List<int[]> generateCombinations(int[] inputArray, int combinationSize) {
        List<int[]> results = new ArrayList<>();
        int[] currentCombination = new int[combinationSize];

        generateCombinationsRecursive(inputArray, combinationSize, 0, 0, currentCombination, results);

        results.removeIf(result -> checkMaxEvens(result, MAX_EVENS) | checkMaxOdds(result, MAX_ODDS) | checkMaxConsecutive(result, MAX_CONSECUTIVE) | checkMaxOccurrences(findFinals(result), MAX_SAME_FINALS) | checkMaxOccurrences(findDecades(result), MAX_SAME_DECADE));

        return results;
    }

    private static void generateCombinationsRecursive(int[] inputArray, int combinationSize, int startIndex, int currentIndex, int[] currentCombination, List<int[]> results) {
        if (currentIndex == combinationSize) {
            results.add(currentCombination.clone());
            return;
        }

        for (int i = startIndex; i < inputArray.length; i++) {
            currentCombination[currentIndex] = inputArray[i];
            generateCombinationsRecursive(inputArray, combinationSize, i + 1, currentIndex + 1, currentCombination, results);
        }
    }

    public static boolean checkMaxEvens(int[] arr, int maxEvens) {
        int evensCount = 0;
        for (int i : arr) {
            if (i % 2 == 0) {
                evensCount++;
            }
        }
        return (evensCount > maxEvens);
    }

    public static boolean checkMaxOdds(int[] arr, int maxOdds) {
        int oddsCount = 0;
        for (int i : arr) {
            if (i % 2 != 0) {
                oddsCount++;
            }
        }
        return (oddsCount > maxOdds);
    }

    public static boolean checkMaxConsecutive(int[] arr, int maxConsecutive) {
        int consecutiveCount = 0;
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] == arr[i - 1] + 1) {
                consecutiveCount++;
            }
        }
        return (consecutiveCount > maxConsecutive);
    }

    public static int[] findFinals(int[] arr) {
        int[] finals = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            finals[i] = arr[i] % 10;
        }
        return finals;
    }

    public static int[] findDecades(int[] arr) {
        int[] decades = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            decades[i] = arr[i] / 10;
        }
        return decades;
    }

    public static boolean checkMaxOccurrences(int[] arr, int max) {
        int[] count = new int[10];

        for (int num : arr) {
            count[num]++;
            if (count[num] > max) {
                return true;
            }
        }
        return false;
    }
}
