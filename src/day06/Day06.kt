package day06

import checkEquals
import println
import readInput

fun main() {
    fun extractLines(input: List<String>): Pair<List<List<Long>>, List<Char>> {
        val numberLines = input.take(input.size - 1)
            .map { numberLine ->
                numberLine
                    .split(" ")
                    .filter { it.trim().isNotEmpty() }
                    .map { it.toLong() }
            }
        val operatorLine = input.last()
            .split(" ")
            .filter { it.trim().isNotEmpty() }
            .map { it.first() }

        return numberLines to operatorLine
    }

    fun part1(input: List<String>) = extractLines(input).let { (numberLines, operatorLine) ->
        operatorLine.foldIndexed(0L) { index, sumOfOperations, operator ->
            val numbersInColumn = numberLines.map { it[index] }
            val operationResult = when (operator) {
                '+' -> numbersInColumn.sum()
                '*' -> numbersInColumn.fold(1L) { acc, num -> acc * num }
                else -> throw IllegalStateException("Unsupported operator: $operator")
            }

            sumOfOperations + operationResult
        }
    }

    fun part2(input: List<String>): Long {
        return 0L
    }

    // Test if implementation meets criteria from the description, like:
    checkEquals(
        part1(
            listOf(
                "123 328",
                "45  64",
                "6   98",
                "*   +",
            )
        ), 33700L
    )
    checkEquals(part2(listOf()), 0L)

    // Or read a large test input from the `src/Day06_test.txt` file:
    val testInput = readInput("day06/Day06_test")
    checkEquals(part1(testInput), 4277556L)
    checkEquals(part2(testInput), 0L)

    // Read the input from the `src/Day06.txt` file.
    val input = readInput("day06/Day06")
    part1(input).println("Part 1: ")
    part2(input).println("Part 2: ")
}
