import java.io.File

/*
 * Copyright (c) 2023. by Dominic Gernert
 */

fun main() {
    val day02 = Day02(File("src/main/resources/day02.txt").bufferedReader().readLines())

    println("Part 1 - total score ${day02.solvePart1()}")
    println("Part 2 - ${day02.solvePart2()}")
}

class Day02(input: List<String>) {
    private val instructions = input
    private val shapeScore = mapOf("X" to 1, "Y" to 2, "Z" to 3)

    fun solvePart1(): Int = instructions.sumOf { instruction ->
        val split = instruction.split(" ")

        val enemy = split.first()
        val myself = split.last()

        val score =
            if ((enemy == "A" && myself == "X") || (enemy == "B" && myself == "Y") || (enemy == "C" && myself == "Z"))
                3
            else if ((enemy == "A" && myself == "Y") || (enemy == "B" && myself == "Z") || (enemy == "C" && myself == "X"))
                6
            else
                0

        score + shapeScore[myself]!!
    }

    fun solvePart2(): Int {
        return 0
    }
}