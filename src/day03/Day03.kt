package day03

import checkEquals
import println
import readInputCsv

fun main() {
    fun part1(input: List<String>): Long {
        return 0L
    }

    fun part2(input: List<String>): Long {
        return 0L
    }

    // Test if implementation meets criteria from the description, like:
    checkEquals(part1(listOf()), 0L)
    checkEquals(part2(listOf()), 0L)

    // Or read a large test input from the `src/Day03_test.txt` file:
    val testInput = readInputCsv("day03/Day03_test")
    checkEquals(part1(testInput), 0L)
    checkEquals(part2(testInput), 0L)

    // Read the input from the `src/Day03.txt` file.
    val input = readInputCsv("day03/Day03")
    part1(input).println("Part 1: ")
    part2(input).println("Part 2: ")
}
