package day11

import checkEquals
import println
import readInput

typealias Node = String

fun main() {
    fun parseInput(input: List<String>): Map<Node, List<Node>> = input.map {
        val parts = it.split(' ')
        val node = parts[0].dropLast(1)

        node to parts.drop(1)
    }.toMap()

    fun part1(input: List<String>): Long {
        val nodesWithConnections = parseInput(input)

        fun step(
            connections: List<Node>
        ): Long = connections.fold(0L) { acc, node ->
            if (node == "out") {
                acc + 1L
            } else {
                acc + step(nodesWithConnections[node]!!)
            }
        }

        return step(nodesWithConnections["you"]!!)
    }

    fun part2(input: List<String>): Long {
        return 0L
    }

    // Test if implementation meets criteria from the description, like:
    //checkEquals(part1(listOf()), 0L)
    checkEquals(part2(listOf()), 0L)

    // Or read a large test input from the `src/day11/Day11_test.txt` file:
    val testInput = readInput("day11/Day11_test")
    checkEquals(part1(testInput), 5L)
    checkEquals(part2(testInput), 0L)

    // Read the input from the `src/day11/Day11.txt` file.
    val input = readInput("day11/Day11")
    part1(input).println("Part 1: ")
    part2(input).println("Part 2: ")
}
