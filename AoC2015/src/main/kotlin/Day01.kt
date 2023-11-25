import java.io.File

/*
 * Copyright (c) 2023. by Dominic Gernert
 */

fun main() {
    val day1 = Day01(File("src/main/resources/day01.txt").bufferedReader().readLine())
    println("Part 1 - Floor ${day1.solvePart1()}")
    println("Part 2 - Instruction ${day1.solvePart2()}")
}

class Day01(input: String) {
    private val instructions = input
    fun solvePart1(): Int = instructions.count { it == '(' } - instructions.count { it == ')' }

    fun solvePart2(): Int {
        var floor = 0
        var counter = 0

        instructions.forEach { c ->
            floor += if (c == '(') 1 else -1
            counter++
            if (floor == -1)
                return counter
        }

        return -1
    }

}
