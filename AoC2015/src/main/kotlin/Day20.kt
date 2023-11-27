import java.io.File
import kotlin.math.ceil
import kotlin.math.sqrt

/*
 * Copyright (c) 2023. by Dominic Gernert
 */

fun main() {
    val day20 = Day20(File("src/main/resources/day20.txt").bufferedReader().readLine())

    println("Part 1 - house number ${day20.solvePart1()}")
    println("Part 2 - house number ${day20.solvePart2()}")
}

class Day20(input: String) {
    private val lowestAmountOfPresents = input.toInt()

    fun solvePart1(): Int {
        val nbrOfPresentsNeeded = lowestAmountOfPresents / 10

        // starting at two, because we know house number 1 is not the answer
        // and the algorithm doesn't work for 1
        var houseNumber = 2

        while (true) {
            var nbrOfPresents = 1 + houseNumber
            var i = 2

            while (i <= sqrt(houseNumber.toDouble())) {
                if (houseNumber % i == 0) {
                    if (i == (houseNumber / i))
                        nbrOfPresents += i
                    else
                        nbrOfPresents += (i + houseNumber / i)
                }

                i++
            }

            if (nbrOfPresents >= nbrOfPresentsNeeded)
                return houseNumber

            houseNumber++
        }
    }

    fun solvePart2(): Int {
        val nbrOfPresentsNeeded = lowestAmountOfPresents / 11.0

        // starting at two, because we know house number 1 is not the answer
        // and the algorithm doesn't work for 1
        var houseNumber = 2

        while (true) {
            var nbrOfPresents = houseNumber
            if (houseNumber <= 50){
                nbrOfPresents += 1
            }
            var i = maxOf(2, ceil((houseNumber / 50.0)).toInt())

            while (i <= houseNumber / 2) {
                if (houseNumber % i == 0) {
                    nbrOfPresents += i
                }

                i++
            }

//            while (i <= sqrt(houseNumber.toDouble())) {
//                if (houseNumber % i == 0) {
//                    if (i == (houseNumber / i))
//                        nbrOfPresents += i
//                    else
//                        nbrOfPresents += (i + houseNumber / i)
//                }
//
//                i++
//            }

            if (nbrOfPresents >= nbrOfPresentsNeeded)
                return houseNumber

            houseNumber++
        }
    }
}