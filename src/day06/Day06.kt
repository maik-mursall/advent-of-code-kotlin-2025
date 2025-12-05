package day06

import checkEquals
import println
import readInput

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

    // Or read a large test input from the `src/Day06_test.txt` file:
    val testInput = readInput("day06/Day06_test")
    checkEquals(part1(testInput), 0L)
    checkEquals(part2(testInput), 0L)

    // Read the input from the `src/Day06.txt` file.
    val input = readInput("day06/Day06")
    part1(input).println("Part 1: ")
    part2(input).println("Part 2: ")
}
