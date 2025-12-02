package day02

import checkEquals
import println
import readInputCsv

fun main() {
    fun sumOfInvalidIndices(input: List<String>, handleIndex: (Long) -> Boolean): Long {
        return input.fold(0L) { sumOfInvalidIds, it ->
            val (startId, endId) = it.split("-").map(String::toLong)

            sumOfInvalidIds + (startId..endId).fold(0L) { sumOfInvalidIdsInRange, id ->
                val isInvalid = handleIndex(id)

                sumOfInvalidIdsInRange + if (isInvalid) id else 0L
            }
        }
    }

    fun part1(input: List<String>) = sumOfInvalidIndices(input) { id ->
        val idString = id.toString()

        if (idString.length % 2 != 0) {
            return@sumOfInvalidIndices false
        }

        val idStringHalfLength = idString.length / 2
        return@sumOfInvalidIndices idString.take(idStringHalfLength) == idString.takeLast(idStringHalfLength)
    }

    fun part2(input: List<String>) = sumOfInvalidIndices(input) { id ->
        val idString = id.toString()

        (1..(idString.length / 2)).any { windowSize ->
            val subSet = idString.take(windowSize)

            idString.windowed(windowSize, windowSize, true).all { it == subSet }
        }
    }

    // Test if implementation meets criteria from the description, like:
    checkEquals(part1(listOf("11-22")), 33L)
    checkEquals(part1(listOf("565653-565659")), 0L)
    checkEquals(part2(listOf("11-22")), 33L)
    checkEquals(part2(listOf("565653-565659")), 565656L)
    checkEquals(part2(listOf("11-22", "12-21", "565653-565659")), 565689L)

    // Or read a large test input from the `src/Day02_test.txt` file:
    val testInput = readInputCsv("day02/Day02_test")
    checkEquals(part1(testInput), 1227775554L)
    checkEquals(part2(testInput), 4174379265L)

    // Read the input from the `src/Day02.txt` file.
    val input = readInputCsv("day02/Day02")
    part1(input).println("Part 1: ")
    part2(input).println("Part 2: ")
}
