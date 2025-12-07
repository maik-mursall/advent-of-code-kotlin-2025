package day07

import checkEquals
import println
import readInput

fun main() {
    fun part1(input: List<String>): Long {
        var splitterHitCount = 0L
        val simulationSpace = input.map { it.toCharArray() }
        (0 until (simulationSpace.size - 1)).forEach { y ->
            val simulationLineBelowIndex = y + 1
            simulationSpace[y].forEachIndexed { x, cell ->
                when (cell) {
                    'S' -> {
                        simulationSpace[simulationLineBelowIndex][x] = '|'
                    }

                    '|' -> {
                        val cellBelow = simulationSpace[simulationLineBelowIndex][x]
                        if (cellBelow == '^') {
                            simulationSpace[simulationLineBelowIndex][x - 1] = '|'
                            simulationSpace[simulationLineBelowIndex][x + 1] = '|'
                            splitterHitCount++
                        } else {
                            simulationSpace[simulationLineBelowIndex][x] = '|'
                        }
                    }
                }
            }
        }

        return splitterHitCount
    }

    fun part2(state: List<String>): Long {
        val memo = hashMapOf<Pair<Int, Int>, Long>()

        fun part2Step(x: Int, y: Int): Long {
            if (y == state.size - 1) {
                return 1L
            }

            if (state[y][x] == '^') {
                if (memo.containsKey(Pair(x, y))) {
                    return memo[Pair(x, y)]!!
                }

                val firstStepResult = part2Step(x - 1, y)
                val secondStepResult = part2Step(x + 1, y)
                val result = firstStepResult + secondStepResult
                memo[Pair(x, y)] = result
                return result
            }

            return part2Step(x, y + 1)
        }

        val startX = state.first().indexOf('S')
        return part2Step(startX, 1)
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
    checkEquals(part2(smallTestInput), 4L)

    // Or read a large test input from the `src/day07/Day07_test.txt` file:
    val testInput = readInput("day07/Day07_test")
    checkEquals(part1(testInput), 21L)
    checkEquals(part2(testInput), 40L)

    // Read the input from the `src/day07/Day07.txt` file.
    val input = readInput("day07/Day07")
    part1(input).println("Part 1: ")
    part2(input).println("Part 2: ")
}
