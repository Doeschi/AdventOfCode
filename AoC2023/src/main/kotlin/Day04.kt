import java.io.File
import kotlin.math.pow

/*
 * Copyright (c) 2023. by Dominic Gernert
 */

fun main() {
    val day04 = Day04(File("src/main/resources/day04.txt").bufferedReader().readLines())

    println("Part 1 - ${day04.solvePart1()}")
    println("Part 2 - ${day04.solvePart2()}")
}

class Day04(input: List<String>) {
    private val cards = mapCards(input)

    fun solvePart1(): Int = cards.sumOf { card ->
        val matching = card.winningNumbers.count { card.ownNumbers.contains(it) }

        if (matching == 0)
            0
        else
            ((2.0).pow(matching - 1)).toInt()
    }

    fun solvePart2(): Int {
        cards.forEachIndexed { index, card ->
            val matching = card.winningNumbers.count { card.ownNumbers.contains(it) }
            val start = index + 1
            val end = minOf(cards.lastIndex, index + matching)

            for (i in start..end) {
                cards[i].nbrOfCopies += card.nbrOfCopies
            }
        }

        return cards.sumOf { it.nbrOfCopies }
    }

    private fun mapCards(input: List<String>): List<ScratchCard> = input.map { ScratchCard.of(it) }
}

internal class ScratchCard(val winningNumbers: List<Int>, val ownNumbers: List<Int>, var nbrOfCopies: Int = 1) {

    companion object {
        fun of(rawString: String): ScratchCard {
            val s = rawString.split(": ")
            val n = s.last().split(" | ")

            val winning = n.first().split(" ").filter { it != "" }.map { it.toInt() }
            val own = n.last().split(" ").filter { it != "" }.map { it.toInt() }

            return ScratchCard(winning, own)
        }
    }
}