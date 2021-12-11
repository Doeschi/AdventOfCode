package day7;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Part2 {
    public static void main(String[] args) {
        ArrayList<Integer> crabPositions = loadInput();

        int lowestPos = Integer.MAX_VALUE;
        int highestPos = Integer.MIN_VALUE;

        for (int i : crabPositions) {
            lowestPos = Math.min(lowestPos, i);
            highestPos = Math.max(highestPos, i);
        }

        // pre calculate the fuel consumption for every possible distance
        int[] fuelConsumptionPerDistance = new int[highestPos + 1];

        for(int i = 1; i < highestPos + 1; i++){
            fuelConsumptionPerDistance[i] = fuelConsumptionPerDistance[i - 1] + i;
        }

        int bestPosition = 0;
        int lowestFuelConsumption = Integer.MAX_VALUE;

        for(int pos = lowestPos; pos <= highestPos; pos++){
            int fuelConsumption = 0;

            // calculate fuel consumption for the current pos
            for (int i : crabPositions) {
                int distance = Math.abs(i - pos);
                fuelConsumption += fuelConsumptionPerDistance[distance];
            }

            if (fuelConsumption < lowestFuelConsumption){
                bestPosition = pos;
                lowestFuelConsumption = fuelConsumption;
            }
        }

        System.out.printf("pos: %d, fuel: %d", bestPosition, lowestFuelConsumption);
    }

    private static ArrayList<Integer> loadInput(){
        ArrayList<Integer> in = new ArrayList<>();

        try(BufferedReader bf = new BufferedReader(new FileReader("src/day7/input.txt"))){
            String line = bf.readLine();
            String[] splitted = line.split(",");

            for(String s: splitted){
                in.add(Integer.parseInt(s));
            }

        } catch (IOException e){
            e.printStackTrace();
            System.exit(-1);
        }

        return in;
    }
}
