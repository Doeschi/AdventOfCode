import java.io.File
import java.util.*

/*
 * Copyright (c) 2023. by Dominic Gernert
 */

fun main() {
    val day05 = Day05(File("src/main/resources/day05.txt").bufferedReader().readLines())

    println("Part 1 - ${day05.solvePart1()}")
    println("Part 2 - ${day05.solvePart2()}")
}

class Day05(input: List<String>) {
    private val crateStacksPart1 = mapCratesPart1(input)
    private val crateStacksPart2 = mapCratesPart2(input)
    private val instructions = mapInstructions(input)

    fun solvePart1(): String {
        instructions.forEach { instruction ->
            val from = crateStacksPart1[instruction.from]!!
            val to = crateStacksPart1[instruction.to]!!

            for (i in 0..<instruction.nbr) {
                val crate = from.crates.pop()
                to.crates.push(crate)
            }
        }

        return crateStacksPart1.map { it.value.crates.last().toString() }.reduce { acc, c -> "$acc$c" }
    }

    fun solvePart2(): String {
        instructions.forEach { instruction ->
            val from = crateStacksPart2[instruction.from]!!
            val to = crateStacksPart2[instruction.to]!!
            val crates = mutableListOf<Char>()

            for (i in 0..<instruction.nbr) {
                val crate = from.crates.removeLast()
                crates.add(crate)
            }

            to.crates.addAll(crates.reversed())
        }

        return crateStacksPart2.map { it.value.crates.last().toString() }.reduce { acc, c -> "$acc$c" }
    }

    private fun mapCratesPart1(input: List<String>): SortedMap<Int, CrateStackPart1> {
        val stacks = mutableMapOf<Int, CrateStackPart1>()

        input.dropLastWhile { !it.startsWith(" ") }.forEach { line ->
            line.forEachIndexed { index, c ->
                if (c.isLetter()) {
                    val correspondingStack = (index / 4) + 1

                    val stack = stacks.getOrPut(correspondingStack) { CrateStackPart1() }
                    stack.crates.addFirst(c)
                }
            }
        }

        return stacks.toSortedMap()
    }

    private fun mapCratesPart2(input: List<String>): SortedMap<Int, CrateStackPart2> {
        val stacks = mutableMapOf<Int, CrateStackPart2>()

        input.dropLastWhile { !it.startsWith(" ") }.forEach { line ->
            line.forEachIndexed { index, c ->
                if (c.isLetter()) {
                    val correspondingStack = (index / 4) + 1

                    val stack = stacks.getOrPut(correspondingStack) { CrateStackPart2() }
                    stack.crates.addFirst(c)
                }
            }
        }

        return stacks.toSortedMap()
    }

    private fun mapInstructions(input: List<String>): List<Instruction> =
        input.dropWhile { !it.startsWith("move") }.map {
            Instruction.of(it)
        }
}

internal data class CrateStackPart1(val crates: Stack<Char> = Stack())

internal data class CrateStackPart2(val crates: MutableList<Char> = mutableListOf())

internal data class Instruction(val nbr: Int, val from: Int, val to: Int) {
    companion object {
        private val pattern = Regex("""^move (\d+) from (\d+) to (\d+)${'$'}""")

        fun of(rawString: String): Instruction {
            val (nbr, from, to) = pattern.matchEntire(rawString)?.destructured
                ?: throw RuntimeException("CANT PARSE $rawString")

            return Instruction(nbr.toInt(), from.toInt(), to.toInt())
        }
    }
}