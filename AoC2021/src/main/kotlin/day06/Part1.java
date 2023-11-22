package day06;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Part1 {

    public static void main(String[] args) {
        ArrayList<Integer> fishTimers = loadInput();

        for (int day = 0; day < 80; day++){
            int numberOfNewFish = 0;

            for(int i = 0; i < fishTimers.size(); i++){
                int time = fishTimers.get(i);

                if (time == 0){
                    fishTimers.set(i, 6);
                    numberOfNewFish++;
                } else {
                    fishTimers.set(i, fishTimers.get(i) - 1);
                }
            }

            for(int i = 0; i < numberOfNewFish; i++){
                fishTimers.add(8);
            }
        }

        System.out.printf("Number of fish after 80 day: %d", fishTimers.size());
    }

    private static ArrayList<Integer> loadInput(){
        ArrayList<Integer> in = new ArrayList<>();

        try(BufferedReader bf = new BufferedReader(new FileReader("src/day6/input.txt"))){
            String line = bf.readLine();
            String[] splittedLine = line.split(",");

            for(String s : splittedLine){
                in.add(Integer.parseInt(s));
            }

        }catch(IOException e){
            e.printStackTrace();
            System.exit(-1);
        }

        return in;
    }
}
