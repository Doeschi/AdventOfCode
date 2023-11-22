package day12

import utils.InputReader

internal data class Cave(
    val name: String,
    val isLarge: Boolean,
    val connectedCaves: ArrayList<Cave> = ArrayList()
)

private val allFoundPaths = mutableSetOf<String>()
fun main() {
    val input = InputReader.readInputStringList("src/main/kotlin/aoc2021/day12/input.txt")
    val startCave = generateCaves(input)
    val numberOfPaths = countPathsPart1(startCave, setOf("start"))
    println("There are $numberOfPaths paths")

    val smallCavesName = getSmallCavesName(input)
    var numberOfPaths2 = 0
    smallCavesName.forEach { caveName ->
        val n = countPathsPart2(startCave, setOf("start"), listOf("start"), caveName, false)
        numberOfPaths2 += n

        println("There are $n paths when you can visit '$caveName' twice")

    }
    println("There are $numberOfPaths2 paths when you can visit one small cave twice")
}

private fun countPathsPart2(
    cave: Cave,
    visitedSmallCaves: Set<String>,
    pathSoFar: List<String> = ArrayList(),
    nameOfSmallCave: String,
    smallCaveVisitedTwice: Boolean
): Int {
    if (cave.name == "end") {
        val path = pathSoFar.joinToString { pathElement -> pathElement }

        return if (allFoundPaths.contains(path))
            0
        else {
            allFoundPaths.add(path)
            1
        }
    }

    var numberOfPaths = 0

    cave.connectedCaves.forEach { c ->
        val newVisitedCaves = visitedSmallCaves.toMutableSet()
        val newPathSoFar = pathSoFar.toMutableList()
        newPathSoFar.add(c.name)

        if (c.isLarge || !visitedSmallCaves.contains(c.name)) {
            if (!c.isLarge)
                newVisitedCaves.add(c.name)

            numberOfPaths += countPathsPart2(c, newVisitedCaves, newPathSoFar, nameOfSmallCave, smallCaveVisitedTwice)
        }

        if (c.name == nameOfSmallCave && !smallCaveVisitedTwice)
            numberOfPaths += countPathsPart2(c, newVisitedCaves, newPathSoFar, nameOfSmallCave, true)
    }

    return numberOfPaths
}

private fun getSmallCavesName(input: ArrayList<String>): List<String> {
    val smallCaves = mutableSetOf<String>()

    input.forEach { connection ->
        val c = connection.split("-")

        val firstCave = c[0]
        if (firstCave.first().isLowerCase() && firstCave != "start" && firstCave != "end")
            smallCaves.add(firstCave)

        val secondCave = c[1]
        if (secondCave.first().isLowerCase() && secondCave != "start" && secondCave != "end")
            smallCaves.add(secondCave)
    }

    return smallCaves.toList()
}

private fun countPathsPart1(cave: Cave, visitedSmallCaves: Set<String>): Int {
    if (cave.name == "end")
        return 1

    var numberOfPaths = 0

    cave.connectedCaves.forEach { c ->
        if (c.isLarge || !visitedSmallCaves.contains(c.name)) {
            val newVisitedCaves = visitedSmallCaves.toMutableSet()
            if (!c.isLarge)
                newVisitedCaves.add(c.name)

            numberOfPaths += countPathsPart1(c, newVisitedCaves)
        }
    }

    return numberOfPaths
}

private fun generateCaves(input: ArrayList<String>): Cave {
    val caves = hashMapOf<String, Cave>()

    input.forEach { connection ->
        val c = connection.split("-")

        val firstCaveName = c[0]
        val firstCave = if (!caves.containsKey(firstCaveName)) {
            val isLarge = firstCaveName.first().isUpperCase()
            val cave = Cave(firstCaveName, isLarge)
            caves[firstCaveName] = cave
            cave
        } else
            caves[firstCaveName]!!

        val secondCaveName = c[1]
        val secondCave = if (!caves.containsKey(secondCaveName)) {
            val isLarge = secondCaveName.first().isUpperCase()
            val cave = Cave(secondCaveName, isLarge)
            caves[secondCaveName] = cave
            cave
        } else
            caves[secondCaveName]!!

        firstCave.connectedCaves.add(secondCave)
        secondCave.connectedCaves.add(firstCave)
    }

    return caves["start"]!!
}