package day02;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Part1 {

    public static void main(String[] args) {
        ArrayList<String> input = loadInput();

        int pos = 0;
        int depth = 0;

        for(String s : input){
            int num = Integer.parseInt(Character.toString(s.charAt(s.length() - 1)));
            if (s.startsWith("forward")) pos += num;
            else if (s.startsWith("down")) depth += num;
            else depth -= num;
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
