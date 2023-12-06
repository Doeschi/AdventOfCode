import java.io.File

/*
 * Copyright (c) 2023. by Dominic Gernert
 */

fun main() {
    val day05 = Day05(File("src/main/resources/day05.txt").bufferedReader().readLines())

    println("Part 1 - ${day05.solvePart1()}")
    println("Part 2 - ${day05.solvePart2()}")
}

class Day05(input: List<String>) {
    private val seeds = getSeeds(input)
    private val maps = getMaps(input)

    fun solvePart1(): Long {
        return seeds.minOf { seed ->
            var src = seed

            maps.forEach { map ->
                for (i in 0..map.sources.lastIndex) {
                    if (map.sources[i].contains(src)) {
                        val foundSrc = map.sources[i]
                        val foundDest = map.destinations[i]

                        src = foundDest.first + (src - foundSrc.first)
                        break
                    }
                }
            }

            src
        }
    }

    fun solvePart2(): Long {
        val seedRanges = mutableListOf<LongRange>()

        for (i in 0..seeds.lastIndex step 2) {
            val start = seeds[i]
            val len = seeds[i + 1]
            seedRanges.add(LongRange(start, start + len - 1))
        }

        val locations = getLocations(seedRanges)

        return locations.minOf { it.first }
    }

    private fun getSeeds(input: List<String>): List<Long> {
        return input.first().split(": ").last().split(" ").map { it.toLong() }
    }

    private fun getMaps(input: List<String>): List<CategoryMap> {
        val maps = mutableListOf<CategoryMap>()

        val m = input.drop(2)

        var categoryMap = CategoryMap()

        m.forEach { line ->
            if (line.isEmpty()) {
                maps.add(categoryMap)
                categoryMap = CategoryMap()
            } else if (line[0].isDigit()) {
                val s = line.split(" ")
                val dest = s[0].toLong()
                val source = s[1].toLong()
                val len = s[2].toLong()

                categoryMap.destinations.addLast(LongRange(dest, dest + len - 1))
                categoryMap.sources.addLast(LongRange(source, source + len - 1))
            }
        }

        maps.add(categoryMap)
        return maps
    }

    private fun getLocations(seeds: MutableList<LongRange>): List<LongRange> {
        var currentSrcRanges = seeds

        maps.forEach { map ->
            val newDestRanges = mutableListOf<LongRange>()

            currentSrcRanges.forEach { currentSrc ->
                val newSrcRanges = mutableListOf<LongRange>()

                for (i in 0..map.sources.lastIndex) {
                    val src = map.sources[i]

                    if (currentSrc.intersects(src)) {
                        val inter = currentSrc.intersection(src)
                        newSrcRanges.add(inter)

                        val destLen = inter.last - inter.first
                        val destStart = map.destinations[i].first + (inter.first - src.first)
                        newDestRanges.add(LongRange(destStart, destStart + destLen))
                    }
                }

                if (newSrcRanges.isEmpty()) {
                    newDestRanges.add(currentSrc)
                } else {
                    newSrcRanges.sortBy { it.first }

                    val first = newSrcRanges.first()
                    if (first.first != currentSrc.first) {
                        newDestRanges.add(LongRange(currentSrc.first, first.first - 1))
                    }

                    val last = newSrcRanges.last()
                    if (last.last != currentSrc.last) {
                        newDestRanges.add(LongRange(last.last + 1, currentSrc.last))
                    }

                    for (i in 0..<newSrcRanges.lastIndex) {
                        val gapStart = newSrcRanges[i].last + 1
                        val gapEnd = newSrcRanges[i + 1].first - 1
                        newDestRanges.add(LongRange(gapStart, gapEnd))
                    }
                }
            }
            currentSrcRanges = newDestRanges

        }

        return currentSrcRanges
    }
}

internal class CategoryMap(
    val destinations: List<LongRange> = mutableListOf(),
    val sources: List<LongRange> = mutableListOf(),
)