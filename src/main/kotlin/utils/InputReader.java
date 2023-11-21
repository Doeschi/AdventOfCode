package utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class InputReader {

    public static String readInputString(String filePath) {
        String line = "";

        try (BufferedReader bf = new BufferedReader(new FileReader(filePath))) {
            line = bf.readLine();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }

        return line;
    }

    public static ArrayList<String> readInputStringList(String filePath) {
        ArrayList<String> in = new ArrayList<>();

        try (BufferedReader bf = new BufferedReader(new FileReader(filePath))) {
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

    public static ArrayList<Integer> readInputIntList(String filePath) {
        ArrayList<Integer> in = new ArrayList<>();

        try (BufferedReader bf = new BufferedReader(new FileReader(filePath))) {
            String line = bf.readLine();

            while (line != null) {
                in.add(Integer.parseInt(line));
                line = bf.readLine();
            }

        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }

        return in;
    }
}
