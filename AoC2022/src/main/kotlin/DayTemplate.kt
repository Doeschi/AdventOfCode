import java.io.File

/*
 * Copyright (c) 2023. by Dominic Gernert
 */

fun main() {
    val dayXX = DayTemplate(File("src/main/resources/dayXX.txt").bufferedReader().readLine())

    println("Part 1 - ${dayXX.solvePart1()}")
    println("Part 2 - ${dayXX.solvePart2()}")
}

class DayTemplate(input: String) {
    private val i = input

    fun solvePart1(): Int {
        return 0
    }

    fun solvePart2(): Int {
        return 0
    }
}