package day05

import checkEquals
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

    fun LongRange.fastSize(): Long {
        return (this.last + 1) - this.first
    }

    fun mergeRangesStep(ranges: List<LongRange>): List<LongRange> = ranges.fold(mutableListOf()) { acc, range ->
        val firstHit = acc.indexOfFirst {
            val rangeStartInside = range.first in it
            val rangeEndInside = range.last in it

            if (rangeStartInside && rangeEndInside) { return@fold acc } // range is fully inside existing range

            if (!rangeStartInside && !rangeEndInside && (range.first > (it.last + 1) || range.last < (it.first - 1))) {
                return@indexOfFirst false
            }

            return@indexOfFirst true
        }

        if (firstHit == -1) {
            acc.add(range)
        } else {
            val hitRange = acc[firstHit]
            val newRange = minOf(hitRange.first, range.first)..maxOf(hitRange.last, range.last)
            acc[firstHit] = newRange
        }

        acc
    }

    fun mergeRanges(ranges: List<LongRange>): List<LongRange> {
        var currentRanges = ranges
        while (true) {
            val merged = mergeRangesStep(currentRanges)
            if (merged.hashCode() == currentRanges.hashCode()) {
                return merged
            }
            currentRanges = merged
        }
    }

    fun part2(input: List<String>): Long {
        val (validIndicesRanges) = extractRanges(input)

        return mergeRanges(validIndicesRanges).fold(0L) { count, range ->
            count + range.fastSize()
        }
    }

    // Test if implementation meets criteria from the description, like:
    //check(part1(listOf()) == 0L)
    checkEquals(part2(listOf(
        "3-5",
        "10-14",
        "16-20",
        "12-18",
        "19-20"
    )), 14L)

    // Or read a large test input from the `src/Day05_test.txt` file:
    val testInput = readInput("day05/Day05_test")
    checkEquals(part1(testInput), 3)
    checkEquals(part2(testInput), 14L)

    // Read the input from the `src/Day05.txt` file.
    val input = readInput("day05/Day05")
    part1(input).println("Part 1: ")
    part2(input).println("Part 2: ")
}
