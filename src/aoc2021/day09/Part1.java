package aoc2021.day09;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Part1 {

    public static void main(String[] args) {
        int[][] heightMap = loadInput();
        int riskLevelSum = 0;

        for (int y = 0; y < heightMap.length; y++) {
            for (int x = 0; x < heightMap[y].length; x++) {
                int height = heightMap[y][x];
                int lowerThenAdjacentCounter = 0;

                // left
                if (x == 0 || height < heightMap[y][x - 1]) lowerThenAdjacentCounter++;

                // right
                if(x == heightMap[y].length - 1 || height < heightMap[y][x + 1]) lowerThenAdjacentCounter++;

                // up
                if(y == 0 || height < heightMap[y - 1][x]) lowerThenAdjacentCounter++;

                // down
                if (y == heightMap.length - 1 || height < heightMap[y + 1][x]) lowerThenAdjacentCounter++;

                if (lowerThenAdjacentCounter == 4) riskLevelSum += height + 1;


//                if ((x == 0 || height < heightMap[y][x - 1]) && (x == heightMap[y].length - 1 || height < heightMap[y][x + 1])
//                        && (y == 0 || height < heightMap[y - 1][x]) && (y == heightMap.length - 1 || height < heightMap[y + 1][x])){
//                    riskLevelSum += height + 1;
//                }
            }
        }

        System.out.printf("sum of risk levels: %d", riskLevelSum);
    }

    private static int[][] loadInput() {
        int[][] in = new int[100][100];

        try (BufferedReader bf = new BufferedReader(new FileReader("src/day9/input.txt"))) {
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
