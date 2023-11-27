import java.io.File

/*
 * Copyright (c) 2023. by Dominic Gernert
 */

fun main() {
    val day15 = Day15(File("src/main/resources/day15.txt").bufferedReader().readLines())

    println("Part 1 - max score is ${day15.solvePart1()}")
    println("Part 2 - max score for a cookie with 500 calories is ${day15.solvePart2()}")
}

class Day15(input: List<String>) {
    private val ingredients = parseIngredients(input)

    fun solvePart1(): Int {
        var maxScore = 0

        for (first in 0..100) {
            for (second in 0..100 - first) {
                for (third in 0..100 - second) {
                    for (fourth in 0..100 - third) {
                        if (first + second + third + fourth != 100)
                            continue

                        val newScore = getScore1(listOf(first, second, third, fourth))

                        if (newScore > maxScore)
                            maxScore = newScore
                    }
                }
            }
        }

        return maxScore
    }

    fun solvePart2(): Int {
        var maxScore = 0

        for (first in 0..100) {
            for (second in 0..100 - first) {
                for (third in 0..100 - second) {
                    for (fourth in 0..100 - third) {
                        if (first + second + third + fourth != 100)
                            continue

                        val newScore = getScore2(listOf(first, second, third, fourth))

                        if (newScore > maxScore)
                            maxScore = newScore

                    }
                }
            }
        }

        return maxScore
    }

    private fun getScore2(nbrOfTeaspoons: List<Int>): Int {
        var cal = 0

        ingredients.forEachIndexed { index, ingredient ->
            cal += nbrOfTeaspoons[index] * ingredient.calories
        }

        return if (cal == 500)
            getScore1(nbrOfTeaspoons)
        else
            0
    }

    private fun getScore1(nbrOfTeaspoons: List<Int>): Int {
        var cap = 0
        var dur = 0
        var flav = 0
        var text = 0

        ingredients.forEachIndexed { index, ingredient ->
            cap += nbrOfTeaspoons[index] * ingredient.capacity
            dur += nbrOfTeaspoons[index] * ingredient.durability
            flav += nbrOfTeaspoons[index] * ingredient.flavor
            text += nbrOfTeaspoons[index] * ingredient.texture
        }

        return maxOf(0, cap) * maxOf(0, dur) * maxOf(0, flav) * maxOf(0, text)
    }

    private fun parseIngredients(input: List<String>): List<Ingredient> = input.map { Ingredient.of(it) }
}

internal data class Ingredient(
    val name: String,
    val capacity: Int,
    val durability: Int,
    val flavor: Int,
    val texture: Int,
    val calories: Int,
) {

    companion object {
        private val pattern =
            """^(\S+): capacity (-?\d+), durability (-?\d+), flavor (-?\d+), texture (-?\d+), calories (-?\d+)$""".toRegex()

        fun of(rawInput: String): Ingredient {
            val (name, cap, dur, flav, text, cal) = pattern.matchEntire(rawInput)?.destructured
                ?: throw RuntimeException("COULD NOT PARSE INPUT $rawInput")

            return Ingredient(name, cap.toInt(), dur.toInt(), flav.toInt(), text.toInt(), cal.toInt())
        }
    }
}