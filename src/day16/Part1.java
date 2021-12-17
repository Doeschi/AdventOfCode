package day16;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Part1 {

    private static int index;
    private static String binaries;
    private static int versionSum;

    public static void main(String[] args) {
        binaries = loadInput();
        index = 0;
        versionSum = 0;

        readPacket();

        System.out.printf("version sum: %d", versionSum);
    }

    private static void readPacket() {
        checkVersion();
        int type = checkType();

        if (type == 4) {
            readLiteral();
        } else {
            char lengthType = binaries.charAt(index);
            index++;

            if (lengthType == '0') {
                readOperatorPacketTypeZero();
            } else {
                readOperatorPacketTypeOne();
            }
        }
    }

    private static void readOperatorPacketTypeZero() {
        int subPacketsLength = Integer.parseInt(binaries.substring(index, index + 15), 2);
        index += 15;

        int endIndex = index + subPacketsLength;
        while (index < endIndex) {
            readPacket();
        }
    }

    private static void readOperatorPacketTypeOne() {
        int numberOfSubPackets = Integer.parseInt(binaries.substring(index, index + 11), 2);
        index += 11;

        for (int i = 0; i < numberOfSubPackets; i++) {
            readPacket();
        }
    }

    private static void readLiteral() {
        char leadingBit;

        do {
            leadingBit = binaries.charAt(index);
            index += 5;
        } while (leadingBit == '1');
    }

    private static void checkVersion() {
        int version = Integer.parseInt(binaries.substring(index, index + 3), 2);
        versionSum += version;
        index += 3;
    }

    private static int checkType() {
        int type = Integer.parseInt(binaries.substring(index, index + 3), 2);
        index += 3;
        return type;
    }

    private static String loadInput() {
        StringBuilder builder = new StringBuilder();

        try (BufferedReader bf = new BufferedReader(new FileReader("src/day16/input.txt"))) {
            String line = bf.readLine();

            for (int i = 0; i < line.length(); i++) {
                String hex = line.substring(i, i + 1);
                String binary = Integer.toBinaryString(Integer.parseInt(hex, 16));

                // doing some wacky formatting to get the binaries to have leading zeros
                String s = String.format("%4s", binary).replace(' ', '0');
                builder.append(s);
            }

        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }

        return builder.toString();
    }
}
