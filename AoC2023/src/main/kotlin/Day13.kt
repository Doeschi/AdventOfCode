import java.io.File

/*
 * Copyright (c) 2023. by Dominic Gernert
 */

fun main() {
    val day13 = Day13(File("src/main/resources/day13.txt").bufferedReader().readText())

    println("Part 1 - ${day13.solvePart1()}")
    println("Part 2 - ${day13.solvePart2()}")
}

class Day13(input: String) {
    private val patterns = getPatterns(input)

    fun solvePart1(): Int {
        var nbrOfCols = 0
        var nbrOfRows = 0

        for (pattern in patterns) {
            // check vertical reflection
            val colRes = searchReflection(pattern.cols)

            if (colRes == -1) {
                // check horizontal reflection
                val rowRes = searchReflection(pattern.rows)

                if (rowRes == -1)
                    println("NO REFLECTION FOUND FOR:\n${pattern.originalInput}")
                else
                    nbrOfRows += rowRes
            } else
                nbrOfCols += colRes
        }

        return nbrOfCols + (nbrOfRows * 100)
    }

    fun solvePart2(): Int {
        var nbrOfCols = 0
        var nbrOfRows = 0

        patternLoop@
        for (p in patterns) {
            val originalColRes = searchReflection(p.cols)
            val originalRowRes = if (originalColRes == -1) searchReflection(p.rows) else -1

            for (rowIndex in 0..p.pattern.lastIndex) {
                val originalRow = p.pattern[rowIndex]
                for (colIndex in 0..originalRow.lastIndex) {
                    p.pattern[rowIndex] = getReplacement(originalRow, colIndex)

                    // check vertical reflection
                    val colRes = searchReflection(p.cols, originalColRes)

                    if (colRes != -1) {
                        nbrOfCols += colRes
                        continue@patternLoop
                    }

                    // check horizontal reflection
                    val rowRes = searchReflection(p.rows, originalRowRes)

                    if (rowRes != -1) {
                        nbrOfRows += rowRes
                        continue@patternLoop
                    }
                }
                p.pattern[rowIndex] = originalRow
            }
        }

        return nbrOfCols + (nbrOfRows * 100)
    }

    private fun getReplacement(input: String, index: Int): String {
        val currentC = input[index]
        val newC = if (currentC == '.') '#' else '.'

        if (index == 0)
            return "$newC${input.substring(1)}"
        else if (index == input.lastIndex)
            return "${input.substring(0, input.lastIndex)}$newC"
        else
            return "${input.substring(0, index)}${newC}${input.substring(index + 1)}"
    }

    private fun searchReflection(input: List<String>, previousReflection: Int = -1): Int {
        outerLoop@
        for (index in 1..input.lastIndex) {
            val nbrOnFirstSide = index
            val nbrOnSecondSide = input.size - nbrOnFirstSide

            val nbrOfRespectedLines = minOf(nbrOnFirstSide, nbrOnSecondSide)

            for (i in 0..<nbrOfRespectedLines) {
                val firstSide = input[index - nbrOfRespectedLines + i]
                val secondSide = input[index + nbrOfRespectedLines - i - 1]

                if (firstSide != secondSide)
                    continue@outerLoop
            }

            if (index != previousReflection)
                return index
        }

        return -1
    }

    private fun getPatterns(input: String): List<ReflectionPattern> {
        return input.split("\r\n\r\n").map { ReflectionPattern.of(it) }
    }
}

internal data class ReflectionPattern(val pattern: MutableList<String>, val originalInput: String, var c: Int = 0) {
    val rows: List<String>
        get() = pattern

    val cols: List<String>
        get() = buildCols()

    val asText: String
        get() = pattern.reduce { acc, s -> "$acc\n$s" }

    private fun buildCols(): List<String> {
        val allCols = mutableListOf<String>()

        for (colIndex in 0..pattern[0].lastIndex) {
            var c = ""
            for (rowIndex in 0..pattern.lastIndex) {
                c += pattern[rowIndex][colIndex]
            }

            allCols.add(c)
        }

        return allCols
    }

    companion object {
        fun of(rawString: String): ReflectionPattern {
            val lines = rawString.split("\r\n")

            val nbrOfRows = lines.size
            val nbrOfCols = lines[0].length

            val pattern = Array(nbrOfRows) { Array(nbrOfCols) { ' ' } }

            lines.forEachIndexed { rowIndex, s ->
                s.forEachIndexed { colIndex, c ->
                    pattern[rowIndex][colIndex] = c
                }
            }

            return ReflectionPattern(lines.toMutableList(), rawString)
        }
    }
}