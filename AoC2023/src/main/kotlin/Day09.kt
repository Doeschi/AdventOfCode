import java.io.File

/*
 * Copyright (c) 2023. by Dominic Gernert
 */

fun main() {
    val day09 = Day09(File("src/main/resources/day09.txt").bufferedReader().readLines())

    println("Part 1 - ${day09.solvePart1()}")
    println("Part 2 - ${day09.solvePart2()}")
}

class Day09(input: List<String>) {
    private val histories = mapValues(input)

    fun solvePart1(): Int {
        var sum = 0

        histories.forEach { history ->
            var predictedValue = history.historicValues.last()
            var currentValues = history.historicValues

            while (true) {
                val newValues = mutableListOf<Int>()

                for (i in 0..<currentValues.lastIndex) {
                    newValues.add(currentValues[i + 1] - currentValues[i])
                }

                if (newValues.count { it != 0 } == 0) {
                    break
                }

                predictedValue += newValues.last()
                currentValues = newValues
            }

            sum += predictedValue
        }

        return sum
    }

    fun solvePart2(): Int {
        var sum = 0

        histories.forEach { history ->
            val firstValues = mutableListOf<Int>()
            var currentValues = history.historicValues

            while (true) {
                firstValues.add(currentValues.first())
                val newValues = mutableListOf<Int>()

                for (i in 0..<currentValues.lastIndex) {
                    newValues.add(currentValues[i + 1] - currentValues[i])
                }

                if (newValues.count { it != 0 } == 0) {
                    break
                }

                currentValues = newValues
            }

            var result = 0
            firstValues.reverse()

            firstValues.forEach { value ->
                result = value - result
            }

            sum += result
        }

        return sum
    }

    private fun mapValues(input: List<String>) = input.map { ValueHistory.of(it) }
}

internal data class ValueHistory(val historicValues: List<Int>) {
    companion object {
        fun of(rawString: String): ValueHistory {
            val values = rawString.split(" ").map { it.toInt() }
            return ValueHistory(values)
        }
    }
}