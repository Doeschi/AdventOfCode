import java.io.File

/*
 * Copyright (c) 2023. by Dominic Gernert
 */

fun main() {
    val day02 = Day02(File("src/main/resources/day02.txt").bufferedReader().readLines())

    println("Part 1 - ${day02.solvePart1()}")
    println("Part 2 - ${day02.solvePart2()}")
}

class Day02(input: List<String>) {
    private val games = input

    fun solvePart1(): Int = games.sumOf { game ->
        val s = game.split(": ")
        val gameId = s.first().split(" ").last().toInt()
        val cubeSets = s.last()

        val c = cubeSets.split("; ").toMutableList()
        if (c.isEmpty())
            c.add(cubeSets)

        var isPossible = true

        outerLoop@
        for (cubeSet in c) {
            val cubes = cubeSet.split(", ").toMutableList()
            if (cubes.isEmpty())
                cubes.add(cubeSet)

            for (cube in cubes) {
                val r = cube.split(" ")
                val amount = r.first().toInt()
                val color = r.last()

                if ((color == "red" && amount > 12) || (color == "green" && amount > 13) || (color == "blue" && amount > 14)) {
                    isPossible = false
                    break@outerLoop
                }
            }
        }

        if (isPossible) gameId else 0
    }

    fun solvePart2(): Int = games.sumOf { game ->
        val s = game.split(": ")
        val cubeSets = s.last()

        val c = cubeSets.split("; ").toMutableList()
        if (c.isEmpty())
            c.add(cubeSets)

        var redAmount = 0
        var greenAmount = 0
        var blueAmount = 0

        for (cubeSet in c) {
            val cubes = cubeSet.split(", ").toMutableList()
            if (cubes.isEmpty())
                cubes.add(cubeSet)

            for (cube in cubes) {
                val r = cube.split(" ")
                val amount = r.first().toInt()
                val color = r.last()

                if (color == "red" && amount > redAmount)
                    redAmount = amount
                else if (color == "green" && amount > greenAmount)
                    greenAmount = amount
                else if (color == "blue" && amount > blueAmount)
                    blueAmount = amount
            }
        }

        redAmount * greenAmount * blueAmount
    }
}