package day18

import java.io.File

internal data class SnailfishNumber(
    var left: SnailfishNumber? = null,
    var right: SnailfishNumber? = null,
    var parent: SnailfishNumber? = null,
    var value: Int = Int.MIN_VALUE,
    var hasValue: Boolean = false
) {

    fun addToLeftSide(value: Int) {
        if (left!!.hasValue)
            left!!.value += value
        else
            left!!.addToLeftSide(value)
    }

    fun addRightValueAfterExplosion(value: Int) {
        if (right!!.hasValue) {
            right!!.value += value
        } else {
            right!!.addToLeftSide(value)
        }
    }

    fun addToRightSide(value: Int) {
        if (right!!.hasValue)
            right!!.value += value
        else
            right!!.addToRightSide(value)
    }

    fun addLeftValueAfterExplosion(value: Int) {
        if (left!!.hasValue) {
            left!!.value += value
        } else {
            left!!.addToRightSide(value)
        }
    }

    fun calculateMagnitude(): Int {
        if (hasValue)
            return value
        else {
            return 3 * left!!.calculateMagnitude() + 2 * right!!.calculateMagnitude()
        }
    }

    override fun toString(): String {
        return if (hasValue) value.toString() else "[${left.toString()},${right.toString()}]"
    }
}

private val NULL_PAIR = Pair(0, 0)

private var hadExplosion = false

fun main() {
    val allNumbers = File("src/main/kotlin/day18/input.txt").bufferedReader().readLines()

    val allSnailfishNumbers = allNumbers.map { s -> getNumber(s) }

    val finalNum = allSnailfishNumbers.reduce { first, second ->
        val newNum = SnailfishNumber(left = first, right = second)
        first.parent = newNum
        second.parent = newNum

        println("After addition: $newNum")
        reduceNum(newNum)
        newNum
    }

    println("Magnitude of $finalNum is ${finalNum.calculateMagnitude()}")

    var maxMagnitude = Int.MIN_VALUE

    allNumbers.forEach { s1 ->
        allNumbers.forEach { s2 ->
            if (s1 != s2) {
                val n1 = getNumber(s1)
                val n2 = getNumber(s2)

                val newNum = SnailfishNumber(left = n1, right = n2)
                n1.parent = newNum
                n2.parent = newNum

                reduceNum(newNum)
                val mag = newNum.calculateMagnitude()

                if (mag > maxMagnitude)
                    maxMagnitude = mag
            }
        }
    }

    println("Max magnitude of two numbers is $maxMagnitude")
}

private fun reduceNum(num: SnailfishNumber) {
    while (true) {
        hadExplosion = false
        explodeNum(num, 0)

        if (hadExplosion)
            println("After explosion: $num")
        else {
            val couldSplit = splitNum(num)
            if (couldSplit)
                println("After split: $num")
            else
                break
        }
    }
}

private fun splitNum(num: SnailfishNumber): Boolean {
    if (num.hasValue) {
        return if (num.value >= 10) {
            val left = SnailfishNumber(value = num.value / 2, hasValue = true, parent = num)
            val right = SnailfishNumber(value = (num.value + 1) / 2, hasValue = true, parent = num)

            num.value = Int.MIN_VALUE
            num.hasValue = false
            num.left = left
            num.right = right

            true
        } else {
            false
        }
    } else {
        val l = splitNum(num.left!!)

        return if (l)
            l
        else
            splitNum(num.right!!)
    }
}

private fun explodeNum(num: SnailfishNumber, depth: Int): Pair<Int, Int> {
    var leftValue = 0
    var rightValue = 0

    if (num.hasValue)
        return NULL_PAIR

    if (num.left!!.hasValue && depth >= 4) {
        if (!num.right!!.hasValue) {
            throw RuntimeException("only number with left and right part are regular can explode :O")
        }

        leftValue = num.left!!.value
        rightValue = num.right!!.value

        // explode the number
        num.left = null
        num.right = null
        num.value = 0
        num.hasValue = true

        hadExplosion = true

        return Pair(leftValue, rightValue)
    } else {
        val leftR = explodeNum(num.left!!, depth + 1)
        leftValue = leftR.first

        if (leftR.second != 0)
            num.addRightValueAfterExplosion(leftR.second)

        if (!hadExplosion) {
            val rightR = explodeNum(num.right!!, depth + 1)
            rightValue = rightR.second

            if (rightR.first != 0)
                num.addLeftValueAfterExplosion(rightR.first)
        }
    }

    return Pair(leftValue, rightValue)
}

private fun getNumber(input: String): SnailfishNumber {
    var left = SnailfishNumber()
    var right = SnailfishNumber()
    val num = SnailfishNumber(left = left, right = right)

    left.parent = num
    right.parent = num

    var isLeft = true

    // first and last braces, as they are always there and represented by the variables "left" and "right"
    input.substring(1..<input.lastIndex).forEachIndexed { index, c ->
        when (c) {
            '[' -> {
                val parent = if (isLeft) left else right

                val newLeft = SnailfishNumber()
                newLeft.parent = parent

                val newRight = SnailfishNumber()
                newRight.parent = parent

                parent.left = newLeft
                parent.right = newRight

                left = newLeft
                right = newRight

                isLeft = true
            }

            ']' -> {
                if (input[index + 2] == ',') {
                    right = left.parent!!.parent!!.right!!
                    left = left.parent!!
                } else {
                    left = right.parent!!.parent!!.left!!
                    right = right.parent!!
                }
            }

            ',' -> {
                isLeft = false
            }

            else -> {
                if (isLeft) {
                    left.value = c.digitToInt()
                    left.hasValue = true
                } else {
                    right.value = c.digitToInt()
                    right.hasValue = true
                }
            }
        }
    }

    return num
}