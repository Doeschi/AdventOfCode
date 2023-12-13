import java.io.File

/*
 * Copyright (c) 2023. by Dominic Gernert
 */

fun main() {
    val day04 = Day04(File("src/main/resources/day04.txt").bufferedReader().readLines())

    println("Part 1 - ${day04.solvePart1()}")
    println("Part 2 - ${day04.solvePart2()}")
}

class Day04(input: List<String>) {
    private val sectionPairs = mapSectionPairs(input)

    fun solvePart1(): Int = sectionPairs.count { pair ->
        if (pair.first.intersects(pair.second)) {
            val intersection = pair.first.intersection(pair.second)

            intersection == pair.first || intersection == pair.second
        } else
            false
    }

    fun solvePart2(): Int = sectionPairs.count { it.first.intersects(it.second) }

    private fun mapSectionPairs(input: List<String>): List<SectionPair> = input.map { pair ->
        val s = pair.split(",")

        val first = s.first().split("-")
        val firstBegin = first.first().toInt()
        val firstEnd = first.last().toInt()
        val firstRange = IntRange(firstBegin, firstEnd)

        val second = s.last().split("-")
        val secondBegin = second.first().toInt()
        val secondEnd = second.last().toInt()
        val secondRange = IntRange(secondBegin, secondEnd)

        SectionPair(firstRange, secondRange)
    }
}

internal data class SectionPair(val first: IntRange, val second: IntRange)