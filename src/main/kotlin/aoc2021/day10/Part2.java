package aoc2021.day10;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class Part2 {

    public static void main(String[] args) {
        ArrayList<String> input = loadInput();
        ArrayList<Long> scores = new ArrayList<>();

        StringBuilder expectedClosingChars = new StringBuilder();

        outerLoop:
        for (String syntaxLine : input) {
            expectedClosingChars.delete(0, expectedClosingChars.length());

            for (int i = 0; i < syntaxLine.length(); i++) {
                char currentChar = syntaxLine.charAt(i);

                if (currentChar == '(')
                    expectedClosingChars.insert(0, ')');

                else if (currentChar == '[')
                    expectedClosingChars.insert(0, ']');

                else if (currentChar == '{')
                    expectedClosingChars.insert(0, '}');

                else if (currentChar == '<')
                    expectedClosingChars.insert(0, '>');


                else {
                    if (currentChar == expectedClosingChars.charAt(0))
                        expectedClosingChars.deleteCharAt(0);
                    else {
                        continue outerLoop;
                    }
                }
            }

            long score = 0;

            for(int i = 0; i < expectedClosingChars.length(); i++){
                score *= 5;
                score += getCharacterScore(expectedClosingChars.charAt(i));
            }

            scores.add(score);
        }

        Collections.sort(scores);
        long middleScore = scores.get((scores.size() / 2));

        System.out.printf("middle score: %d", middleScore);
    }

    private static int getCharacterScore(char c) {
        if (c == ')') return 1;
        else if (c == ']') return 2;
        else if (c == '}') return 3;
        else return 4;
    }

    public static ArrayList<String> loadInput() {
        ArrayList<String> in = new ArrayList<>();

        try (BufferedReader bf = new BufferedReader(new FileReader("src/aoc2021.day10/input.txt"))) {
            String line = bf.readLine();

            while (line != null) {
                in.add(line);
                line = bf.readLine();
            }

        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }

        return in;
    }
}
