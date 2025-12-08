package day09

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

    // Or read a large test input from the `src/day09/Day09_test.txt` file:
    val testInput = readInput("day09/Day09_test")
    checkEquals(part1(testInput), 0L)
    checkEquals(part2(testInput), 0L)

    // Read the input from the `src/day09/Day09.txt` file.
    val input = readInput("day09/Day09")
    part1(input).println("Part 1: ")
    part2(input).println("Part 2: ")
}
