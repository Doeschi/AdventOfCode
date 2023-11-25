import java.io.File

/*
 * Copyright (c) 2023. by Dominic Gernert
 */

fun main() {
    val day06 = Day06(File("src/main/resources/day06.txt").bufferedReader().readLines())

    println("Part 1 - ${day06.solvePart1()} lights are on")
    println("Part 2 - ${day06.solvePart2()} total brightness")
}

class Day06(input: List<String>) {
    private val instructions = mapInstructions(input)

    fun solvePart1(): Int {
        val lights = Array(1000) { Array(1000) { false } }

        instructions.forEach { instruction ->
            for (x in instruction.xRange) {
                for (y in instruction.yRange) {
                    lights[x][y] = when (instruction.action) {
                        "turn on" -> true
                        "turn off" -> false
                        "toggle" -> !lights[x][y]
                        else -> throw RuntimeException("UNKNOWN ACTION")
                    }
                }
            }
        }

        return lights.sumOf { it.count { light -> light } }
    }

    fun solvePart2(): Int {
        val lights = Array(1000) { Array(1000) { 0 } }

        instructions.forEach { instruction ->
            for (x in instruction.xRange) {
                for (y in instruction.yRange) {
                    val currentBrightness = lights[x][y]

                    lights[x][y] = when (instruction.action) {
                        "turn on" -> currentBrightness + 1
                        "turn off" -> maxOf(currentBrightness - 1, 0)
                        "toggle" -> currentBrightness + 2
                        else -> throw RuntimeException("UNKNOWN ACTION")
                    }
                }
            }
        }

        return lights.sumOf { lightLine -> lightLine.sumOf { it } }
    }
}

private fun mapInstructions(input: List<String>): List<Instruction> = input.map { Instruction.of(it) }

internal data class Instruction(val xRange: IntRange, val yRange: IntRange, val action: String) {
    companion object {
        private val pattern =
            """^(turn on|turn off|toggle) (\d+),(\d+) through (\d+),(\d+)$""".toRegex()

        fun of(input: String): Instruction {
            val (action, x1, y1, x2, y2) = pattern.matchEntire(input)?.destructured
                ?: throw RuntimeException("CANT PARSE INPUT")

            return Instruction(
                x1.toInt()..x2.toInt(),
                y1.toInt()..y2.toInt(),
                action
            )
        }
    }
}