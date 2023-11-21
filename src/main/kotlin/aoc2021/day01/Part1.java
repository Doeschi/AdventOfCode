package aoc2021.day01;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Part1 {

    public static void main(String[] args) {
        int[] numbers = readInput();
        int largerThenPrevious = 0;

        for(int i = 1; i < numbers.length; i++){
            if (numbers[i - 1] < numbers[i]) largerThenPrevious++;
        }

        System.out.println(largerThenPrevious);
    }

    private static int[] readInput() {
        int[] inp = new int[2000];

        try (BufferedReader bf = new BufferedReader(new FileReader("src/day1/input.txt"))) {
            String line = bf.readLine();
            int index = 0;
            while(line != null){
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
