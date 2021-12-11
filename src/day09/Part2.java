package day09;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class Part2 {

    private static int[][] heightMap;

    public static void main(String[] args) {
        heightMap = loadInput();
        ArrayList<Integer> basinSizes = new ArrayList<>();

        for (int y = 0; y < heightMap.length; y++) {
            for (int x = 0; x < heightMap[y].length; x++) {
                if (checkHeight(heightMap[y][x]))
                    basinSizes.add(calcBasinSize(x, y));
            }
        }

        Collections.sort(basinSizes);

        // multiply the biggest 3 basins
        int result = basinSizes.get(basinSizes.size() - 1) * basinSizes.get(basinSizes.size() - 2) * basinSizes.get(basinSizes.size() - 3);

        System.out.printf("result: %d", result);
    }

    private static int calcBasinSize(int x, int y) {
        int basinSize = 1; // include myself

        // set the height -1 to mark it as "counted"
        heightMap[y][x] = -1;

        // left
        if (x != 0 && checkHeight(heightMap[y][x - 1]))
            basinSize += calcBasinSize(x - 1, y);

        // right
        if (x != heightMap[y].length - 1 && checkHeight(heightMap[y][x + 1]))
            basinSize += calcBasinSize(x + 1, y);

        // up
        if (y != 0 && checkHeight(heightMap[y - 1][x]))
            basinSize += calcBasinSize(x, y - 1);

        // down
        if (y != heightMap.length - 1 && checkHeight(heightMap[y + 1][x]))
            basinSize += calcBasinSize(x, y + 1);

        return basinSize;
    }

    private static boolean checkHeight(int height) {
        return height != 9 && height != -1;
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
