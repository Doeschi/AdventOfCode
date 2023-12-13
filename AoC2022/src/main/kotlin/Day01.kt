import java.awt.image.AreaAveragingScaleFilter
import java.io.File

/*
 * Copyright (c) 2023. by Dominic Gernert
 */

fun main() {
    val day01 = Day01(File("src/main/resources/day01.txt").bufferedReader().readLines())

    println("Part 1 - ${day01.solvePart1()}")
    println("Part 2 - ${day01.solvePart2()}")
}

class Day01(input: List<String>) {
    private val calorieList = input

    fun solvePart1(): Int {
        var max = 0
        var currentSum = 0

        calorieList.forEach { line ->
            if (line == "") {
                if (currentSum > max)
                    max = currentSum

                currentSum = 0
            } else
                currentSum += line.toInt()
        }

        return max
    }

    fun solvePart2(): Int {
        val totalOfCalories = ArrayList<Int>()
        var currentSum = 0

        calorieList.forEach { line ->
            if (line == "") {
                totalOfCalories.add(currentSum)
                currentSum = 0
            } else
                currentSum += line.toInt()
        }

        totalOfCalories.sortDescending()

        var total = 0
        for (i in 0..2)
            total += totalOfCalories[i]

        return total
    }
}