import java.io.File

/*
 * Copyright (c) 2023. by Dominic Gernert
 */

fun main() {
    val day02 = Day02(File("src/main/resources/day02.txt").bufferedReader().readLines())

    println("Part 1 - ${day02.solvePart1()} square feet of wrapping paper")
    println("Part 2 - ${day02.solvePart2()} feet of ribbon")
}

class Day02(input: List<String>) {
    private val gifts = mapGifts(input)

    fun solvePart1(): Int {
        return gifts.sumOf { gift ->
            val wl = gift.width * gift.length
            val wh = gift.width * gift.height
            val hl = gift.height * gift.length

            (wl * 2) + (wh * 2) + (hl * 2) + minOf(wl, wh, hl)
        }
    }

    fun solvePart2(): Int {
        return gifts.sumOf { gift ->
            val wl = (gift.width * 2) + (gift.length * 2)
            val wh = (gift.width * 2) + (gift.height * 2)
            val hl = (gift.height * 2) + (gift.length * 2)

            minOf(wl, wh, hl) + (gift.width * gift.length * gift.height)
        }
    }

    private fun mapGifts(input: List<String>): List<Gift> {
        return input.map() { line ->
            val s = line.split("x")
            Gift(s[0].toInt(), s[1].toInt(), s[2].toInt())
        }
    }
}

internal data class Gift(val width: Int, val length: Int, val height: Int)