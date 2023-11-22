package day06;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Part2 {

    public static void main(String[] args) {
        ArrayList<Integer> fishTimers = loadInput();

        long[] fishTimersCounter = new long[9];

        for (int i : fishTimers) {
            fishTimersCounter[i]++;
        }

        for (int day = 0; day < 256; day++) {
            long numberOfNewFish = fishTimersCounter[0];

            fishTimersCounter[0] = fishTimersCounter[1];
            fishTimersCounter[1] = fishTimersCounter[2];
            fishTimersCounter[2] = fishTimersCounter[3];
            fishTimersCounter[3] = fishTimersCounter[4];
            fishTimersCounter[4] = fishTimersCounter[5];
            fishTimersCounter[5] = fishTimersCounter[6];
            fishTimersCounter[6] = fishTimersCounter[7] + numberOfNewFish;
            fishTimersCounter[7] = fishTimersCounter[8];
            fishTimersCounter[8] = numberOfNewFish;
        }

        long numberOfFish = 0;
        for (long l : fishTimersCounter) {
            numberOfFish += l;
        }

        System.out.printf("Number of fish after 256 day: %d", numberOfFish);


    }

    private static ArrayList<Integer> loadInput() {
        ArrayList<Integer> in = new ArrayList<>();

        try (BufferedReader bf = new BufferedReader(new FileReader("src/day6/input.txt"))) {
            String line = bf.readLine();
            String[] splittedLine = line.split(",");

            for (String s : splittedLine) {
                in.add(Integer.parseInt(s));
            }

        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }

        return in;
    }
}
