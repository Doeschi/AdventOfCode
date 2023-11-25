import java.io.File
import java.security.MessageDigest
import kotlin.text.Charsets.UTF_8

/*
 * Copyright (c) 2023. by Dominic Gernert
 */

fun main() {
    val day04 = Day04(File("src/main/resources/day04.txt").bufferedReader().readLine())

    println("Part 1 - ${day04.solvePart1()} creates a hex hash with 5 leading zeros")
    println("Part 2 - ${day04.solvePart2()} creates a hex hash with 6 leading zeros")

}

class Day04(input: String) {
    private val secretKey = input

    fun solvePart1(): Int = findHash("00000")

    fun solvePart2(): Int = findHash("000000")

    private fun findHash(leadingZeros: String): Int {
        var suffix = 1

        while (true) {
            if (md5("$secretKey$suffix").toHex().startsWith(leadingZeros))
                return suffix

            suffix++
        }
    }

    private fun md5(str: String): ByteArray = MessageDigest.getInstance("MD5").digest(str.toByteArray(UTF_8))
    private fun ByteArray.toHex() = joinToString(separator = "") { byte -> "%02x".format(byte) }

}