import java.io.File

/*
 * Copyright (c) 2023. by Dominic Gernert
 */

fun main() {
    val day12 = Day12(File("src/main/resources/day12.txt").bufferedReader().readLine())

    println("Part 1 - sum of all numbers is ${day12.solvePart1()}")
    println("Part 2 - sum of all numbers excluding the red ones is ${day12.solvePart2()}")
}

class Day12(input: String) {
    private val jsonString = input

    fun solvePart1(): Int = sumNumbers()

    fun solvePart2(): Int {
        var index = 0
        val excludedRanges = ArrayList<IntRange>()

        while (true) {
            index = jsonString.indexOf("\":\"red\"", index)

            if (index == -1)
                break

            var beginOfObject = 0
            var endOfObject = 0
            var i = index - 1
            var objectDepth = 0

            // find beginn of object
            while (i >= 0) {
                when (jsonString[i]) {
                    '{' -> {
                        if (objectDepth == 0) {
                            beginOfObject = i
                            break
                        }

                        objectDepth--
                    }

                    '}' -> objectDepth++
                }
                i--
            }

            // find end of object
            i = index + 1
            objectDepth = 0
            while (i <= jsonString.lastIndex) {
                when (jsonString[i]) {
                    '{' -> objectDepth++
                    '}' -> {
                        if (objectDepth == 0) {
                            endOfObject = i
                            break
                        }

                        objectDepth--
                    }
                }

                i++
            }

            val excludedRange = IntRange(beginOfObject, endOfObject)
            excludedRanges.add(excludedRange)
            index += 6
        }

        return sumNumbers(excludedRanges)
    }

    private fun sumNumbers(excludedRanges: ArrayList<IntRange> = ArrayList()): Int {
        var sum = 0
        var index = 0

        parseLoop@
        while (index <= jsonString.lastIndex) {
            for (range in excludedRanges) {
                if (range.contains(index)) {
                    index = range.last + 1
                    excludedRanges.remove(range)
                    continue@parseLoop
                }
            }

            val currentC = jsonString[index]

            if (currentC.isDigit()) {
                var numberString = if (index != 0 && jsonString[index - 1] == '-') "-$currentC" else "$currentC"

                for (j in (index + 1)..jsonString.lastIndex) {
                    if (jsonString[j].isDigit()) {
                        numberString += jsonString[j]
                        index++
                    } else
                        break
                }

                sum += numberString.toInt()
            }
            index++
        }
        return sum
    }
}