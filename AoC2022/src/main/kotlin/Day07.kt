import java.io.File

/*
 * Copyright (c) 2023. by Dominic Gernert
 */

fun main() {
    val day07 = Day07(File("src/main/resources/day07.txt").bufferedReader().readLines())

    println("Part 1 - ${day07.solvePart1()}")
    println("Part 2 - ${day07.solvePart2()}")
}

class Day07(input: List<String>) {
    private val directories = getDirectories(input)

    fun solvePart1(): Int {
        var sum = 0

        val sort = directories.sortedByDescending { it.depth }

        sort.forEach { dir ->
            val size = getSize(dir)

            if (dir.name != "/") {
                if (size <= 100_000)
                    sum += size
            }
        }

        return sum
    }

    private fun getSize(dir: Directory): Int {
        var size = 0

        dir.content.forEach { content ->
            if (content is DeviceFile)
                size += content.size
            else if (content is Directory) {
                if (content.size == -1) {
                    size += getSize(content)

                } else
                    size += content.size
            }
        }

        dir.size = size
        return size
    }

    fun solvePart2(): Int {
        return 0
    }

    private fun getDirectories(input: List<String>): List<Directory> {
        val directories = mutableMapOf<String, Directory>()
        var currentDir = Directory.NULL_DIR
        var currentDepth = -1

        input.forEach { line ->
            if (line.startsWith("$")) {
                val s = line.drop(2).split(" ")
                val command = s.first()

                if (command == "cd") {
                    val dirName = s.last()

                    if (dirName == ".." && currentDir.parent != null) {
                        currentDir = currentDir.parent!!
                        currentDepth--
                    } else {
                        currentDepth++

                        val newDir = directories.getOrPut(dirName) { Directory(dirName, currentDepth) }
                        newDir.parent = currentDir

                        currentDir.content.add(newDir)
                        currentDir = newDir
                    }
                }
            } else if (line.startsWith("dir")) {
                val dirName = line.removePrefix("dir ")
                val d = directories.getOrPut(dirName) { Directory(dirName, currentDepth) }
                currentDir.content.add(d)
            } else {
                val f = line.split(" ")
                val size = f.first().toInt()
                val name = f.last()

                val file = DeviceFile(name, size)
                currentDir.content.add(file)
            }
        }

        return directories.toList().map { it.second }
    }
}

internal interface Content

internal data class Directory(
    val name: String,
    var depth: Int,
    var parent: Directory? = null,
    val content: MutableSet<Content> = mutableSetOf(),
    var size: Int = -1,
) : Content {
    companion object {
        val NULL_DIR = Directory("NULL DIR", -1)
    }

    override fun hashCode(): Int = name.hashCode()

    override fun equals(other: Any?): Boolean {
        if (this === other) return true

        other as Directory

        return this.name == other.name
    }

    override fun toString(): String {
        return "$name $size"
    }
}

internal data class DeviceFile(val name: String, val size: Int) : Content