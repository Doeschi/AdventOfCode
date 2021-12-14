package day14;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Part2 {
    private static int IDX_CONDITION = 0;
    private static int IDX_ELEMENT = 1;

    private static HashMap<String, Long> polymerConnections;
    private static HashMap<Character, Long> elementCounter;
    private static ArrayList<String[]> insertionRules;

    public static void main(String[] args) {
        loadInput();

        // step 40 times
        for (int i = 0; i < 40; i++) {
            HashMap<String, Long> newPolymerConnections = new HashMap<>();

            polymerLoop:
            for (Map.Entry<String, Long> entry : polymerConnections.entrySet()) {
                String polymer = entry.getKey();
                long numberOfPolymer = entry.getValue();

                // check if one of the rules can be applied to the polmer
                for (String[] insertionRule : insertionRules) {
                    if (insertionRule[IDX_CONDITION].equals(polymer)) {

                        countElement(insertionRule[IDX_ELEMENT].charAt(0), numberOfPolymer);

                        String newPolymer = polymer.charAt(0) + insertionRule[IDX_ELEMENT];
                        if (!newPolymerConnections.containsKey(newPolymer))
                            newPolymerConnections.put(newPolymer, numberOfPolymer);
                        else
                            newPolymerConnections.put(newPolymer, newPolymerConnections.get(newPolymer) + numberOfPolymer);

                        newPolymer = insertionRule[IDX_ELEMENT] + polymer.charAt(1);
                        if (!newPolymerConnections.containsKey(newPolymer))
                            newPolymerConnections.put(newPolymer, numberOfPolymer);
                        else
                            newPolymerConnections.put(newPolymer, newPolymerConnections.get(newPolymer) + numberOfPolymer);

                        continue polymerLoop;
                    }
                }

                // this code should never be reached
                newPolymerConnections.put(polymer, numberOfPolymer);
            }

            polymerConnections = newPolymerConnections;
        }

        long mostCommon = Long.MIN_VALUE;
        long leastCommon = Long.MAX_VALUE;

        for (long counter : elementCounter.values()) {
            if (counter > mostCommon) {
                mostCommon = counter;
            } else if (counter < leastCommon) {
                leastCommon = counter;
            }
        }

        long result = mostCommon - leastCommon;
        System.out.println(result);
    }

    private static void countElement(char c, long amount) {
        if (!elementCounter.containsKey(c))
            elementCounter.put(c, amount);
        else
            elementCounter.put(c, elementCounter.get(c) + amount);
    }

    private static void insertPolymer(String s) {
        if (!polymerConnections.containsKey(s))
            polymerConnections.put(s, 1L);
        else
            polymerConnections.put(s, polymerConnections.get(s) + 1);
    }

    private static void loadInput() {
        polymerConnections = new HashMap<>();
        elementCounter = new HashMap<>();
        insertionRules = new ArrayList<>();

        try (BufferedReader bf = new BufferedReader(new FileReader("src/day14/input.txt"))) {
            String line = bf.readLine();

            for (int i = 0; i < line.length() - 1; i++) {
                insertPolymer(line.substring(i, i + 2));
                countElement(line.charAt(i), 1);
            }

            countElement(line.charAt(line.length() - 1), 1);
            bf.readLine();

            while (line != null) {
                insertionRules.add(line.split(" -> "));
                line = bf.readLine();
            }

        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }
}
