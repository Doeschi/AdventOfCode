package day10;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Part1 {

    public static void main(String[] args) {
        ArrayList<String> input = loadInput();
        int syntaxErrorScore = 0;

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
                        syntaxErrorScore += getCharacterScore(currentChar);
                        continue outerLoop;
                    }

                }
            }
        }

        System.out.printf("syntax error score: %d", syntaxErrorScore);
    }

    private static int getCharacterScore(char c) {
        if (c == ')') return 3;
        else if (c == ']') return 57;
        else if (c == '}') return 1197;
        else return 25137;
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
