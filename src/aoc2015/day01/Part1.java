package aoc2015.day01;

import utils.InputReader;
import utils.Timer;

public class Part1 {

    public static void main(String[] args) {
        String input = InputReader.readInputString("src/aoc2015/day01/input.txt");
        Timer.startTimer();

        int floor = 0;

        for (int i = 0; i < input.length(); i++){
            if (input.charAt(i) == '(')
                floor++;
            else
                floor--;
        }

        System.out.printf("floor: %d%n", floor);
        Timer.endAndPrintTimer();
    }
}
