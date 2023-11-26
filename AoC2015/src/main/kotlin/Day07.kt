import java.io.File

/*
 * Copyright (c) 2023. by Dominic Gernert
 */

fun main() {
    val day07 = Day07(File("src/main/resources/day07.txt").bufferedReader().readLines())

    println("Part 1 - signal of wire a: ${day07.solvePart1()}")
    println("Part 2 - signal of wire a after piping its first result to b: ${day07.solvePart2()}")
}

class Day07(input: List<String>) {
    private val wires = HashMap<String, Wire>()
    private val connections = input

    fun solvePart1(): Int {
        wires.clear()
        prepareWires()
        return (wires["a"]?.getSignal()) ?: throw RuntimeException("Wire a not found")
    }

    fun solvePart2(): Int {
        wires.clear()
        prepareWires()
        val signalOfA = (wires["a"]?.getSignal()) ?: throw RuntimeException("Wire a not found")

        wires.clear()
        prepareWires()
        wires["b"]!!.source = NumberLiteral(signalOfA)

        return wires["a"]!!.getSignal()
    }

    private fun prepareWires() {
        connections.forEach { conn ->
            val split = conn.split(" -> ")
            val source = split.first()
            val identifier = split.last()

            val wire = wires.getOrPut(identifier) { Wire(identifier) }
            wire.source = getSource(source)
        }
    }

    private fun getSource(s: String): Source {
        // source is a value
        return if (s.all { c -> c.isDigit() }) {
            NumberLiteral(s.toInt())

            // source is another wire
        } else if (s.all { c -> c.isLetter() }) {
            wires.getOrPut(s) { Wire(s) }

            // source is a not gate
        } else if (s.contains("NOT")) {
            val source = getSource(s.removePrefix("NOT "))
            NotGate(source)

            // all other sources are constructed from two other sources
        } else {
            val sources = s.split(" ")
            val s1 = getSource(sources.first())
            val s2 = getSource(sources.last())
            val operation = sources[1]

            when (operation) {
                "AND" -> AndGate(s1, s2)
                "OR" -> OrGate(s1, s2)
                "LSHIFT" -> LeftShift(s1, s2)
                "RSHIFT" -> RightShift(s1, s2)
                else -> throw RuntimeException("WE SHOULD NOT REACH THIS")
            }
        }
    }
}

internal abstract class Source {
    private var cachedSignal: Int = -1

    fun getSignal(): Int {
        if (cachedSignal == -1)
            cachedSignal = getSignalInternal()

        return cachedSignal
    }

    protected abstract fun getSignalInternal(): Int
}

internal class Wire(private val identifier: String, var source: Source? = null) : Source() {
    override fun getSignalInternal(): Int =
        source?.getSignal() ?: throw RuntimeException("Wire $identifier has no signal source")
}

internal class NumberLiteral(private val value: Int) : Source() {
    override fun getSignalInternal(): Int = value
}

internal class NotGate(private var source: Source) : Source() {
    override fun getSignalInternal(): Int = source.getSignal().inv() and 0x0000FFFF
}

internal abstract class BinaryOperator(val source1: Source, val source2: Source) : Source()

internal class AndGate(s1: Source, s2: Source) : BinaryOperator(s1, s2) {
    override fun getSignalInternal(): Int = (source1.getSignal() and source2.getSignal()) and 0x0000FFFF
}

internal class OrGate(s1: Source, s2: Source) : BinaryOperator(s1, s2) {
    override fun getSignalInternal(): Int = (source1.getSignal() or source2.getSignal()) and 0x0000FFFF
}

internal class LeftShift(s1: Source, s2: Source) : BinaryOperator(s1, s2) {
    override fun getSignalInternal(): Int = (source1.getSignal() shl source2.getSignal()) and 0x0000FFFF
}

internal class RightShift(s1: Source, s2: Source) : BinaryOperator(s1, s2) {
    override fun getSignalInternal(): Int = (source1.getSignal() shr source2.getSignal()) and 0x0000FFFF
}