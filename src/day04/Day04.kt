package day04

import checkEquals
import println
import readInput

fun main() {
    fun findValidIndices(grid: List<String>, onIndexFound: (Int, Int) -> Unit) {
        grid.indices.forEach { rowIndex ->
            val rowIndices = grid[rowIndex].indices

            rowIndices.forEach { colIndex ->
                if (grid[rowIndex][colIndex] != '@') return@forEach
                val colIndices = (colIndex - 1)..(colIndex + 1)
                val rowIndices = (rowIndex - 1)..(rowIndex + 1)

                val numberOfNeighbours = rowIndices.sumOf rowSumOf@{ currentRowIndex ->
                    if (currentRowIndex !in grid.indices) return@rowSumOf 0L
                    val currentRowIndices = grid[currentRowIndex].indices

                    colIndices.sumOf colSumOf@{ currentColIndex ->
                        if (currentColIndex == colIndex && currentRowIndex == rowIndex) return@colSumOf 0
                        if (currentColIndex !in currentRowIndices) return@colSumOf 0

                        val neighborChar = grid[currentRowIndex][currentColIndex]

                        if (neighborChar == '@') 1L else 0L
                    }
                }

                if (numberOfNeighbours < 4) {
                    onIndexFound(rowIndex, colIndex)
                }
            }
        }
    }

    fun part1(grid: List<String>): Long {
        var count = 0L
        findValidIndices(grid) { _,_ ->
            count++
        }
        return count
    }

    fun part2Step(grid: List<String>) = mutableListOf<Pair<Int, Int>>().apply {
        findValidIndices(grid) { rowIndex, colIndex ->
            add(rowIndex to colIndex)
        }
    }

    fun part2(grid: List<String>): Long {
        val currentGrid = grid.toMutableList()
        var currentCount = 0L

        while(true) {
            val indicesToRemove = part2Step(currentGrid)
            if (indicesToRemove.isEmpty()) {
                break
            }
            currentCount += indicesToRemove.size
            indicesToRemove.forEach { (rowIndex, colIndex) ->
                val rowChars = currentGrid[rowIndex].toCharArray()
                rowChars[colIndex] = '.'
                currentGrid[rowIndex] = String(rowChars)
            }
        }

        return currentCount
    }

    // Test if implementation meets criteria from the description, like:
    //checkEquals(part1(listOf()), 0L)
    //checkEquals(part2(listOf()), 0L)

    // Or read a large test input from the `src/Day04_test.txt` file:
    val testInput = readInput("day04/Day04_test")
    checkEquals(part1(testInput), 13L)
    checkEquals(part2(testInput), 43L)

    // Read the input from the `src/Day04.txt` file.
    val input = readInput("day04/Day04")
    part1(input).println("Part 1: ")
    part2(input).println("Part 2: ")
}
