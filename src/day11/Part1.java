package day11;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Part1 {

    public static void main(String[] args) {
        int[][] octopusField = loadInput();

        int flashCounter = 0;

        for (int step = 0; step < 100; step++) {

            // increase every octopus's energy by 1
            for (int y = 0; y < octopusField.length; y++) {
                for (int x = 0; x < octopusField[y].length; x++) {
                    octopusField[y][x]++;
                }
            }

            boolean octopusHasFlashed;

            do {
                octopusHasFlashed = false;
                for (int y = 0; y < octopusField.length; y++) {
                    for (int x = 0; x < octopusField[y].length; x++) {
                        if (octopusField[y][x] > 9) {
                            // set the octopus's energy to -1 to mark it as "flashed" for this step
                            octopusField[y][x] = -1;

                            // increase the energy of every adjacent octopus
                            for (int yOff = y - 1; yOff < y + 2; yOff++) {
                                for (int xOff = x - 1; xOff < x + 2; xOff++) {
                                    if (x == xOff && y == yOff) continue;

                                    try {
                                        if (octopusField[yOff][xOff] != -1) {
                                            octopusField[yOff][xOff]++;
                                            octopusHasFlashed = true;
                                        }
                                    } catch (ArrayIndexOutOfBoundsException e) {
                                        // to nothing
                                    }
                                }
                            }
                        }
                    }
                }
            } while (octopusHasFlashed);

            // count how many octopus's have flashed and reset the energy
            for (int y = 0; y < octopusField.length; y++) {
                for (int x = 0; x < octopusField[y].length; x++) {
                    if (octopusField[y][x] == -1) {
                        octopusField[y][x] = 0;
                        flashCounter++;
                    }
                }
            }

        }

        System.out.printf("total flashes: %d", flashCounter);
    }

    private static int[][] loadInput() {
        int[][] in = new int[10][10];

        try (BufferedReader bf = new BufferedReader(new FileReader("src/day11/input.txt"))) {
            String line = bf.readLine();

            int xIndex;
            int yIndex = 0;

            while (line != null) {
                xIndex = 0;

                for (String s : line.split("")) {
                    in[yIndex][xIndex] = Integer.parseInt(s);
                    xIndex++;
                }

                yIndex++;
                line = bf.readLine();
            }

        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }

        return in;
    }

}
