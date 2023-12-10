import java.io.File

/*
 * Copyright (c) 2023. by Dominic Gernert
 */

fun main() {
    val day10 = Day10(File("src/main/resources/day10.txt").bufferedReader().readLines())

    println("Part 1 - ${day10.solvePart1()}")
    println("Part 2 - ${day10.solvePart2()}")
}

class Day10(input: List<String>) {
    private val grid = Grid(input)
    fun solvePart1(): Int = grid.loopLength / 2

    fun solvePart2(): Int {
        var nbrOfEnclosedTiles = 0

        val red = "\u001b[31m"
        val green = "\u001b[32m"
        val reset = "\u001b[0m"

        grid.tiles.forEach { row ->
            row.forEach { pipe ->
                if (grid.loopPipes.contains(pipe)) {
                    print("${green}${pipe.type}$reset")
                } else if (grid.loopPolygon.contains(Point2d(pipe.x, pipe.y))) {
                    nbrOfEnclosedTiles++
                    print("${red}I$reset")
                } else
                    print(pipe.type)
            }
            println()
        }

        return nbrOfEnclosedTiles
    }
}

internal data class Pipe(
    val type: Char,
    val x: Int,
    val y: Int,
    val connectedTo: List<Pipe> = mutableListOf(),
) {
    companion object {
        val NULL_PIPE = Pipe(' ', -1, -1)
    }

    override fun hashCode(): Int = Pair(x, y).hashCode()

    override fun toString(): String = type.toString()

    override fun equals(other: Any?): Boolean {
        other as Pipe

        return x == other.x && y == other.y
    }
}

internal class Grid(input: List<String>) {
    val tiles: Array<Array<Pipe>>
    var startPipe: Pipe = Pipe.NULL_PIPE
    val loopPipes: HashSet<Pipe> = HashSet()
    var loopLength: Int = 0
    val loopPolygon: Polygon = Polygon()

    init {
        tiles = Array(input.size) { Array(input[0].length) { Pipe.NULL_PIPE } }
        populateTiles(input)
        connectPipes()
        traverseLoop()
    }

    private fun populateTiles(input: List<String>) {
        input.forEachIndexed { rowIndex, line ->
            line.forEachIndexed { colIndex, c ->
                tiles[rowIndex][colIndex] = Pipe(c, colIndex, rowIndex)

                if (c == 'S')
                    startPipe = tiles[rowIndex][colIndex]
            }
        }
    }

    private fun connectPipes() {
        tiles.forEach { row ->
            row.forEach { pipe ->
                when (pipe.type) {
                    '.' -> {}
                    '|' -> connectVerticalPipe(pipe)
                    '-' -> connectHorizontal(pipe)
                    'L' -> connectL(pipe)
                    'J' -> connectJ(pipe)
                    '7' -> connect7(pipe)
                    'F' -> connectF(pipe)
                    'S' -> connectStart(pipe)
                    else -> throw RuntimeException("UNKNOWN PIPE TYPE ${pipe.type}")
                }
            }
        }
    }

    private fun connectVerticalPipe(pipe: Pipe) {
        connectTop(pipe)
        connectBottom(pipe)
    }

    private fun connectHorizontal(pipe: Pipe) {
        connectLeft(pipe)
        connectRight(pipe)
    }

    private fun connectL(pipe: Pipe) {
        connectTop(pipe)
        connectRight(pipe)
    }

    private fun connectJ(pipe: Pipe) {
        connectTop(pipe)
        connectLeft(pipe)
    }

    private fun connect7(pipe: Pipe) {
        connectBottom(pipe)
        connectLeft(pipe)
    }

    private fun connectF(pipe: Pipe) {
        connectBottom(pipe)
        connectRight(pipe)
    }

    private fun connectStart(pipe: Pipe) {
        connectTop(pipe)
        connectBottom(pipe)
        connectLeft(pipe)
        connectRight(pipe)

        // link the connected pipes back to the start pipe
        pipe.connectedTo.forEach { connection ->
            connection.connectedTo.addLast(pipe)
        }
    }

    private fun connectTop(pipe: Pipe) {
        val top = getOrNull(pipe.x, pipe.y - 1)
        connectIfPossible(pipe, top, setOf('|', '7', 'F'))
    }

    private fun connectBottom(pipe: Pipe) {
        val bottom = getOrNull(pipe.x, pipe.y + 1)
        connectIfPossible(pipe, bottom, setOf('|', 'L', 'J'))
    }

    private fun connectLeft(pipe: Pipe) {
        val left = getOrNull(pipe.x - 1, pipe.y)
        connectIfPossible(pipe, left, setOf('-', 'L', 'F'))
    }

    private fun connectRight(pipe: Pipe) {
        val right = getOrNull(pipe.x + 1, pipe.y)
        connectIfPossible(pipe, right, setOf('-', 'J', '7'))
    }

    private fun connectIfPossible(pipe: Pipe, other: Pipe?, allowedTypes: Set<Char>) {
        if (other != null) {
            if (allowedTypes.contains(other.type))
                pipe.connectedTo.addLast(other)
        }
    }

    private fun getOrNull(x: Int, y: Int): Pipe? {
        if (x < 0 || x > tiles[0].lastIndex || y < 0 || y > tiles.lastIndex) return null
        else return tiles[y][x]
    }

    private fun traverseLoop() {
        // travers the loop beginning by the start pipe
        var currentPipe = startPipe
        var previousPipe = startPipe.connectedTo.first()

        while (true) {
            loopPipes.add(currentPipe)

            val nextPipe =
                if (currentPipe.connectedTo.first() != previousPipe) currentPipe.connectedTo.first()
                else currentPipe.connectedTo.last()

            previousPipe = currentPipe
            currentPipe = nextPipe

            val edge = Edge(Point2d(previousPipe.x, previousPipe.y), Point2d(currentPipe.x, currentPipe.y))
            loopPolygon.edges.add(edge)

            loopLength++

            if (currentPipe.type == 'S')
                break
        }
    }
}