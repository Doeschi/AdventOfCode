package day3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Part1 {

    public static void main(String[] args) {
        solvePartOne();
    }

    private static void solvePartOne() {
        ArrayList<String> inputNumbers = readInput();
        StringBuilder stringBuilder = new StringBuilder();

        // building the gamma rate
        for (int i = 0; i < 12; i++) {
            stringBuilder.append(findMostCommonAtIndex(inputNumbers, i));
        }

        String gammaRateStr = stringBuilder.toString();
        int gammaRate = Integer.parseInt(gammaRateStr, 2);

        // flipping the bits will result in the epsilon rate
        stringBuilder = new StringBuilder();
        for (int i = 0; i < gammaRateStr.length(); i++) {
            stringBuilder.append(gammaRateStr.charAt(i) == '0' ? '1' : '0');
        }

        String epsilonRateStr = stringBuilder.toString();
        int epsilonRate = Integer.parseInt(epsilonRateStr, 2);

        int powerConsumption = gammaRate * epsilonRate;
        System.out.println("Power consumption: " + powerConsumption);
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
