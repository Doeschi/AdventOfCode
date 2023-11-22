package day17;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Part2 {
    private static int xMin;
    private static int xMax;
    private static int yMin;
    private static int yMax;

    public static void main(String[] args) {
        loadInput();

        int lowestXVelocity;
        int highestXVelocity = xMax;
        int lowestYVelocity = yMin;
        int highestYVelocity = -yMin;

        // find the lowest possible x velocity
        int initialVel = 1;
        int pos = 0;

        while (true) {
            pos += initialVel;

            if (pos >= xMin) {
                lowestXVelocity = initialVel;
                break;
            }

            initialVel++;
        }

        // find every solution
        int counter = 0;

        for (int x = lowestXVelocity; x <= highestXVelocity; x++) {
            for (int y = lowestYVelocity; y <= highestYVelocity; y++) {
                int xVel = x;
                int yVel = y;

                int xPos = 0;
                int yPos = 0;

                while (yPos >= yMin) {
                    if (xPos >= xMin && xPos <= xMax && yPos <= yMax) {
                        counter++;
                        break;
                    }

                    xPos += xVel;
                    yPos += yVel;

                    if (xVel > 0)
                        xVel--;
                    else if (xVel < 0)
                        xVel++;

                    yVel--;
                }
            }
        }

        System.out.printf("number of initial velocities %d%n", counter);

    }


    private static void loadInput() {

        try (BufferedReader bf = new BufferedReader(new FileReader("src/aoc2021.day17/input.txt"))) {
            String line = bf.readLine().replace("target area: ", "");

            String[] splitted = line.split(", ");

            String xRange = splitted[0].replace("x=", "");
            String[] xRangeSplit = xRange.split("\\.\\.");
            xMin = Integer.parseInt(xRangeSplit[0]);
            xMax = Integer.parseInt(xRangeSplit[1]);

            String yRange = splitted[1].replace("y=", "");
            String[] yRangeSplit = yRange.split("\\.\\.");
            yMin = Integer.parseInt(yRangeSplit[0]);
            yMax = Integer.parseInt(yRangeSplit[1]);

        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }

    }
}
