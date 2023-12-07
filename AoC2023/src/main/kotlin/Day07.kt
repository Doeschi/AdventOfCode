import java.io.File

/*
 * Copyright (c) 2023. by Dominic Gernert
 */

fun main() {
    val day07 = Day07(File("src/main/resources/day07.txt").bufferedReader().readLines())

    println("Part 1 - ${day07.solvePart1()}")
    println("Part 2 - ${day07.solvePart2()}")
}

class Day07(input: List<String>) {
    private val cardStrengthPart1 = mapOf(
        '2' to 1,
        '3' to 2,
        '4' to 3,
        '5' to 4,
        '6' to 5,
        '7' to 6,
        '8' to 7,
        '9' to 8,
        'T' to 9,
        'J' to 10,
        'Q' to 11,
        'K' to 12,
        'A' to 13,
    )

    private val cardStrengthPart2 = mapOf(
        'J' to 1,
        '2' to 2,
        '3' to 3,
        '4' to 4,
        '5' to 5,
        '6' to 6,
        '7' to 7,
        '8' to 8,
        '9' to 9,
        'T' to 10,
        'Q' to 11,
        'K' to 12,
        'A' to 13,
    )

    private val hands = mapHandsPart1(input)
    private val hands2 = mapHandsPart2(input)

    fun solvePart1(): Int = getResult(hands)

    fun solvePart2(): Int = getResult(hands2)

    private fun getResult(hands: List<Hand>): Int {
        val sorted = hands.sorted()
        var result = 0

        sorted.forEachIndexed { index, hand ->
            result += hand.bid * (index + 1)
        }

        return result
    }

    private fun mapHandsPart1(input: List<String>) = input.map { Hand.ofPart1(it, cardStrengthPart1) }

    private fun mapHandsPart2(input: List<String>) = input.map { Hand.ofPart2(it, cardStrengthPart2) }
}

internal data class Hand(val cards: String, val strength: Int, val bid: Int, val cardStrength: Map<Char, Int>) :
    Comparable<Hand> {
    override fun compareTo(other: Hand): Int {
        if (this.strength != other.strength)
            return this.strength compareTo other.strength

        this.cards.forEachIndexed { index, thisCard ->
            val thisStrength = cardStrength[thisCard]!!
            val otherStrength = cardStrength[other.cards[index]]!!

            if (thisStrength != otherStrength)
                return thisStrength compareTo otherStrength
        }

        return 0
    }

    companion object {

        private val pattern = """^(\w{5}) (\d+)${'$'}""".toRegex()

        fun ofPart1(rawString: String, cardStrength: Map<Char, Int>): Hand {
            val (cards, bid) = pattern.matchEntire(rawString)?.destructured
                ?: throw RuntimeException("CANT PARSE $rawString")

            val count = getCardCount(cards)
            val strength = getStrengthPart1(count)

            return Hand(cards, strength, bid.toInt(), cardStrength)
        }

        fun ofPart2(rawString: String, cardStrength: Map<Char, Int>): Hand {
            val (cards, bid) = pattern.matchEntire(rawString)?.destructured
                ?: throw RuntimeException("CANT PARSE $rawString")

            val count = getCardCount(cards)
            val strength = if (count.containsKey('J')) getStrengthPart2(count) else getStrengthPart1(count)

            return Hand(cards, strength, bid.toInt(), cardStrength)
        }

        private fun getCardCount(cards: String): Map<Char, Int> {
            val cardCount = mutableMapOf<Char, Int>()

            cards.forEach { card ->
                val count = cardCount.getOrPut(card) { 0 }
                cardCount[card] = count + 1
            }

            return cardCount
        }

        private fun getStrengthPart1(cardCount: Map<Char, Int>): Int {
            val c = cardCount.toList()

            if (c.size == 1)
                return 7
            else if (c.size == 2) {
                if (c.first().second == 4 || c.last().second == 4)
                    return 6
                else
                    return 5
            } else if (c.size == 3) {
                c.forEach {
                    if (it.second == 3)
                        return 4
                }
                return 3
            } else if (c.size == 4)
                return 2
            else
                return 1
        }

        private fun getStrengthPart2(cardCount: Map<Char, Int>): Int {
            val c = cardCount.toList()
            val nbrOfJokers = cardCount['J']!!

            if (c.size == 1)
                return 7
            else if (c.size == 2) {
                return 7
            } else if (c.size == 3) {
                if (nbrOfJokers == 1) {
                    val other = if (c.first().first != 'J') c.first().second else c.last().second

                    if (other == 2)
                        return 5
                    else
                        return 6
                } else
                    return 6

            } else if (c.size == 4) {
                return 4
            } else {
                return 2
            }
        }
    }
}