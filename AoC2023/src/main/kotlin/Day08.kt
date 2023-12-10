import java.io.File
import kotlin.math.max
import kotlin.math.min

/*
 * Copyright (c) 2023. by Dominic Gernert
 */

fun main() {
    val day08 = Day08(File("src/main/resources/day08.txt").bufferedReader().readLines())

//    println("Part 1 - ${day08.solvePart1()}")
    println("Part 2 - ${day08.solvePart2()}")
}

class Day08(input: List<String>) {
    private val instructions = getInstructions(input)
    private val nodes = mapNodes(input)

    fun solvePart1(): Int {
        var currentNode = "AAA"
        var nbrOfSteps = 0

        outerLoop@
        while (true) {
            if (instructions[nbrOfSteps % instructions.length] == 'L')
                currentNode = nodes[currentNode]!!.left
            else
                currentNode = nodes[currentNode]!!.right

            nbrOfSteps++

            if (currentNode == "ZZZ")
                break
        }

        return nbrOfSteps
    }

    fun solvePart2(): Long {
        val ghostNodes = getGhostNodes()

        val cycleLengths = ghostNodes.map { node ->
            var currentNode = node.name
            var nbrOfSteps = 0

            while (nbrOfSteps == 0 || !currentNode.endsWith("Z")) {
                if (instructions[nbrOfSteps % instructions.length] == 'L')
                    currentNode = nodes[currentNode]!!.left
                else
                    currentNode = nodes[currentNode]!!.right

                nbrOfSteps++
            }

            nbrOfSteps.toLong()
        }

        var result = cycleLengths.first()

        for (i in 1..cycleLengths.lastIndex) {
            result = leastCommonMultiple(result, cycleLengths[i])
        }

        return result
    }

    private fun leastCommonMultiple(a: Long, b: Long): Long {
        var tmpA = max(a, b)
        var tmpB = min(a, b)

        var remainder = -1L
        var previousRemainder = -1L

        while (remainder != 0L) {
            previousRemainder = remainder
            remainder = tmpA % tmpB

            tmpA = tmpB
            tmpB = remainder
        }

        return (a * b) / previousRemainder
    }

    private fun getGhostNodes(): List<Node> {
        val l = mutableListOf<Node>()

        nodes.forEach { name, node ->
            if (name.endsWith("A"))
                l.add(node)
        }

        return l
    }

    private fun getInstructions(input: List<String>) = input.first()

    private fun mapNodes(input: List<String>): HashMap<String, Node> {
        val nodes = HashMap<String, Node>()

        val pattern = Regex("""^(\w+) = \((\w+), (\w+)\)$""")
        input.drop(2).forEach { line ->
            val (nodeName, leftName, rightName) = pattern.matchEntire(line)?.destructured
                ?: throw RuntimeException("CANT PARSE $line")

            val node = Node(nodeName, leftName, rightName)
            nodes[nodeName] = node
        }

        return nodes
    }
}

internal data class Node(val name: String, val left: String, val right: String)