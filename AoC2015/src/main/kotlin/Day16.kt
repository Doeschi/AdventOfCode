import java.io.File

/*
 * Copyright (c) 2023. by Dominic Gernert
 */

fun main() {
    val day16 = Day16(File("src/main/resources/day16.txt").bufferedReader().readLines())

    println("Part 1 - aunt ${day16.solvePart1()} send the gift")
    println("Part 2 - aunt ${day16.solvePart2()} send the gift actually")
}

class Day16(private val input: List<String>) {

    fun solvePart1(): Int {
        val auntsSue = parseAunts(input)
        for (auntSue in auntsSue.toList()) {
            if (auntSue.children != -1 && auntSue.children != 3)
                auntsSue.remove(auntSue)

            if (auntSue.cats != -1 && auntSue.cats != 7)
                auntsSue.remove(auntSue)

            if (auntSue.samoyeds != -1 && auntSue.samoyeds != 2)
                auntsSue.remove(auntSue)

            if (auntSue.pomeranians != -1 && auntSue.pomeranians != 3)
                auntsSue.remove(auntSue)

            if (auntSue.akitas != -1 && auntSue.akitas != 0)
                auntsSue.remove(auntSue)

            if (auntSue.vizslas != -1 && auntSue.vizslas != 0)
                auntsSue.remove(auntSue)

            if (auntSue.goldfish != -1 && auntSue.goldfish != 5)
                auntsSue.remove(auntSue)

            if (auntSue.trees != -1 && auntSue.trees != 3)
                auntsSue.remove(auntSue)

            if (auntSue.cars != -1 && auntSue.cars != 2)
                auntsSue.remove(auntSue)

            if (auntSue.perfumes != -1 && auntSue.perfumes != 1)
                auntsSue.remove(auntSue)
        }

        if (auntsSue.size != 1)
            throw RuntimeException("THERE ARE MORE THEN 1 AUNTS LEFT")
        else
            return auntsSue.first().number
    }

    fun solvePart2(): Int {
        val auntsSue = parseAunts(input)
        for (auntSue in auntsSue.toList()) {
            if (auntSue.children != -1 && auntSue.children != 3)
                auntsSue.remove(auntSue)

            if (auntSue.cats != -1 && auntSue.cats <= 7)
                auntsSue.remove(auntSue)

            if (auntSue.samoyeds != -1 && auntSue.samoyeds != 2)
                auntsSue.remove(auntSue)

            if (auntSue.pomeranians != -1 && auntSue.pomeranians >= 3)
                auntsSue.remove(auntSue)

            if (auntSue.akitas != -1 && auntSue.akitas != 0)
                auntsSue.remove(auntSue)

            if (auntSue.vizslas != -1 && auntSue.vizslas != 0)
                auntsSue.remove(auntSue)

            if (auntSue.goldfish != -1 && auntSue.goldfish >= 5)
                auntsSue.remove(auntSue)

            if (auntSue.trees != -1 && auntSue.trees <= 3)
                auntsSue.remove(auntSue)

            if (auntSue.cars != -1 && auntSue.cars != 2)
                auntsSue.remove(auntSue)

            if (auntSue.perfumes != -1 && auntSue.perfumes != 1)
                auntsSue.remove(auntSue)
        }

        if (auntsSue.size != 1)
            throw RuntimeException("THERE ARE MORE THEN 1 AUNTS LEFT")
        else
            return auntsSue.first().number
    }

    private fun parseAunts(input: List<String>): MutableSet<AuntSue> = input.map { AuntSue.of(it) }.toMutableSet()
}

internal data class AuntSue(
    val number: Int,
    val children: Int,
    val cats: Int,
    val samoyeds: Int,
    val pomeranians: Int,
    val akitas: Int,
    val vizslas: Int,
    val goldfish: Int,
    val trees: Int,
    val cars: Int,
    val perfumes: Int,
) {
    companion object {
        private val namePattern = """^Sue (\d+):.+""".toRegex()
        private val childrenPattern = """.+children: (\d+).*""".toRegex()
        private val catPattern = """.+cats: (\d+).*""".toRegex()
        private val samoyedsPattern = """.+samoyeds: (\d+).*""".toRegex()
        private val pomeraniansPattern = """.+pomeranians: (\d+).*""".toRegex()
        private val akitasPattern = """.+akitas: (\d+).*""".toRegex()
        private val vizslasPattern = """.+vizslas: (\d+).*""".toRegex()
        private val goldfishPattern = """.+goldfish: (\d+).*""".toRegex()
        private val treesPattern = """.+trees: (\d+).*""".toRegex()
        private val carsPattern = """.+cars: (\d+).*""".toRegex()
        private val perfumesPattern = """.+perfumes: (\d+).*""".toRegex()
        fun of(rawString: String): AuntSue {
            val (number) = namePattern.matchEntire(rawString)?.destructured ?: throw RuntimeException("No number found")
            val children = getNumber(rawString, childrenPattern)
            val cats = getNumber(rawString, catPattern)
            val samoyeds = getNumber(rawString, samoyedsPattern)
            val pomeranians = getNumber(rawString, pomeraniansPattern)
            val akitas = getNumber(rawString, akitasPattern)
            val vizslas = getNumber(rawString, vizslasPattern)
            val goldfish = getNumber(rawString, goldfishPattern)
            val trees = getNumber(rawString, treesPattern)
            val cars = getNumber(rawString, carsPattern)
            val perfumes = getNumber(rawString, perfumesPattern)

            return AuntSue(
                number.toInt(),
                children,
                cats,
                samoyeds,
                pomeranians,
                akitas,
                vizslas,
                goldfish,
                trees,
                cars,
                perfumes
            )
        }

        private fun getNumber(rawString: String, pattern: Regex): Int {
            val (n) = pattern.matchEntire(rawString)?.destructured ?: return -1
            return n.toInt()
        }
    }
}