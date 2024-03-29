package day05;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Part1 {

    private static final int FROM_X = 0;
    private static final int FROM_Y = 1;
    private static final int TO_X = 2;
    private static final int TO_Y = 3;

    public static void main(String[] args) {
        ArrayList<int[]> input = loadInput();
        int overlapCounter = 0;

        int[][] ventField = new int[1000][1000];

        for (int[] line : input) {
            int lowerXIndex = Math.min(line[FROM_X], line[TO_X]) - 1;
            int higherXIndex = Math.max(line[FROM_X], line[TO_X]);
            int lowerYIndex = Math.min(line[FROM_Y], line[TO_Y]) - 1;
            int higherYIndex = Math.max(line[FROM_Y], line[TO_Y]);

            for (int y = lowerYIndex; y < higherYIndex; y++) {
                for (int x = lowerXIndex; x < higherXIndex; x++) {
                    ventField[x][y]++;
                }
            }
        }

        for (int[] ventRow : ventField) {
            for (int ventCell : ventRow) {
                if (ventCell > 1) overlapCounter++;
            }
        }

        System.out.printf("Overlapping points: %d", overlapCounter);
    }

    private static ArrayList<int[]> loadInput() {
        ArrayList<int[]> in = new ArrayList<>();

        try (BufferedReader bf = new BufferedReader(new FileReader("src/day5/input.txt"))) {
            String line = bf.readLine();

            while (line != null) {
                String[] splittedLine = line.split(" -> ");

                int[] linePos = new int[4];
                int index = 0;

                for (String s : splittedLine) {
                    String[] splittedNumbers = s.split(",");

                    for (String number : splittedNumbers) {
                        linePos[index] = Integer.parseInt(number);
                        index++;
                    }
                }

                // only consider horizontal and vertical lines
                if (linePos[FROM_X] == linePos[TO_X] || linePos[FROM_Y] == linePos[TO_Y]) {
                    in.add(linePos);
                }

                line = bf.readLine();
            }

        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }

        return in;
    }
}
