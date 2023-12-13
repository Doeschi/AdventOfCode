import java.io.File

/*
 * Copyright (c) 2023. by Dominic Gernert
 */

fun main() {
    val day12 = Day12(File("src/main/resources/day12.txt").bufferedReader().readLines())

    println("Part 1 - ${day12.solvePart1()}")
    println("Part 2 - ${day12.solvePart2()}")
}

class Day12(input: List<String>) {
    private val records = input.map { Record.of(it) }
    private val calculatedStates = HashMap<Record, Long>()

    fun solvePart1(): Long = records.sumOf { record ->
        calculatedStates.clear()
        getPossibilities(record)
    }

    fun solvePart2(): Long = records.sumOf { record ->
        calculatedStates.clear()

        var unfoldedData = record.data
        val unfoldedGroups = record.groups.toMutableList()

        for (i in 0..3) {
            unfoldedData = "$unfoldedData?${record.data}"
            unfoldedGroups.addAll(record.groups)
        }

        getPossibilities(Record(unfoldedData, unfoldedGroups))
    }

    private fun getPossibilities(recordState: Record): Long {
        if (calculatedStates.contains(recordState))
            return calculatedStates[recordState]!!

        var nbrOfPossibilities = 0L
        var index = 0

        val data = recordState.data
        val groups = recordState.groups
        val nbrOfDamaged = groups.first()

        while (!data.substring(0, index).contains('#') && index + nbrOfDamaged <= data.lastIndex + 1) {
            val possibleGroup = data.substring(index, index + nbrOfDamaged)

            if (!possibleGroup.contains('.')) {
                val indexAfterGroup = index + nbrOfDamaged

                if (indexAfterGroup > data.lastIndex || data[indexAfterGroup] != '#') {
                    if (groups.size == 1) {
                        if (indexAfterGroup <= data.lastIndex) {
                            val dataAfterGroup = data.substring(indexAfterGroup)
                            if (!dataAfterGroup.contains('#'))
                                nbrOfPossibilities++
                        } else
                            nbrOfPossibilities++

                    } else {
                        val newDataStartIndex = indexAfterGroup + 1
                        if (newDataStartIndex > data.lastIndex)
                            break

                        val newData = data.substring(newDataStartIndex)
                        val newGroups = groups.drop(1)

                        if (newData.count { it == '#' } <= newGroups.sumOf { it })
                            nbrOfPossibilities += getPossibilities(Record(newData, newGroups))
                    }
                }
            }

            index++
        }

        calculatedStates[recordState] = nbrOfPossibilities
        return nbrOfPossibilities
    }
}

internal data class Record(val data: String, val groups: List<Int>) {
    companion object {
        fun of(rawString: String): Record {
            val s = rawString.split(" ")
            val data = s.first()
            val groups = s.last().split(",").map { it.toInt() }

            return Record(data, groups)
        }
    }
}