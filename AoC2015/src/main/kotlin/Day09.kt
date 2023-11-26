import java.io.File

/*
 * Copyright (c) 2023. by Dominic Gernert
 */

fun main() {
    val day09 = Day09(File("src/main/resources/day09.txt").bufferedReader().readLines())

    println("Part 1 - lowest cost to travel all cities is ${day09.solvePart1()}")
    println("Part 2 - highest cost to travel all cities is ${day09.solvePart2()}")
}

class Day09(input: List<String>) {
    private val cities = createCities(input)

    fun solvePart1(): Int = cities.minOf { city ->
        findLowestCost(city, cities.size)
    }

    fun solvePart2(): Int = cities.maxOf { city ->
        findHighestCost(city, cities.size)
    }

    private fun findLowestCost(city: City, nbrOfCities: Int, visitedCities: HashSet<City> = hashSetOf(city)): Int {
        val lowestCost = city.connectedTo.filter { entry -> !visitedCities.contains(entry.key) }.minOfOrNull { entry ->
            val newVisitedCities = visitedCities.clone() as HashSet<City>
            newVisitedCities.add(entry.key)
            findLowestCost(entry.key, nbrOfCities, newVisitedCities) + entry.value
        }

        return lowestCost ?: if (visitedCities.size == nbrOfCities)
            0
        else
            Int.MAX_VALUE
    }

    private fun findHighestCost(city: City, nbrOfCities: Int, visitedCities: HashSet<City> = hashSetOf(city)): Int {
        val highestCost = city.connectedTo.filter { entry -> !visitedCities.contains(entry.key) }.maxOfOrNull { entry ->
            val newVisitedCities = visitedCities.clone() as HashSet<City>
            newVisitedCities.add(entry.key)
            findHighestCost(entry.key, nbrOfCities, newVisitedCities) + entry.value
        }

        return if (highestCost == null)
            if (visitedCities.size == nbrOfCities)
                0
            else
                Int.MIN_VALUE
        else
            highestCost
    }

    private fun createCities(input: List<String>): List<City> {
        val c = HashMap<String, City>()

        input.forEach { s ->
            val split = s.split(" = ")
            val cost = split.last().toInt()

            val cities = split.first().split(" to ")
            val name1 = cities.first()
            val name2 = cities.last()

            val city1 = c.getOrPut(name1) { City(name1) }
            val city2 = c.getOrPut(name2) { City(name2) }

            city1.connectedTo[city2] = cost
            city2.connectedTo[city1] = cost
        }

        return c.values.toList()
    }
}

internal data class City(val name: String, val connectedTo: HashMap<City, Int> = HashMap()) {
    override fun hashCode(): Int {
        return name.hashCode()
    }
}