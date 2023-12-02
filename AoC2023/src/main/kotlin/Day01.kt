import java.io.File

/*
 * Copyright (c) 2023. by Dominic Gernert
 */

fun main() {
    val day01 = Day01(File("src/main/resources/day01.txt").bufferedReader().readLines())

    println("Part 1 - sum is ${day01.solvePart1()}")
    println("Part 2 - sum is ${day01.solvePart2()}")
}

class Day01(input: List<String>) {
    private val calibrationDoc = input

    fun solvePart1(): Int = calibrationDoc.sumOf { line ->
        var firstDig = -1
        var lastDig = -1

        for (i in 0..line.lastIndex) {
            if (firstDig == -1 && line[i].isDigit())
                firstDig = line[i].digitToInt()

            if (lastDig == -1 && line[line.lastIndex - i].isDigit())
                lastDig = line[line.lastIndex - i].digitToInt()

            if (firstDig != -1 && lastDig != -1)
                break
        }

        (firstDig * 10) + lastDig
    }

    fun solvePart2(): Int {
        // implemented in python because kotlin regex doesn't
        // work the same as python regex
        return 0
    }
}