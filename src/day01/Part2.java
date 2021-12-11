package day01;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Part2 {
    public static void main(String[] args) {
        int[] numbers = readInput();
        int largerThenPrevious = 0;

        for (int i = 0; i < numbers.length - 3; i++) {
            if (numbers[i] < numbers[i + 3]) largerThenPrevious++;
        }

        System.out.println(largerThenPrevious);
    }

    private static int[] readInput() {
        int[] inp = new int[2000];

        try (BufferedReader bf = new BufferedReader(new FileReader("src/day1/input.txt"))) {
            String line = bf.readLine();
            int index = 0;
            while (line != null) {
                inp[index] = Integer.parseInt(line);
                line = bf.readLine();
                index++;
            }

        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }

        return inp;
    }
}
