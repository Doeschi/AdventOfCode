import java.io.File

/*
 * Copyright (c) 2023. by Dominic Gernert
 */

fun main() {
    val day06 = Day06(File("src/main/resources/day06.txt").bufferedReader().readLines())

    println("Part 1 - ${day06.solvePart1()}")
    println("Part 2 - ${day06.solvePart2()}")
}

class Day06(input: List<String>) {
    private val races = mapRaces(input)
    private val singleRace = mapSingleRace(input)

    fun solvePart1(): Int {
        var result = 1

        races.forEach { race ->
            result *= getNbrOfPossibilities(race)
        }

        return result
    }

    fun solvePart2(): Int {
        return getNbrOfPossibilities(singleRace)
    }

    private fun getNbrOfPossibilities(race: Race): Int {
        var p = 0

        for (i in 0..race.time) {
            if (i * (race.time - i) > race.distance)
                p++
        }

        return p
    }

    private fun mapRaces(input: List<String>): List<Race> {
        val regex = Regex("""\d+""")
        val time = regex.findAll(input.first()).toList()
        val dist = regex.findAll(input.last()).toList()

        return time.mapIndexed { index, t -> Race(t.value.toLong(), dist[index].value.toLong()) }
    }

    private fun mapSingleRace(input: List<String>): Race {
        val regex = Regex("""\d+""")
        val time = regex.findAll(input.first()).toList().map { it.value }.reduce { acc, s -> "$acc$s" }.toLong()
        val dist = regex.findAll(input.last()).toList().map { it.value }.reduce { acc, s -> "$acc$s" }.toLong()

        return Race(time, dist)
    }
}

internal data class Race(val time: Long, val distance: Long)