import java.io.File
import java.lang.StringBuilder

/*
 * Copyright (c) 2023. by Dominic Gernert
 */

fun main() {
    val day06 = Day06(File("src/main/resources/day06.txt").bufferedReader().readLine())

    println("Part 1 - ${day06.solvePart1()}")
    println("Part 2 - ${day06.solvePart2()}")
}

class Day06(input: String) {
    private val signal = input

    fun solvePart1(): Int = getBeginOfMark(4)

    fun solvePart2(): Int = getBeginOfMark(14)

    private fun getBeginOfMark(markLength: Int): Int {
        val previousChars = StringBuilder()

        signal.forEachIndexed { index, c ->
            if (previousChars.length < markLength)
                previousChars.append(c)
            else {
                previousChars.deleteAt(0)
                previousChars.append(c)

                if (previousChars.toSet().size == markLength)
                    return index + 1
            }
        }

        throw RuntimeException("NO START OF MARKER FOUND")
    }
}