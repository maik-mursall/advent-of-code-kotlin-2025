package day09

import checkEquals
import math.Vector2L
import println
import readInput
import uniquePairs

data class Rectangle(
    val topLeft: Vector2L,
    val bottomRight: Vector2L
) {
    constructor(bounds: Pair<Vector2L, Vector2L>): this(
        Vector2L(
            minOf(bounds.first.x, bounds.second.x),
            minOf(bounds.first.y, bounds.second.y)
        ),
        Vector2L(
            maxOf(bounds.first.x, bounds.second.x),
            maxOf(bounds.first.y, bounds.second.y)
        )
    )

    fun area(): Long {
        return (bottomRight.x - topLeft.x + 1) * (bottomRight.y - topLeft.y + 1)
    }
}

fun main() {
    fun getMaxArea(input: List<Pair<Vector2L, Vector2L>>) = input.maxOf { (first, second) ->
        val bottomLeft = Vector2L(minOf(first.x, second.x), minOf(first.y, second.y))
        val topRight = Vector2L(maxOf(first.x, second.x), maxOf(first.y, second.y))

        val width = topRight.x - bottomLeft.x + 1
        val height = topRight.y - bottomLeft.y + 1

        width * height
    }

    fun part1(input: List<String>): Long {
        val vertices = input.map(Vector2L::fromString)

        return getMaxArea(vertices.uniquePairs())
    }

    fun part2(input: List<String>): Long {
        val vertices = input.map(Vector2L::fromString)

        val allRectangles = vertices
            .uniquePairs()
            .map(::Rectangle)
            .sortedByDescending(Rectangle::area)

        val allEdges = vertices
            .zipWithNext()
            .plusElement(vertices.last() to vertices.first()) // End shape
            .map(::Rectangle)
            .sortedByDescending(Rectangle::area)

        val largestRectangle = allRectangles.first { rectangle ->
            allEdges.none { edge ->
                edge.topLeft.x < rectangle.bottomRight.x && edge.topLeft.y < rectangle.bottomRight.y
                        && edge.bottomRight.x > rectangle.topLeft.x && edge.bottomRight.y > rectangle.topLeft.y
            }
        }

        return largestRectangle.area()
    }


    // Test if implementation meets criteria from the description, like:
    // Normal rectangular case:
    checkEquals(
        part1(
            listOf(
                "2,5",
                "9,7"
            )
        ), 24L
    )
    // Single-Line case:
    checkEquals(
        part1(
            listOf(
                "7,3",
                "2,3"
            )
        ), 6L
    )

    // Or read a large test input from the `src/day09/Day09_test.txt` file:
    val testInput = readInput("day09/Day09_test")
    checkEquals(part1(testInput), 50L)
    checkEquals(part2(testInput), 24L)

    // Read the input from the `src/day09/Day09.txt` file.
    val input = readInput("day09/Day09")
    part1(input).println("Part 1: ")
    part2(input).println("Part 2: ")
}
