import java.io.File

/*
 * Copyright (c) 2023. by Dominic Gernert
 */

fun main() {
    val day17 = Day17(File("src/main/resources/day17.txt").bufferedReader().readLines().map { it.toInt() })

    println("Part 1 - ${day17.solvePart1()} combinations found")
    println("Part 2 - ${day17.solvePart2()} combinations")
}

class Day17(input: List<Int>) {
    private val containers = input.mapIndexed { index, volume -> Container(index, volume) }
    private val foundCombinations = HashSet<Combination>()

    init {
        fill(containers)
    }

    fun solvePart1(): Int = foundCombinations.size

    fun solvePart2(): Int {
        var nbrOfCombinations = 0
        var lowestNbrOfContainers = Int.MAX_VALUE

        foundCombinations.forEach { combination ->
            if (combination.containers.size == lowestNbrOfContainers)
                nbrOfCombinations++
            else if (combination.containers.size < lowestNbrOfContainers) {
                lowestNbrOfContainers = combination.containers.size
                nbrOfCombinations = 1
            }
        }

        return nbrOfCombinations
    }

    private fun fill(
        unusedContainers: List<Container>,
        usedContainers: List<Int> = mutableListOf(),
        currentSum: Int = 0,
        combinedSum: Int = 150,
    ) {

        unusedContainers.forEach { cont ->
            val sum = currentSum + cont.volume

            if (sum == combinedSum) {
                val usedCont = usedContainers.toMutableList()
                usedCont.add(cont.id)
                usedCont.sort()

                val comb = Combination(usedCont.toTypedArray())
                if (!foundCombinations.contains(comb)) {
                    foundCombinations.add(comb)
                }

            } else if (sum < combinedSum) {
                val newUsed = usedContainers.toMutableList()
                newUsed.add(cont.id)

                val newUnused = unusedContainers.toMutableList()
                newUnused.remove(cont)
                fill(newUnused, newUsed, sum)
            }
        }
    }
}

internal data class Container(val id: Int, val volume: Int)

internal data class Combination(val containers: Array<Int>) {
    override fun equals(other: Any?): Boolean {
        if (javaClass != other?.javaClass) return false

        other as Combination
        return containers.contentEquals(other.containers)
    }

    override fun hashCode(): Int {
        return containers.contentHashCode()
    }
}