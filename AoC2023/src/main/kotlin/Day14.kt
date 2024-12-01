import java.io.File

/*
 * Copyright (c) 2023. by Dominic Gernert
 */

fun main() {
    val day14 = Day14(File("src/main/resources/day14.txt").bufferedReader().readLines())

    println("Part 1 - ${day14.solvePart1()}")
    println("Part 2 - ${day14.solvePart2()}")
}

class Day14(input: List<String>) {
    private val platformPart1 = getPlatform(input)
    private val platformPart2 = getPlatform(input)

    fun solvePart1(): Int {
        platformPart1.tilt2(-1, 0)
        return platformPart1.getLoad()
    }

    fun solvePart2(): Int {
        outerLoop@
        for (i in 0..<1_000_000_000) {
            val beginState = platformPart2.grid
            val b = platformPart2.asText

            // north
            platformPart2.tilt2(-1, 0)

            // west
            platformPart2.tilt2(0, -1)

            // south
            platformPart2.tilt2(1, 0)

            // east
//            platformPart2.grid.forEach { it.reverse() }
            platformPart2.tilt2(0, 1)

            // reset
//            platformPart2.grid.reverse()
//            platformPart2.grid.forEach { it.reverse() }

            val endState = platformPart2.grid
            val e = platformPart2.asText


            for(k in 0..beginState.lastIndex){
                for (j in 0..beginState[0].lastIndex)
                    if (beginState[k][j] != endState[k][j])
                        continue@outerLoop
            }

            break
        }

        return platformPart2.getLoad()
    }

    private fun getPlatform(input: List<String>): Platform {
        val rows = input.size
        val column = input[0].length

        val grid = Array(rows) { Array(column) { ' ' } }

        input.forEachIndexed { rowIndex, line ->
            line.forEachIndexed { colIndex, c ->
                grid[rowIndex][colIndex] = c
            }
        }

        return Platform(grid)
    }
}

internal data class Platform(val grid: Array<Array<Char>>) {

    val asText: String
        get() {
            return grid.map {
                it.map {
                    it.toString()
                }.reduce { acc, s -> "$acc$s" }
            }.reduce { acc, s -> "$acc\n$s" }
        }

    fun tiltNorth() {
        for (rowIndex in 1..grid.lastIndex) {
            for (colIndex in 0..grid[0].lastIndex) {
                val c = grid[rowIndex][colIndex]

                if (c == 'O') {
                    var newPos = rowIndex
                    while (newPos > 0) {
                        if (grid[newPos - 1][colIndex] != '.') {
                            break
                        }
                        newPos--
                    }

                    grid[rowIndex][colIndex] = '.'
                    grid[newPos][colIndex] = 'O'

                }
            }
        }
    }

    fun tilt(rowOffset: Int, colOffset: Int) {
        for (rowIndex in 0..grid.lastIndex) {
            for (colIndex in 0..grid[0].lastIndex) {
                val c = grid[rowIndex][colIndex]

                if (c == 'O') {
                    var newRowPos = rowIndex
                    var newColPos = colIndex

                    while (newRowPos in 0..grid.lastIndex && newColPos in 0..grid[0].lastIndex) {
                        if (newRowPos + rowOffset !in 0..grid.lastIndex || newColPos + colOffset !in 0..grid[0].lastIndex)
                            break

                        if (grid[newRowPos + rowOffset][newColPos + colOffset] != '.') {
                            break
                        }

                        newRowPos += rowOffset
                        newColPos += colOffset
                    }

                    grid[rowIndex][colIndex] = '.'
                    grid[newRowPos][newColPos] = 'O'
                }
            }
        }
    }

    fun tilt2(rowOffset: Int, colOffset: Int) {
        var rowIndex = if (rowOffset == -1) 0 else grid.lastIndex

        val rowIncrement = if (rowOffset == -1) 1 else -1
        val colIncrement = if (colOffset == -1) 1 else -1

        while (rowIndex in 0..grid.lastIndex) {
            var colIndex = if (colOffset == -1) 0 else grid[0].lastIndex
            while (colIndex in 0..grid[0].lastIndex) {
                val c = grid[rowIndex][colIndex]

                if (c == 'O') {
                    var newRowPos = rowIndex
                    var newColPos = colIndex

                    while (newRowPos in 0..grid.lastIndex && newColPos in 0..grid[0].lastIndex) {
                        if (newRowPos + rowOffset !in 0..grid.lastIndex || newColPos + colOffset !in 0..grid[0].lastIndex)
                            break

                        if (grid[newRowPos + rowOffset][newColPos + colOffset] != '.') {
                            break
                        }

                        newRowPos += rowOffset
                        newColPos += colOffset
                    }

                    grid[rowIndex][colIndex] = '.'
                    grid[newRowPos][newColPos] = 'O'
                }

                colIndex += colIncrement
            }
            rowIndex += rowIncrement
        }
    }


    fun getLoad(): Int {
        var total = 0
        grid.forEachIndexed { rowIndex, line ->
            line.forEach { c ->
                if (c == 'O') {
                    total += grid.size - rowIndex
                }
            }
        }

        return total
    }
}