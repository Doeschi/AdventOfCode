package aoc2021.day13;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Part1 {

    private static ArrayList<int[]> dots;
    private static ArrayList<String> instruction;

    public static void main(String[] args) {
        loadInput();

        for (String s : instruction) {
            String[] splitted = s.split("=");
            int linePos = Integer.parseInt(splitted[1]);

            for (int[] dot : dots) {
                int posIndex = splitted[0].equals("x") ? 0 : 1;

                if (dot[posIndex] > linePos) {
                    int newPos = linePos - (dot[posIndex] - linePos);
                    dot[posIndex] = newPos;
                }
            }

            outerLoop:
            for (int i = 0; i < dots.size(); i++) {
                int[] currentDot = dots.get(i);

                for (int j = i + 1; j < dots.size(); j++) {
                    int[] otherDot = dots.get(j);

                    if (currentDot[0] == otherDot[0] && currentDot[1] == otherDot[1]) {
                        dots.remove(i);
                        i--;
                        continue outerLoop;
                    }
                }
            }
            break;
        }

        System.out.printf("number of visible dots: %d", dots.size());

    }

    private static void loadInput() {
        dots = new ArrayList<>();
        instruction = new ArrayList<>();

        try (BufferedReader bf = new BufferedReader(new FileReader("src/aoc2021.day13/input.txt"))) {
            String line = bf.readLine();

            while (!line.equals("")) {
                String[] splitted = line.split(",");

                int[] dot = new int[2];
                dot[0] = Integer.parseInt(splitted[0]);
                dot[1] = Integer.parseInt(splitted[1]);

                dots.add(dot);
                line = bf.readLine();
            }

            line = bf.readLine();

            while (line != null) {
                String[] splitted = line.split(" ");
                instruction.add(splitted[2]);
                line = bf.readLine();
            }

        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }
}
