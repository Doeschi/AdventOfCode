import java.io.File
import kotlin.math.abs

/*
 * Copyright (c) 2023. by Dominic Gernert
 */

fun main() {
    val day11 = Day11(File("src/main/resources/day11.txt").bufferedReader().readLines())

    println("Part 1 - ${day11.solvePart1()}")
    println("Part 2 - ${day11.solvePart2()}")
}

class Day11(input: List<String>) {
    private val galaxiesPart1 = getGalaxies(input, 2)
    private val galaxiesPart2 = getGalaxies(input, 1_000_000)

    fun solvePart1(): Long = getTotalLength(galaxiesPart1)

    fun solvePart2(): Long = getTotalLength(galaxiesPart2)

    private fun getTotalLength(galaxies: List<Galaxy>): Long {
        var totalLength = 0L
        for (i in 0..<galaxies.lastIndex) {
            for (j in i + 1..galaxies.lastIndex) {
                val g1 = galaxies[i]
                val g2 = galaxies[j]
                totalLength += getLength(g1, g2)
            }
        }

        return totalLength
    }

    private fun getLength(g1: Galaxy, g2: Galaxy): Long {
        return abs(g1.x - g2.x) + abs(g1.y - g2.y)
    }

    private fun getGalaxies(input: List<String>, gapMultiplier: Int = 1): List<Galaxy> {
        val galaxies = mutableListOf<Galaxy>()
        val horizontalOffsets = (0..input.lastIndex).toMutableSet()
        val verticalOffsets = (0..input[0].lastIndex).toMutableSet()

        input.forEachIndexed { rowIndex, line ->
            var hasGalaxy = false

            line.forEachIndexed { colIndex, c ->
                if (c == '#') {
                    galaxies.add(Galaxy(colIndex.toLong(), rowIndex.toLong()))
                    verticalOffsets.remove(colIndex)
                    hasGalaxy = true
                }
            }

            if (hasGalaxy) {
                horizontalOffsets.remove(rowIndex)
            }
        }

        galaxies.forEach { galaxy ->
            var totalYOffset = 0
            horizontalOffsets.forEach { offset ->
                if (galaxy.y > offset)
                    totalYOffset++
            }
            galaxy.y += totalYOffset * (gapMultiplier - 1)

            var totalXOffset = 0
            verticalOffsets.forEach { offset ->
                if (galaxy.x > offset)
                    totalXOffset++
            }
            galaxy.x += totalXOffset * (gapMultiplier - 1)
        }

        return galaxies
    }
}

internal data class Galaxy(var x: Long, var y: Long)