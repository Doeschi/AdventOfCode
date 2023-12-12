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
    private val calculatedStates = HashMap<State, Long>()

    fun solvePart1(): Long = records.sumOf {
        calculatedStates.clear()
        getPossibilities(it.data, it.groups)
    }

    fun solvePart2(): Long {
        val sum = records.sumOf { record ->
            calculatedStates.clear()

            var unfoldedData = record.data
            val unfoldedGroups = record.groups.toMutableList()

            for (i in 0..3) {
                unfoldedData = "$unfoldedData?${record.data}"
                unfoldedGroups.addAll(record.groups)
            }

            getPossibilities(unfoldedData, unfoldedGroups)
        }

        return sum
    }

    private fun getPossibilities(data: String, groups: List<Int>): Long {
        val state = State(data, groups)

        if (calculatedStates.contains(state))
            return calculatedStates[state]!!

        val nbrOfDamaged = groups.first()
        var nbrOfPossibilities = 0L
        var index = 0

        while (!data.substring(0, index).contains('#') && index + nbrOfDamaged <= data.lastIndex + 1) {
            val possibleGroup = data.substring(index, index + nbrOfDamaged)

            if (!possibleGroup.contains('.')) {
                val indexAfterGroup = index + nbrOfDamaged

                if (indexAfterGroup > data.lastIndex || data[indexAfterGroup] != '#') {
                    if (groups.size == 1) {
                        if (indexAfterGroup <= data.lastIndex) {
                            val dataAfterGroup = data.substring(indexAfterGroup)
                            if (dataAfterGroup.contains('#')) {
                                index++
                                continue
                            } else {
                                nbrOfPossibilities++
                            }
                        } else
                            nbrOfPossibilities++
                    } else {
                        val newDataStartIndex = index + nbrOfDamaged + 1 // indexAfterGroup + 1
                        if (newDataStartIndex > data.lastIndex)
                            break

                        val newData = data.substring(index + nbrOfDamaged + 1)
                        val newGroups = groups.drop(1)

                        if (newData.count { it == '#' } > newGroups.sumOf { it }) {
                            index++
                            continue
                        }

                        nbrOfPossibilities += getPossibilities(newData, newGroups)
                    }
                }
            }

            index++
        }

        calculatedStates[state] = nbrOfPossibilities
        return nbrOfPossibilities
    }

    internal data class State(val data: String, val groups: List<Int>)

    internal class Record(val data: String, val groups: List<Int>) {
        companion object {
            fun of(rawString: String): Record {
                val s = rawString.split(" ")
                val data = s.first()
                val groups = s.last().split(",").map { it.toInt() }

                return Record(data, groups)
            }
        }
    }
}


