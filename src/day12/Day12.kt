package day12

import checkEquals
import println
import readInput
import kotlin.collections.mutableListOf

typealias Vector2I = Pair<Int, Int>
typealias Grid = List<CharArray>

fun Grid.prettyPrint() = joinToString("\n") {
    it.joinToString("")
}.println()

data class PresentTemplate(
    val size: Int,
    val data: CharArray,
) {
    fun rotateRight(): PresentTemplate {
        val newCharArray = CharArray(data.size) { '.' }

        data.forEachIndexed { index, it ->
            val newColumn = index % size
            val newRow = (size - 1) - index / size
            newCharArray[(newColumn * size) + newRow] = it
        }

        return copy(
            data = newCharArray,
        )
    }

    fun applyTo(grid: Grid, position: Vector2I): Boolean {
        if ((size + position.second - 1) !in grid.indices || (size + position.first - 1) !in grid.first().indices) {
            return false
        }

        // Check pass
        data.forEachIndexed { index, it ->
            val column = (index % size) + position.first
            val row = (index / size) + position.second

            if (grid[row][column] == '#' && it == '#') {
                return false
            }
        }

        // assign pass
        data.forEachIndexed { index, it ->
            if (it == '.') return@forEachIndexed

            val column = (index % size) + position.first
            val row = (index / size) + position.second

            grid[row][column] = it
        }

        return true
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as PresentTemplate

        if (size != other.size) return false
        if (!data.contentEquals(other.data)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = size
        result = 31 * result + data.contentHashCode()
        return result
    }

    companion object {
        fun parse(input: List<String>) = PresentTemplate(
            size = input.first().length,
            data = input.takeLast(input.first().length).joinToString("").toCharArray()
        )
    }
}

fun main() {
    fun parseInput(input: List<String>): Triple<List<PresentTemplate>, List<Grid>, List<List<Int>>> {
        val presentTemplates = mutableListOf<PresentTemplate>()
        val grids = mutableListOf<List<CharArray>>()
        val targets = mutableListOf<List<Int>>()

        var currentIndex = 0
        while (currentIndex < input.size) {
            val line = input[currentIndex]

            if ('x' !in line) {
                val size = input[currentIndex + 1].length
                val presentBlock = input.slice((currentIndex + 1) .. (currentIndex + size))
                presentTemplates.add(PresentTemplate.parse(presentBlock))
                currentIndex += size + 2
            } else {
                val sectionDividerIndex = line.indexOf(':')
                val gridSection = line.substring(0 until sectionDividerIndex)

                val (width, height) = gridSection.split("x").map(String::toInt)
                grids.add(List(height) { CharArray(width) { '.' } })

                val targetSection = line.substring((sectionDividerIndex + 2) until line.length)
                targets.add(targetSection.split(' ').map(String::toInt))
                currentIndex++
            }
        }

        return Triple(presentTemplates, grids, targets)
    }

    fun part1(input: List<String>): Long {
        val (presentTemplates, grids, targets) = parseInput(input)

        fun solveForGrid(grid: Grid, target: List<Int>): Boolean {
            val validXRange = (0..grid.first().size - presentTemplates.first().size)
            val validYRange = (0..grid.size - presentTemplates.first().size)

            val targetPresentTemplates = target.foldIndexed(emptyList<PresentTemplate>()) { index, acc, it ->
                acc + List(it) { presentTemplates[index] }
            }

            return targetPresentTemplates.all { targetPresentTemplates ->
                val presentTemplateVariants = mutableListOf(targetPresentTemplates)
                repeat(3) {
                    presentTemplateVariants.add(presentTemplateVariants.last().rotateRight())
                }

                validXRange.any { x ->
                    validYRange.any { y ->
                        presentTemplateVariants.any { it.applyTo(grid, Vector2I(x, y)) }
                    }
                }
            }
        }

        var count = 0L
        grids.forEachIndexed { index, grid ->
            if (solveForGrid(grid, targets[index])) {
                count++
            }
        }

        return count
    }

    fun part2(input: List<String>): Long {
        return 0L
    }

    // Test if implementation meets criteria from the description, like:
    //checkEquals(part1(listOf()), 0L)
    checkEquals(part2(listOf()), 0L)

    // Or read a large test input from the `src/day12/Day12_test.txt` file:
    val testInput = readInput("day12/Day12_test")
    checkEquals(part1(testInput), 2L)
    checkEquals(part2(testInput), 0L)

    // Read the input from the `src/day12/Day12.txt` file.
    val input = readInput("day12/Day12")
    part1(input).println("Part 1: ")
    part2(input).println("Part 2: ")
}
