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
    private val instructions = input
    private val shapeScore = mapOf("A" to 1, "B" to 2, "C" to 3)
    private val shapes = mapOf("X" to "A", "Y" to "B", "Z" to "C")

    // A = ROCK
    // B = PAPER
    // C = SCISSORS

    fun solvePart1(): Int = instructions.sumOf { instruction ->
        val split = instruction.split(" ")

        val enemy = split.first()
        val myself = shapes[split.last()]

        val score =
            if (myself == enemy)
                3
            else if ((enemy == "A" && myself == "B") || (enemy == "B" && myself == "C") || (enemy == "C" && myself == "A"))
                6
            else
                0

        score + shapeScore[myself]!!
    }

    // X = LOSE
    // Y = DRAW
    // Z = WIN

    // A = ROCK
    // B = PAPER
    // C = SCISSORS

    fun solvePart2(): Int = instructions.sumOf { instruction ->
        val s = instruction.split(" ")

        val enemy = s.first()
        val outCome = s.last()

        val score = when (outCome) {
            "X" -> 0
            "Y" -> 3
            else -> 6
        }

        val myself =
            if ((outCome == "X" && enemy == "A") || (outCome == "Y" && enemy == "C") || (outCome == "Z" && enemy == "B"))
                "C"
            else if ((outCome == "X" && enemy == "C") || (outCome == "Y" && enemy == "B") || (outCome == "Z" && enemy == "A"))
                "B"
            else
                "A"

        score + shapeScore[myself]!!
    }
}