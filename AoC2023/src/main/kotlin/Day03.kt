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
    private val schematic = mapEngine(input)

    fun solvePart1(): Int {
        var colIndex: Int
        var sum = 0

        for (rowIndex in 0..schematic.lastIndex) {
            colIndex = 0

            while (colIndex <= schematic[0].lastIndex) {
                val c = schematic[rowIndex][colIndex]

                if (c.isDigit()) {
                    var i = colIndex + 1
                    var num = c.toString()

                    while (i <= schematic[0].lastIndex && schematic[rowIndex][i].isDigit()) {
                        num += schematic[rowIndex][i]
                        i++
                    }

                    val xMin = maxOf(0, colIndex - 1)
                    val xMax = minOf(schematic[0].lastIndex, i)
                    val yMin = maxOf(0, rowIndex - 1)
                    val yMax = minOf(schematic.lastIndex, rowIndex + 1)

                    var isPartNumber = false

                    outerLoop@
                    for (y in yMin..yMax) {
                        for (x in xMin..xMax) {
                            val neighborC = schematic[y][x]

                            if (neighborC != '.' && !neighborC.isDigit()) {
                                isPartNumber = true
                                break@outerLoop
                            }
                        }
                    }

                    if (isPartNumber) {
                        sum += num.toInt()
                    }

                    colIndex = i
                } else {
                    colIndex++
                }
            }
        }

        return sum
    }

    fun solvePart2(): Int {
        var colIndex: Int
        val gears = HashMap<Pair<Int, Int>, Gear>()

        for (rowIndex in 0..schematic.lastIndex) {
            colIndex = 0

            while (colIndex <= schematic[0].lastIndex) {
                val c = schematic[rowIndex][colIndex]

                if (c.isDigit()) {
                    var i = colIndex + 1
                    var num = c.toString()

                    while (i <= schematic[0].lastIndex && schematic[rowIndex][i].isDigit()) {
                        num += schematic[rowIndex][i]
                        i++
                    }

                    val xMin = maxOf(0, colIndex - 1)
                    val xMax = minOf(schematic[0].lastIndex, i)
                    val yMin = maxOf(0, rowIndex - 1)
                    val yMax = minOf(schematic.lastIndex, rowIndex + 1)

                    for (y in yMin..yMax) {
                        for (x in xMin..xMax) {
                            val neighborC = schematic[y][x]

                            if (neighborC == '*') {
                                val key = Pair(x, y)
                                val g = gears.getOrPut(key) { Gear() }
                                g.surroundingParts.add(num.toInt())
                            }
                        }
                    }

                    colIndex = i
                } else {
                    colIndex++
                }
            }
        }

        var sum = 0

        gears.forEach { (_, gear) ->
            if (gear.surroundingParts.size == 2) {
                sum += gear.surroundingParts[0] * gear.surroundingParts[1]
            }
        }

        return sum
    }

    private fun mapEngine(input: List<String>): Array<Array<Char>> {
        val width = input[0].length
        val height = input.size

        val schematic = Array(height) { Array(width) { ' ' } }

        input.forEachIndexed { rowIndex, line ->
            line.forEachIndexed { colIndex, c ->
                schematic[rowIndex][colIndex] = c
            }
        }

        return schematic
    }
}

internal class Gear(val surroundingParts: MutableList<Int> = mutableListOf())