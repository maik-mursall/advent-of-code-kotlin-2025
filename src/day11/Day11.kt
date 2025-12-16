package day11

import checkEquals
import println
import readInput

typealias Node = String

fun main() {
    fun parseInput(input: List<String>): Map<Node, List<Node>> = input.associate {
        val parts = it.split(' ')
        val node = parts[0].dropLast(1)

        node to parts.drop(1)
    }

    fun findPathCount(
        nodesWithConnections: Map<Node, List<Node>>,
        from: Node,
        to: Node
    ): Long {
        val memory = mutableMapOf<Node, Long>()

        fun step(
            connections: List<Node>
        ): Long = connections.fold(0L) { acc, node ->
            if (node == to) {
                acc + 1L
            } else {
                if (memory.containsKey(node)) {
                    acc + memory[node]!!
                } else {
                    val nextNodes = nodesWithConnections[node]
                    if (nextNodes != null) {
                        val stepResult = step(nextNodes)

                        memory[node] = stepResult

                        acc + stepResult
                    } else {
                        acc
                    }
                }
            }
        }

        return step(nodesWithConnections[from]!!)
    }

    fun part1(input: List<String>): Long {
        val nodesWithConnections = parseInput(input)

        return findPathCount(nodesWithConnections, "you", "out")
    }

    fun part2(input: List<String>): Long {
        val nodesWithConnections = parseInput(input)

        val pathCountSvrDac = findPathCount(nodesWithConnections, "svr", "dac")
        val pathCountSvrFft = findPathCount(nodesWithConnections, "svr", "fft")

        if (pathCountSvrDac < pathCountSvrFft) {
            val pathCountDacFft = findPathCount(nodesWithConnections, "dac", "fft")
            val pathCountFftOut = findPathCount(nodesWithConnections, "fft", "out")
            return pathCountSvrDac * pathCountDacFft * pathCountFftOut
        } else {
            val pathCountFftDac = findPathCount(nodesWithConnections, "fft", "dac")
            val pathCountDacOut = findPathCount(nodesWithConnections, "dac", "out")
            return pathCountSvrFft * pathCountFftDac * pathCountDacOut
        }
    }

    // Test if implementation meets criteria from the description, like:
    //checkEquals(part1(listOf()), 0L)
    //checkEquals(part2(listOf()), 0L)

    // Or read a large test input from the `src/day11/Day11_test.txt` file:
    checkEquals(part1(readInput("day11/Day11_test")), 5L)
    checkEquals(part2(readInput("day11/Day11_test2")), 2L)

    // Read the input from the `src/day11/Day11.txt` file.
    val input = readInput("day11/Day11")
    part1(input).println("Part 1: ")
    part2(input).println("Part 2: ")
}
