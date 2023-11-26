import java.io.File

/*
 * Copyright (c) 2023. by Dominic Gernert
 */

fun main() {
    val day03 = Day03(File("src/main/resources/day03.txt").bufferedReader().readLine())

    println("Part 1 - ${day03.solvePart1()} houses get at least one present")
    println("Part 2 - ${day03.solvePart2()}")

}

class Day03(input: String) {
    private val moves = input

    fun solvePart1(): Int = visitHouses1(moves)

    fun solvePart2(): Int = visitHouses2(moves)

    private fun visitHouses1(moves: String): Int {
        val houses = HashSet<Point2d>()
        var x = 0
        var y = 0

        // visit starting house
        houses.add(Point2d(0, 0))

        moves.forEach { move ->
            when (move) {
                '^' -> x++
                'v' -> x--
                '>' -> y++
                '<' -> y--
            }

            houses.add(Point2d(x, y))
        }

        return houses.size
    }

    private fun visitHouses2(moves: String): Int {
        val houses = HashSet<Point2d>()
        var x = 0
        var y = 0

        val action = { move: Char ->
            when (move) {
                '^' -> x++
                'v' -> x--
                '>' -> y++
                '<' -> y--
            }

            Point2d(x, y)
        }

        // visit starting house
        houses.add(Point2d(0, 0))

        // visits of santa
        houses.addAll(moves.filterIndexed { index, _ -> index % 2 == 0 }.map(action))

        x = 0
        y = 0
        // visits of robo santa
        houses.addAll(moves.filterIndexed { index, _ -> index % 2 == 1 }.map(action))

        return houses.size
    }
}