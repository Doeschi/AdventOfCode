import java.io.File

/*
 * Copyright (c) 2023. by Dominic Gernert
 */

fun main() {
    val day14 = Day14(File("src/main/resources/day14.txt").bufferedReader().readLines())

    println("Part 1 - winning reindeer has traveled ${day14.solvePart1()} km")
    println("Part 2 - winning reindeer has ${day14.solvePart2()} points")
}

class Day14(input: List<String>) {
    private val reindeer = mapReindeer(input)

    init {
        doRace(2503)
    }

    fun solvePart1(): Int = reindeer.maxOf { it.traveledDistance }

    fun solvePart2(): Int = reindeer.maxOf { it.points }

    private fun doRace(duration: Int): Int {
        val winners = ArrayList<Reindeer>()
        var currentMax: Int

        for (currentTimeStep in 0..duration) {
            currentMax = 0
            winners.clear()

            reindeer.forEach { r ->
                r.stepForward(currentTimeStep)

                if (r.traveledDistance > currentMax) {
                    winners.clear()
                    winners.add(r)
                    currentMax = r.traveledDistance
                } else if (r.traveledDistance == currentMax)
                    winners.add(r)
            }

            winners.forEach { it.awardPoint() }
        }

        return reindeer.maxOf { it.points }
    }

    private fun mapReindeer(input: List<String>): List<Reindeer> = input.map { Reindeer.of(it) }
}

internal data class Reindeer(
    val name: String,
    val speed: Int,
    val flyDuration: Int,
    val restDuration: Int,
    var wakeUpTime: Int = 0,
    var traveledDistance: Int = 0,
    var points: Int = 0,
) {

    fun stepForward(currentTime: Int) {
        if (currentTime >= wakeUpTime) {
            if (wakeUpTime + flyDuration == currentTime)
                wakeUpTime = currentTime + restDuration
            else
                traveledDistance += speed
        }
    }

    fun awardPoint() = points++

    companion object {
        private val pattern =
            """(\S+) can fly (\d+) km/s for (\d+) seconds, but then must rest for (\d+) seconds.$""".toRegex()

        fun of(rawInput: String): Reindeer {
            val (name, speed, flyDuration, restDuration) = pattern.matchEntire(rawInput)?.destructured
                ?: throw RuntimeException("CANT PARSE INPUT")

            return Reindeer(name, speed.toInt(), flyDuration.toInt(), restDuration.toInt())
        }
    }
}