import java.io.File

/*
 * Copyright (c) 2023. by Dominic Gernert
 */

fun main() {
    val day10 = Day10(File("src/main/resources/day10.txt").bufferedReader().readLine())

    println("Part 1 - length after 40 iterations is ${day10.solvePart1()}")
    println("Part 2 - length after 50 iterations is ${day10.solvePart2()}")
}

class Day10(input: String) {
    private val initialSequence = input

    fun solvePart1(): Int = getLength(initialSequence, 40)

    fun solvePart2(): Int = getLength(initialSequence, 50)

    private fun getLength(sequence: String, nbrOfIterations: Int): Int {
        var s = sequence

        for (i in 0..<nbrOfIterations)
            s = iterateSequence(s)

        return s.length
    }

    private fun iterateSequence(sequence: String): String {
        var previousChar = '-'
        var nbrOfOccurrence = 1
        val newSequence = StringBuilder()

        sequence.forEach { c ->
            if (c == previousChar)
                nbrOfOccurrence++
            else if (previousChar != '-') {
                newSequence.append("$nbrOfOccurrence$previousChar")
                nbrOfOccurrence = 1
            }
            previousChar = c
        }

        newSequence.append("$nbrOfOccurrence$previousChar")

        return newSequence.toString()
    }
}