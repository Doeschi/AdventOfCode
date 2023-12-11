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

    fun solvePart2(): Int = calibrationDoc.sumOf { line ->
        val pattern = Regex("""one|two|three|four|five|six|seven|eight|nine|\d""")
        val r = pattern.findAllWithOverlap(line).toList()

        val firstDig = getValue(r.first().value)
        val lastDig = getValue(r.last().value)

        (firstDig * 10) + lastDig
    }

    fun getValue(input: String): Int {
        try {
            return input.toInt()
        } catch (e: NumberFormatException) {
            return when (input) {
                "one" -> 1
                "two" -> 2
                "three" -> 3
                "four" -> 4
                "five" -> 5
                "six" -> 6
                "seven" -> 7
                "eight" -> 8
                "nine" -> 9
                else -> throw RuntimeException("CANT PARSE NUMBER: $input")
            }
        }
    }
}