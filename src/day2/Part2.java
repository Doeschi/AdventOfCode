package day2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Part2 {

    public static void main(String[] args) {
        ArrayList<String> input = loadInput();

        int pos = 0;
        int depth = 0;
        int aim = 0;

        for (String s : input) {
            int num = Integer.parseInt(Character.toString(s.charAt(s.length() - 1)));

            if (s.startsWith("forward")) {
                pos += num;
                depth += aim * num;
            }
            else if (s.startsWith("down")) {
                aim += num;
            }
            else aim -= num;
        }

        int result = pos * depth;
        System.out.printf("Result: %s", result);
    }

    private static ArrayList<String> loadInput() {
        ArrayList<String> in = new ArrayList<>();

        try (BufferedReader bf = new BufferedReader(new FileReader("src/day2/input.txt"))) {

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
