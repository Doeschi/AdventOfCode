package day17;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Part1 {

    private static int xMin;
    private static int xMax;
    private static int yMin;
    private static int yMax;

    public static void main(String[] args) {
        loadInput();

        int lowestXVelocity;
        int highestXVelocity = xMax;
        int lowestYVelocity = 0;
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

        // try every solution to find the best
        int highestInitialYVel = Integer.MIN_VALUE;

        for(int x = lowestXVelocity; x <= highestXVelocity; x++){
            for(int y = lowestYVelocity; y <= highestYVelocity; y++){
                int xVel = x;
                int yVel = y;

                int xPos = 0;
                int yPos = 0;

                while (yPos >= yMin){
                    if (xPos >= xMin && xPos <= xMax && yPos <= yMax){
                        highestInitialYVel = Math.max(highestInitialYVel, y);
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

        int highestYPos = 0;

        for (int i = 1; i <= highestInitialYVel; i++) {
            highestYPos += i;
        }

        System.out.printf("highest y pos: %d%n", highestYPos);

    }


    private static void loadInput() {

        try (BufferedReader bf = new BufferedReader(new FileReader("src/day17/input.txt"))) {
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
