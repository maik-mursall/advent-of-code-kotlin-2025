package day05

import println
import readInput

fun main() {
    fun extractRanges(input: List<String>): Pair<List<LongRange>, List<Long>> {
        val (validIndices, indicesToCheckStrings) = input
            .filter { it.isNotEmpty() }
            .partition { it.contains('-') }

        val validIndicesRange = validIndices.map {
            val (start, end) = it.split('-')
            start.toLong()..end.toLong()
        }

        val indicesToCheck = indicesToCheckStrings.map(String::toLong)

        return validIndicesRange to indicesToCheck
    }

    fun part1(input: List<String>): Int {
        val (validIndicesRanges, indicesToCheck) = extractRanges(input)

        return indicesToCheck.count { indexToCheck ->
            validIndicesRanges.any {
                it.contains(indexToCheck)
            }
        }
    }

    fun part2(input: List<String>): Long {
        return 0L
    }

    // Test if implementation meets criteria from the description, like:
    //check(part1(listOf()) == 0L)
    //check(part2(listOf()) == 0L)

    // Or read a large test input from the `src/Day05_test.txt` file:
    val testInput = readInput("day05/Day05_test")
    check(part1(testInput) == 3)
    //check(part2(testInput) == 0L)

    // Read the input from the `src/Day05.txt` file.
    val input = readInput("day05/Day05")
    part1(input).println()
    //part2(input).println()
}
