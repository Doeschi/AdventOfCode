package day03;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Part2 {
    public static void main(String[] args) {
        solvePartTwo();
    }

    private static void solvePartTwo() {
        ArrayList<String> oxygenNumbers = readInput();
        ArrayList<String> co02Numbers = new ArrayList<>(oxygenNumbers);

        // oxygen generator rating
        for (int i = 0; i < 12; i++) {
            if (oxygenNumbers.size() > 1) {
                char mostCommon = findMostCommonAtIndex(oxygenNumbers, i);
                oxygenNumbers.removeAll(getNumbersWithDifferentBit(oxygenNumbers, mostCommon, i));
            }

            if (co02Numbers.size() > 1) {
                char leastCommon = findLeastCommonAtIndex(co02Numbers, i);
                co02Numbers.removeAll(getNumbersWithDifferentBit(co02Numbers, leastCommon, i));
            }

            if (oxygenNumbers.size() == 1 && co02Numbers.size() == 1)
                break;

        }

        // oxygen generator rating
        String generatorRatingStr = oxygenNumbers.get(0);
        int generatorRating = Integer.parseInt(generatorRatingStr, 2);

        // CO02 scrubber rating
        String scrubberRatingStr = co02Numbers.get(0);
        int scrubberRating = Integer.parseInt(scrubberRatingStr, 2);

        int lifeSupportRating = generatorRating * scrubberRating;
        System.out.println("Life support rating: " + lifeSupportRating);
    }

    private static ArrayList<String> getNumbersWithDifferentBit(ArrayList<String> n, char c, int i) {
        ArrayList<String> numbers = new ArrayList<>();

        for (String s : n) {
            if (s.charAt(i) != c) numbers.add(s);
        }

        return numbers;
    }

    private static char findMostCommonAtIndex(ArrayList<String> list, int index) {
        int zeroCounter = 0;
        for (String number : list) {
            if (number.charAt(index) == '0') {
                zeroCounter++;
            }
        }

        return zeroCounter > list.size() / 2 ? '0' : '1';
    }

    private static char findLeastCommonAtIndex(ArrayList<String> list, int index) {
        int zeroCounter = 0;
        for (String number : list) {
            if (number.charAt(index) == '0') {
                zeroCounter++;
            }
        }

        return zeroCounter <= list.size() / 2 ? '0' : '1';
    }

    private static ArrayList<String> readInput() {
        ArrayList<String> input = new ArrayList<>();
        try (BufferedReader bf = new BufferedReader(new FileReader("src/day3/input.txt"))) {
            String line = bf.readLine();

            while (line != null) {
                input.add(line);
                line = bf.readLine();
            }

        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }

        return input;
    }
}
