import java.io.File

/*
 * Copyright (c) 2023. by Dominic Gernert
 */

fun main() {
    val day08 = Day08(File("src/main/resources/day08.txt").bufferedReader().readLines())

    println("Part 1 - difference: ${day08.solvePart1()}")
    println("Part 2 - difference: ${day08.solvePart2()}")

}

class Day08(input: List<String>) {
    private val inputStrings = input

    fun solvePart1(): Int = inputStrings.sumOf { s ->
        var difference = 2 // beginn and end quote
        var i = 1

        while (i < s.lastIndex) {
            if (s[i] == '\\') {
                val offset = when (s[i + 1]) {
                    '\\' -> 1
                    '"' -> 1
                    'x' -> 3
                    else -> throw RuntimeException("Unexpected char")
                }

                difference += offset
                i += offset
            }

            i++
        }

        difference
    }

    fun solvePart2(): Int = inputStrings.sumOf { s ->
        s.count { c -> c == '\\' || c == '"' } + 2 // beginn and end quote
    }
}