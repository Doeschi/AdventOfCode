import java.io.File

/*
 * Copyright (c) 2023. by Dominic Gernert
 */

fun main() {
    val day11 = Day11(File("src/main/resources/day11.txt").bufferedReader().readLine())

    println("Part 1 - new password is ${day11.solvePart1()}")
    println("Part 2 - new new password is ${day11.solvePart2()}")
}

class Day11(input: String) {
    private val currentPassword = input

    fun solvePart1(): String = getNextPassword(currentPassword)

    fun solvePart2(): String = getNextPassword(getNextPassword(currentPassword))

    private fun getNextPassword(pw: String): String {
        var nextPw = pw

        do {
            nextPw = incrementPassword(nextPw)
        } while (!checkPassword(nextPw))

        return nextPw
    }

    private fun checkPassword(pw: String): Boolean {
        var hasIncreasingStraight = false
        var hasFirstPair = false
        var hasSecondPair = false
        var firstPairIndex = -2

        pw.forEachIndexed { index, c ->
            if (index < pw.lastIndex - 1) {
                if (c.code + 1 == pw[index + 1].code && c.code + 2 == pw[index + 2].code)
                    hasIncreasingStraight = true
            }
            if (c == 'i' || c == 'o' || c == 'l')
                return false

            if (index < pw.lastIndex && index > firstPairIndex + 1) {
                if (c.code == pw[index + 1].code) {
                    if (!hasFirstPair) {
                        hasFirstPair = true
                        firstPairIndex = index
                    } else
                        hasSecondPair = true
                }
            }

        }

        return hasIncreasingStraight && hasFirstPair && hasSecondPair
    }

    private fun incrementPassword(pw: String): String {
        val currentC = pw[pw.lastIndex].code
        val newC = currentC + 1

        return if (newC == 123) {
            incrementPassword(pw.dropLast(1)) + "a"
        } else
            pw.dropLast(1) + newC.toChar()
    }
}