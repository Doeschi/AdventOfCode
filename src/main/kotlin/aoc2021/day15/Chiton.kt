package aoc2021.day15

import utils.InputReader

internal data class Cell(val riskLevel: Int, val connectedCells: ArrayList<Cell> = ArrayList())

private val grid = HashMap<Pair<Int, Int>, Cell>()

fun main() {
    val input = InputReader.readInputStringList("src/main/kotlin/aoc2021/day15/input.txt")
    constructGrid(input)
    val lowestTotalOfRisk = findPathWithLowestTotalOfRisk(0, 0, hashSetOf())

    println("Path has a total risk of $lowestTotalOfRisk")
}

private fun findPathWithLowestTotalOfRisk(
    x: Int,
    y: Int,
    visitedCells: HashSet<Pair<Int, Int>>
): Int {

    return 0
}

private fun constructGrid(input: ArrayList<String>) {
    val rows = input.size
    val columns = input[0].length

    input.forEachIndexed { row, line ->
        line.forEachIndexed { column, riskLevel ->
            grid[Pair(column, row)] = Cell(riskLevel.digitToInt())
        }
    }

    for (x in 0..<columns) {
        for (y in 0..<rows) {
            val cell = grid[Pair(x, y)]!!

            connectNeighbor(cell, x, y + 1)
            connectNeighbor(cell, x + 1, y)
            connectNeighbor(cell, x, y - 1)
            connectNeighbor(cell, x - 1, y)
        }
    }
}

private fun connectNeighbor(cell: Cell, neighborX: Int, neighborY: Int) {
    val key = Pair(neighborX, neighborY)
    if (grid.contains(key))
        cell.connectedCells.add(grid[key]!!)
}