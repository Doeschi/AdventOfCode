package utils;

public class Timer {
    private static long beginTime;
    private static long endTime;

    public static void startTimer() {
        beginTime = System.currentTimeMillis();
    }

    public static void endTimer() {
        endTime = System.currentTimeMillis();
    }

    public static void printTimer() {
        System.out.printf("runtime: %dms%n", endTime - beginTime);
    }

    public static void endAndPrintTimer() {
        endTimer();
        printTimer();
    }
}
