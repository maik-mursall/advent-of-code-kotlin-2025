package day07

import checkEquals
import println
import readInput

fun main() {
    fun part1(input: List<String>): Long {
        var splitterHitCount = 0L
        val currentSimulationSpace = input.map { it.toCharArray() }
        currentSimulationSpace.forEachIndexed { y, simulationLine ->
            if (y >= currentSimulationSpace.size - 1) {
                return@forEachIndexed
            }

            simulationLine.forEachIndexed { x, cell ->
                when (cell) {
                    'S' -> {
                        currentSimulationSpace[y + 1][x] = '|'
                    }

                    '|' -> {
                        val cellBelow = currentSimulationSpace[y + 1][x]
                        if (cellBelow == '^') {
                            currentSimulationSpace[y + 1][x - 1] = '|'
                            currentSimulationSpace[y + 1][x + 1] = '|'
                            splitterHitCount++
                        } else {
                            currentSimulationSpace[y + 1][x] = '|'
                        }
                    }
                }
            }
        }

        return splitterHitCount
    }

    fun part2(input: List<String>): Long {
        return 0L
    }

    val smallTestInput = """
        ..S..
        .....
        ..^..
        .....
        .^.^.
        .....
    """.trimIndent().lines()
    // Test if implementation meets criteria from the description, like:
    checkEquals(part1(smallTestInput), 3L)
    checkEquals(part2(smallTestInput), 0L)

    // Or read a large test input from the `src/day07/Day07_test.txt` file:
    val testInput = readInput("day07/Day07_test")
    checkEquals(part1(testInput), 21L)
    checkEquals(part2(testInput), 0L)

    // Read the input from the `src/day07/Day07.txt` file.
    val input = readInput("day07/Day07")
    part1(input).println("Part 1: ")
    part2(input).println("Part 2: ")
}
