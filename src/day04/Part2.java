package day04;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Part2 {

    public static int[] bingoNumbers;
    public static ArrayList<Integer> calledBingoNumbers;
    public static ArrayList<int[][]> bingoBoards;

    public static void main(String[] args) {
        init();
        int lastWinnerScore = 0;

        // go through every number
        for (int bingoNumber : bingoNumbers) {
            calledBingoNumbers.add(bingoNumber);

            // get the winner board, if there is one
            ArrayList<int[][]> winnerBoards = getWinnerBoards();

            for (int[][] board : winnerBoards) {
                lastWinnerScore = calculateScore(board, bingoNumber);
                bingoBoards.remove(board);
            }
        }

        System.out.println("Score: " + lastWinnerScore);
    }

    private static ArrayList<int[][]> getWinnerBoards() {
        ArrayList<int[][]> winnerBoards = new ArrayList<>();
        int numberOfMarkedCells;

        boardLoop:
        for (int[][] board : bingoBoards) {

            // check rows
            for (int i = 0; i < 5; i++) {
                numberOfMarkedCells = 0;
                for (int j = 0; j < 5; j++) {
                    if (calledBingoNumbers.contains(board[i][j])) numberOfMarkedCells++;
                }

                if (numberOfMarkedCells == 5) {
                    winnerBoards.add(board);
                    continue boardLoop;
                }
            }

            // check columns
            for (int j = 0; j < 5; j++) {
                numberOfMarkedCells = 0;
                for (int i = 0; i < 5; i++) {
                    if (calledBingoNumbers.contains(board[i][j])) numberOfMarkedCells++;
                }
                if (numberOfMarkedCells == 5) {
                    winnerBoards.add(board);
                    continue boardLoop;
                }

            }
        }

        return winnerBoards;
    }

    private static int calculateScore(int[][] board, int bingoNumber) {
        int sum = 0;

        // calc the sum of the unmarked cells
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                int number = board[i][j];
                if (!calledBingoNumbers.contains(number)) {
                    sum += number;
                }
            }
        }

        return sum * bingoNumber;
    }

    private static void init() {
        // read input file
        try (BufferedReader reader = new BufferedReader(new FileReader("src/day4/input.txt"))) {
            // get the bingo numbers
            String line = reader.readLine();
            bingoNumbers = Arrays.stream(line.split(",")).mapToInt(Integer::parseInt).toArray();

            // read the blank line
            line = reader.readLine();

            // add the boards
            bingoBoards = new ArrayList<>();
            while (line != null) {
                int[][] board = new int[5][];

                for (int i = 0; i < 5; i++) {
                    line = reader.readLine().strip().replace("  ", " ");
                    String[] numbers = line.split(" ");
                    board[i] = Arrays.stream(numbers).mapToInt(Integer::parseInt).toArray();
                }
                bingoBoards.add(board);

                // read blank line
                line = reader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }

        calledBingoNumbers = new ArrayList<>();
    }
}
