package day22

import java.io.File
import kotlin.math.max
import kotlin.math.min

fun main() {
    val r = ReactorReboot(File("src/main/kotlin/day22/Input.txt").bufferedReader().readLines())
    println(r.solve1())
}

class ReactorReboot(val input: List<String>) {
    fun solve1(): Int {
        val reactor = Reactor(-50..50, -50..50, -50..50)

        input.forEach { line ->
            val cuboid = Cuboid.of(line)
            reactor.setCubes(cuboid)
        }

        return reactor.countCubes()
    }

    fun solve2(): Int {
        input.map { line -> Cuboid.of(line) }.forEach {

        }

        return 0
    }
}

internal data class Cuboid(val x: LongRange, val y: LongRange, val z: LongRange, val state: Boolean) {

    companion object {
        fun of(input: String): Cuboid {
            val split = input.split(" ")
            val state = split[0] == "on"
            val coords = split[1].replace(",", "=").split("=")

            val xR = parseCoords(coords[1])
            val yR = parseCoords(coords[3])
            val zR = parseCoords(coords[5])

            return Cuboid(xR, yR, zR, state)
        }

        private fun parseCoords(input: String): LongRange {
            val split = input.split("..")
            return LongRange(split[0].toLong(), split[1].toLong())
        }
    }
}

internal data class Reactor(
    val xRange: IntRange,
    val yRange: IntRange,
    val zRange: IntRange,
) {
    val cubes: Array<Array<Array<Boolean>>>

    init {
        val xSize = (xRange.last - xRange.first) + 1
        val ySize = (yRange.last - yRange.first) + 1
        val zSize = (zRange.last - zRange.first) + 1
        cubes = Array(xSize.toInt()) { Array(ySize.toInt()) { Array(zSize.toInt()) { false } } }
    }

    fun setCubes(cuboid: Cuboid) {
        val affectedX = IntRange(max(cuboid.x.first.toInt(), xRange.first), min(cuboid.x.last.toInt(), xRange.last))
        val affectedY = IntRange(max(cuboid.y.first.toInt(), yRange.first), min(cuboid.y.last.toInt(), yRange.last))
        val affectedZ = IntRange(max(cuboid.z.first.toInt(), zRange.first), min(cuboid.z.last.toInt(), zRange.last))

        for (x in affectedX) {
            for (y in affectedY) {
                for (z in affectedZ) {
                    if (!setCube(x, y, z, cuboid.state))
                        println("$x, $y, $z was not in range")
                }
            }
        }
    }

    private fun setCube(x: Int, y: Int, z: Int, state: Boolean): Boolean {
        return if (xRange.contains(x) && yRange.contains(y) && zRange.contains(z)) {
            cubes[x - xRange.first][y - yRange.first][z - zRange.first] = state
            true
        } else
            false
    }

    fun countCubes(): Int {
        var counter = 0
        for (yzPlane in cubes) {
            for (zAxis in yzPlane) {
                for (cube in zAxis) {
                    if (cube)
                        counter++
                }
            }
        }

        return counter
    }
}