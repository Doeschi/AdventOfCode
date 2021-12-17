package aoc2015.day01;

import utils.InputReader;

public class Part2 {

    public static void main(String[] args) {
        String input = InputReader.readInputString("src/aoc2015/day01/input.txt");

        int floor = 0;
        int instructionPos = 0;

        for (int i = 0; i < input.length(); i++) {
            if (input.charAt(i) == '(')
                floor++;
            else
                floor--;

            if (floor == -1){
                instructionPos = i + 1;
                break;
            }
        }

        System.out.printf("instructionPos: %d%n", instructionPos);
    }
}
