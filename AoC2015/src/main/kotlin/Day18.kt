import java.io.File
import kotlin.io.path.fileVisitor

/*
 * Copyright (c) 2023. by Dominic Gernert
 */

private typealias Grid = Array<Array<Boolean>>

fun main() {
    val day18 = Day18(File("src/main/resources/day18.txt").bufferedReader().readLines())

    println("Part 1 - ${day18.solvePart1()} lights are on")
    println("Part 2 - ${day18.solvePart2()} lights are on")
}

class Day18(input: List<String>) {
    private val initialGrid = createGrid(input)

    fun solvePart1(): Int {
        val newGrid = stepPart1(initialGrid, 100)
        return countLights(newGrid)
    }

    fun solvePart2(): Int {
        val newGrid = stepPart2(initialGrid, 100)
        return countLights(newGrid)
    }

    private fun countLights(grid: Grid): Int {
        return grid.sumOf { row -> row.sumOf { if (it) 1.toInt() else 0 } }
    }

    private fun stepPart1(grid: Grid, nbrOfSteps: Int = 1): Grid {
        var currentGrid = grid
        var newGrid: Grid

        for (i in 0..<nbrOfSteps) {
            newGrid = Array(currentGrid.size) { Array(currentGrid[0].size) { false } }

            currentGrid.forEachIndexed { rowIndex, row ->
                row.forEachIndexed { columnIndex, on ->
                    val livingNeighbors = countLivingNeighbors(currentGrid, columnIndex, rowIndex)

                    newGrid[rowIndex][columnIndex] = if (on)
                        (livingNeighbors == 2 || livingNeighbors == 3)
                    else
                        livingNeighbors == 3
                }
            }

            currentGrid = newGrid

        }

        return currentGrid
    }

    private fun stepPart2(grid: Grid, nbrOfSteps: Int = 1): Grid {
        var currentGrid = grid
        var newGrid: Grid

        currentGrid[0][0] = true
        currentGrid[0][currentGrid[0].lastIndex] = true
        currentGrid[currentGrid.lastIndex][currentGrid[0].lastIndex] = true
        currentGrid[currentGrid.lastIndex][0] = true

        for (i in 0..<nbrOfSteps) {
            newGrid = Array(currentGrid.size) { Array(currentGrid[0].size) { false } }

            currentGrid.forEachIndexed { rowIndex, row ->
                row.forEachIndexed { columnIndex, on ->
                    val livingNeighbors = countLivingNeighbors(currentGrid, columnIndex, rowIndex)

                    newGrid[rowIndex][columnIndex] = if (on)
                        (livingNeighbors == 2 || livingNeighbors == 3)
                    else
                        livingNeighbors == 3
                }
            }

            newGrid[0][0] = true
            newGrid[0][currentGrid[0].lastIndex] = true
            newGrid[currentGrid.lastIndex][currentGrid[0].lastIndex] = true
            newGrid[currentGrid.lastIndex][0] = true

            currentGrid = newGrid
        }

        return currentGrid
    }

    private fun countLivingNeighbors(grid: Grid, x: Int, y: Int): Int {
        val xMin = maxOf(0, x - 1)
        val xMax = minOf(grid[0].lastIndex, x + 1)
        val yMin = maxOf(0, y - 1)
        val yMax = minOf(grid.lastIndex, y + 1)

        var counter = 0
        for (yIndex in yMin..yMax) {
            for (xIndex in xMin..xMax) {
                if ((xIndex != x || yIndex != y) && grid[yIndex][xIndex])
                    counter++
            }
        }

        return counter
    }

    private fun createGrid(input: List<String>): Grid {
        val columns = input[0].length
        val rows = input.size

        val grid = Array(rows) { Array(columns) { false } }

        input.forEachIndexed { rowIndex, line ->
            line.forEachIndexed { columnIndex, state ->
                grid[rowIndex][columnIndex] = state == '#'
            }
        }

        return grid
    }

    private fun printGrid(grid: Grid) {
        grid.forEach { row ->
            row.forEach { b ->
                print(if (b) '#' else '.')
            }
            println()
        }

        println()
    }
}