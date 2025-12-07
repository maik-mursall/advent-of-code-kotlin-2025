package day06

import checkEquals
import println
import readInput

fun main() {
    fun extractLinesHorizontally(input: List<String>): Pair<List<List<Long>>, List<Char>> {
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

    fun applyOperation(numbers: List<Long>, operator: Char) = when (operator) {
        '+' -> numbers.sum()
        '*' -> numbers.fold(1L) { acc, num -> acc * num }
        else -> throw IllegalStateException("Unsupported operator: $operator")
    }

    fun part1(input: List<String>) = extractLinesHorizontally(input).let { (numberLines, operatorLine) ->
        operatorLine.foldIndexed(0L) { index, sumOfOperations, operator ->
            val numbersInColumn = numberLines.map { it[index] }
            sumOfOperations + applyOperation(numbersInColumn, operator)
        }
    }

    fun extractNumbersVertically(input: List<String>) = input.first().indices.fold(listOf<Long>()) { acc, index ->
        val numberInColum = input.map { it[index] }.joinToString("").trim().toLong()
        acc.plusElement(numberInColum)
    }

    fun findNextOperatorIndex(line: String, startIndex: Int): Int {
        for (i in startIndex until line.length) {
            if (line[i] != ' ') {
                return i
            }
        }
        return line.length
    }


    fun solveAlienMath(input: List<String>): Long {
        val maxLineSize = input.maxOf { it.length }
        val normalizedInput = input.map {
            it.padEnd(maxLineSize + 1, ' ')
        }
        val numberLines = normalizedInput.take(normalizedInput.size - 1)
        val operatorLine = normalizedInput.last()

        var currentIndex = 0
        var sum = 0L
        while (currentIndex < operatorLine.length) {
            val lastIndexBeforeNextOperator = findNextOperatorIndex(operatorLine, currentIndex + 1) - 1
            val slicedNumbers = numberLines.map { it.slice(currentIndex until lastIndexBeforeNextOperator) }
            val extractedNumbers = extractNumbersVertically(slicedNumbers)

            sum += applyOperation(extractedNumbers, operatorLine[currentIndex])
            currentIndex = lastIndexBeforeNextOperator + 1
        }

        return sum
    }

    fun part2(input: List<String>): Long {
        return solveAlienMath(input)
    }

    // Read a large test input from the `src/Day06_test.txt` file:
    val testInput = readInput("day06/Day06_test")
    checkEquals(part1(testInput), 4277556L)
    checkEquals(part2(testInput), 3263827L)

    // Read the input from the `src/Day06.txt` file.
    val input = readInput("day06/Day06")
    part1(input).println("Part 1: ")
    part2(input).println("Part 2: ")
}
