package day15

import javafx.geometry.Pos
import tornadofx.App
import tornadofx.View
import tornadofx.label
import tornadofx.vbox
import utils.InputReader
import kotlin.collections.set
import kotlin.math.min

internal data class Cell(
    val riskLevel: Int,
    val pos: Pair<Int, Int>,
    val connectedCells: ArrayList<Cell> = ArrayList(),
    var cost: Int = Int.MAX_VALUE,
    var previous: Cell? = null
) {
    override fun hashCode(): Int {
        return pos.hashCode()
    }
}

private val grid = HashMap<Pair<Int, Int>, Cell>()

fun main() {
    val input = InputReader.readInputStringList("src/main/kotlin/day15/input.txt")

    val startAndEndFirstGrid = constructFirstGrid(input)
    val lowestTotalOfRisk = findPathWithLowestTotalOfRisk(startAndEndFirstGrid.first, startAndEndFirstGrid.second)
    println("Small grid path has a total risk of $lowestTotalOfRisk")

    val startAndEndSecondGrid = constructSecondGrid(input)
    val lowestTotalOfRisk2 = findPathWithLowestTotalOfRisk(startAndEndSecondGrid.first, startAndEndSecondGrid.second)
    println("Large grid path has a total risk of $lowestTotalOfRisk2")
    // launch<DemoApp>()
}

private fun findPathWithLowestTotalOfRisk(start: Cell, end: Cell): Int {
    val checkedCells = mutableSetOf<Cell>()
    val possibleCells = HashSet<Cell>()
    val dummyCell = Cell(Int.MAX_VALUE, Pair(0, 0))
    var currentCell = start
    currentCell.cost = 0

    while (!checkedCells.contains(end)) {
        for (cell in currentCell.connectedCells) {
            if (!checkedCells.contains(cell)) {
                val newCost = min(cell.cost, currentCell.cost + cell.riskLevel)
                if (newCost < cell.cost) {
                    cell.cost = newCost
                    cell.previous = currentCell
                }

                possibleCells.add(cell)
            }
        }

        var lowestRiskCell = dummyCell

        possibleCells.forEach { cell ->
            if (cell.cost <= lowestRiskCell.cost) {
                lowestRiskCell = cell
            }
        }

        if (lowestRiskCell == dummyCell)
            throw RuntimeException("end is not connected with the start")

        checkedCells.add(lowestRiskCell)
        possibleCells.remove(lowestRiskCell)
        currentCell = lowestRiskCell
        println("lowest: ${lowestRiskCell.riskLevel}, ${lowestRiskCell.cost}")
    }

    return end.cost
}

private fun constructSecondGrid(input: ArrayList<String>): Pair<Cell, Cell> {
    val scale = 5
    val rowsSmallGrid = input.size
    val columnsSmallGrid = input[0].length

    input.forEachIndexed { row, line ->
        line.forEachIndexed { column, riskLevel ->
            val key = Pair(column, row)
            grid[key] = Cell(riskLevel.digitToInt(), key)
        }
    }

    for (row in 0..<(rowsSmallGrid * scale)) {
        for (column in 0..<(columnsSmallGrid * scale)) {
            if (row >= rowsSmallGrid || column >= columnsSmallGrid) {
                val key = Pair(column, row)

                val baseRiskLevel = grid[Pair(column % columnsSmallGrid, row % rowsSmallGrid)]!!.riskLevel
                val additionalRiskLevel = (column / columnsSmallGrid) + (row / rowsSmallGrid)
                var riskLevel = baseRiskLevel + additionalRiskLevel

                if (riskLevel > 9)
                    riskLevel = (riskLevel % 10) + 1

                grid[key] = Cell(riskLevel, key)
            }
        }
    }

    for (x in 0..<(columnsSmallGrid * scale)) {
        for (y in 0..<(rowsSmallGrid * scale)) {
            val cell = grid[Pair(x, y)]!!

            connectNeighbor(cell, x, y + 1)
            connectNeighbor(cell, x + 1, y)
            connectNeighbor(cell, x, y - 1)
            connectNeighbor(cell, x - 1, y)
        }
    }

    return Pair(grid[Pair(0, 0)]!!, grid[Pair((columnsSmallGrid * 5) - 1, (rowsSmallGrid * 5) - 1)]!!)
}

private fun constructFirstGrid(input: ArrayList<String>): Pair<Cell, Cell> {
    val rows = input.size
    val columns = input[0].length

    input.forEachIndexed { row, line ->
        line.forEachIndexed { column, riskLevel ->
            val key = Pair(column, row)
            grid[key] = Cell(riskLevel.digitToInt(), key)
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

    return Pair(grid[Pair(0, 0)]!!, grid[Pair(columns - 1, rows - 1)]!!)
}

private fun connectNeighbor(cell: Cell, neighborX: Int, neighborY: Int) {
    val key = Pair(neighborX, neighborY)
    if (grid.contains(key))
        cell.connectedCells.add(grid[key]!!)
}

/**
 * The main class of the board application.
 * @see App
 */
class DemoApp : App(DemoView::class) {
    init {
        println("on startup")
    }
}

class DemoView() : View("Demo View") {
    override val root = vbox {
        minWidth = 600.0
        minHeight = 800.0
        alignment = Pos.CENTER

        label("hello world")
    }
}