package day8;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Part2 {
    public static void main(String[] args) {
        ArrayList<String> input = loadInput();

//        int digitCounter = 0;
//
//        for (String line : input) {
//            String[] splitted = line.split(" \\| ");
//            String[] outputValues = splitted[1].split(" ");
//
//            for(String s : outputValues){
//                int len = s.length();
//
//                // digits 1, 7, 4, 8
//                if (len == 2 || len == 3 || len == 4 || len == 7) digitCounter++;
//
//            }
//        }
//
//        System.out.printf("digits: %d", digitCounter);
    }

    private static ArrayList<String> loadInput(){
        ArrayList<String> in = new ArrayList<>();

        try(BufferedReader bf = new BufferedReader(new FileReader("src/day8/input.txt"))){
            String line = bf.readLine();

            while (line != null){
                in.add(line);
                line = bf.readLine();
            }

        } catch (IOException e){
            e.printStackTrace();
            System.exit(-1);
        }

        return in;
    }
}
