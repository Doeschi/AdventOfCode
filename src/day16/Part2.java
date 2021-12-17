package day16;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Part2 {

    private static int index;
    private static String binaries;

    public static void main(String[] args) {
        binaries = loadInput();
        index = 0;

        long result = readPacket();

        System.out.printf("packet result: %d%n", result);
    }

    private static long readPacket() {
        checkVersion();
        int type = checkType();

        if (type == 0) {
            return evalSumPacket();
        } else if (type == 1) {
            return evalProductPacket();
        } else if (type == 2) {
            return evalMinimumPacket();
        } else if (type == 3) {
            return evalMaximumPacket();
        } else if (type == 4) {
            return evalLiteral();
        } else if (type == 5) {
            return evalGreaterThanPacket();
        } else if (type == 6) {
            return evalLessThanPacket();
        } else {

            if (type != 7)
                System.out.println("NOT 7");
            return evalEqualToPacket();
        }

    }

    private static long evalSumPacket() {
        long sum = 0;

        if (checkLengthType() == '0') {
            int endIndex = getEndIndexOfPacket();

            while (index < endIndex) {
                sum += readPacket();
            }
        } else {
            int numberOfSubPackets = getNumberOfSubPackets();

            for (int i = 0; i < numberOfSubPackets; i++) {
                sum += readPacket();
            }
        }

        return sum;
    }

    private static long evalProductPacket() {
        long product = 1;

        if (checkLengthType() == '0') {
            int endIndex = getEndIndexOfPacket();

            while (index < endIndex) {
                product *= readPacket();
            }
        } else {
            int numberOfSubPackets = getNumberOfSubPackets();

            for (int i = 0; i < numberOfSubPackets; i++) {
                product *= readPacket();
            }
        }

        return product;
    }

    private static long evalMinimumPacket() {
        long minimum = Integer.MAX_VALUE;

        if (checkLengthType() == '0') {
            int endIndex = getEndIndexOfPacket();

            while (index < endIndex) {
                minimum = Math.min(readPacket(), minimum);
            }
        } else {
            int numberOfSubPackets = getNumberOfSubPackets();

            for (int i = 0; i < numberOfSubPackets; i++) {
                minimum = Math.min(readPacket(), minimum);
            }
        }

        return minimum;
    }

    private static long evalMaximumPacket() {
        long maximum = Integer.MIN_VALUE;

        if (checkLengthType() == '0') {
            int endIndex = getEndIndexOfPacket();

            while (index < endIndex) {
                maximum = Math.max(readPacket(), maximum);
            }
        } else {
            int numberOfSubPackets = getNumberOfSubPackets();

            for (int i = 0; i < numberOfSubPackets; i++) {
                maximum = Math.max(readPacket(), maximum);
            }
        }

        return maximum;
    }

    private static long evalGreaterThanPacket() {
        if (checkLengthType() == '0')
            index += 15;
        else
            index += 11;

        long packet1 = readPacket();
        long packet2 = readPacket();

        return packet1 > packet2 ? 1 : 0;
    }

    private static long evalLessThanPacket() {
        if (checkLengthType() == '0')
            index += 15;
        else
            index += 11;

        long packet1 = readPacket();
        long packet2 = readPacket();

        return packet1 < packet2 ? 1 : 0;
    }

    private static long evalEqualToPacket() {
        if (checkLengthType() == '0')
            index += 15;
        else
            index += 11;

        long packet1 = readPacket();
        long packet2 = readPacket();

        return packet1 == packet2 ? 1 : 0;
    }

    private static long evalLiteral() {
        char leadingBit;
        StringBuilder builder = new StringBuilder();
        do {
            leadingBit = binaries.charAt(index);
            builder.append(binaries, index + 1, index + 5);
            index += 5;
        } while (leadingBit == '1');

        return Long.parseLong(builder.toString(), 2);
    }

    private static int getEndIndexOfPacket() {
        int subPacketsLength = Integer.parseInt(binaries.substring(index, index + 15), 2);
        index += 15;
        return index + subPacketsLength;
    }

    private static int getNumberOfSubPackets() {
        int numberOfSubPackets = Integer.parseInt(binaries.substring(index, index + 11), 2);
        index += 11;
        return numberOfSubPackets;
    }

    private static char checkLengthType() {
        char c = binaries.charAt(index);
        index++;
        return c;
    }

    private static void checkVersion() {
//        int version = Integer.parseInt(binaries.substring(index, index + 3), 2);
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
