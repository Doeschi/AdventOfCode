import java.io.File

/*
 * Copyright (c) 2023. by Dominic Gernert
 */

fun main() {
    val day05 = Day05(File("src/main/resources/day05.txt").bufferedReader().readLines())

    println("Part 1 - ${day05.solvePart1()} nice strings")
    println("Part 2 - ${day05.solvePart2()} nice strings")
}

class Day05(input: List<String>) {
    private val inputStrings = input

    fun solvePart1(): Int = inputStrings.count { s ->
        var vowelsCounts = 0
        var hasDoubleLetter = false
        var hasDisallowedSubstring = false
        var previousC = '-'

        s.forEach { c ->
            if (c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u')
                vowelsCounts++

            if (c == previousC)
                hasDoubleLetter = true

            if ("ab cd pq xy".contains("$previousC$c")) {
                hasDisallowedSubstring = true
            }

            previousC = c
        }

        vowelsCounts >= 3 && hasDoubleLetter && !hasDisallowedSubstring
    }

    fun solvePart2(): Int = inputStrings.count { s ->
        var hasPair = false
        var hasRepeatingLetter = false

        for (i in 0..<s.lastIndex - 2) {
            val subStr = s.subSequence(i, i + 2)

            for (j in IntRange(i + 2, s.lastIndex - 1)) {
                val other = s.subSequence(j, j + 2)

                if (other == subStr)
                    hasPair = true

            }

            if (s[i] == s[i + 2])
                hasRepeatingLetter = true
        }

        if (!hasRepeatingLetter && s[s.lastIndex - 2] == s[s.lastIndex])
            hasRepeatingLetter = true

        hasPair && hasRepeatingLetter
    }
}