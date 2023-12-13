import java.io.File

/*
 * Copyright (c) 2023. by Dominic Gernert
 */

fun main() {
    val day03 = Day03(File("src/main/resources/day03.txt").bufferedReader().readLines())

    println("Part 1 - ${day03.solvePart1()}")
    println("Part 2 - ${day03.solvePart2()}")
}

class Day03(input: List<String>) {
    private val rucksacks = input

    fun solvePart1(): Int = rucksacks.sumOf { rucksack ->
        val compartment1 = rucksack.substring(0, rucksack.length / 2)
        val compartment2 = rucksack.substring(rucksack.length / 2)

        val containingBoth = mutableSetOf<Char>()

        compartment1.forEach { item ->
            if (compartment2.contains(item))
                containingBoth.add(item)
        }

        containingBoth.sumOf { item ->
            getPriority(item)
        }
    }

    fun solvePart2(): Int = rucksacks.chunked(3).sumOf { group ->
        val first = group.component1()
        val second = group.component2()
        val third = group.component3()

        var commonItem = ' '
        for (item in first) {
            if (second.contains(item) && third.contains(item)) {
                commonItem = item
                break
            }
        }

        getPriority(commonItem)
    }

    private fun getPriority(item: Char): Int {
        return when (item.code) {
            in 97..122 -> item.code - 96
            in 65..90 -> item.code - 38
            else -> throw RuntimeException("ITEM HAS NO PRIORITY: $item")
        }
    }
}