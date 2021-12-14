package day14;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Part1 {

    private static int IDX_CONDITION = 0;
    private static int IDX_ELEMENT = 1;

    private static String template;
    private static ArrayList<String[]> insertionRules;

    public static void main(String[] args) {
        loadInput();

        StringBuilder polymer = new StringBuilder(template);

        for (int i = 0; i < 10; i++) {

            int index = 0;

            while (index < polymer.length() - 1) {
                for (String[] insertionRule : insertionRules) {
                    if (insertionRule[IDX_CONDITION].equals(polymer.substring(index, index + 2))) {
                        polymer.insert(index + 1, insertionRule[IDX_ELEMENT]);
                        index++;
                        break;
                    }
                }

                index++;
            }
        }

        HashMap<Integer, Integer> elementMap = new HashMap();

        for (int i = 0; i < polymer.length(); i++) {
            int c = polymer.charAt(i);
            if (!elementMap.containsKey(c))
                elementMap.put(c, 1);
            else
                elementMap.put(c, elementMap.get(c) + 1);
        }

        int mostCommon = Integer.MIN_VALUE;
        int leastCommon = Integer.MAX_VALUE;

        for (int i : elementMap.values()) {
            if (i > mostCommon) {
                mostCommon = i;
            } else if (i < leastCommon) {
                leastCommon = i;
            }
        }

        int result = mostCommon - leastCommon;
        System.out.println(result);
    }

    private static void loadInput() {
        insertionRules = new ArrayList<>();

        try (BufferedReader bf = new BufferedReader(new FileReader("src/day14/input.txt"))) {
            template = bf.readLine();

            String line = bf.readLine();

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
