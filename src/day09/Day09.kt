package day09

import checkEquals
import math.Vector2L
import println
import readInput
import uniquePairs

fun main() {
    fun part1(input: List<String>): Long {
        val vertices = input.map(Vector2L::fromString)

        return vertices.uniquePairs().maxOf { (first, second) ->
            val bottomLeft = Vector2L(minOf(first.x, second.x), minOf(first.y, second.y))
            val topRight = Vector2L(maxOf(first.x, second.x), maxOf(first.y, second.y))

            val width = topRight.x - bottomLeft.x + 1
            val height = topRight.y - bottomLeft.y + 1

            width * height
        }
    }

    fun part2(input: List<String>): Long {
        return 0L
    }

    // Test if implementation meets criteria from the description, like:
    // Normale rectangular case:
    checkEquals(part1(listOf(
        "2,5",
        "9,7"
    )), 24L)
    // Single-Line case:
    checkEquals(part1(listOf(
        "7,3",
        "2,3"
    )), 6L)
    checkEquals(part2(listOf()), 0L)

    // Or read a large test input from the `src/day09/Day09_test.txt` file:
    val testInput = readInput("day09/Day09_test")
    checkEquals(part1(testInput), 50L)
    checkEquals(part2(testInput), 0L)

    // Read the input from the `src/day09/Day09.txt` file.
    val input = readInput("day09/Day09")
    part1(input).println("Part 1: ")
    part2(input).println("Part 2: ")
}
