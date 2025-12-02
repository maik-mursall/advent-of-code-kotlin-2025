package day01

import println
import readInput

data class SafeSimulationResult(
    val zeroCount: Int,
    val wrapCount: Int
) {
    val totalCount = zeroCount + wrapCount
}

fun main() {
    fun simulatePasswordCombination(
        input: List<String>
    ): SafeSimulationResult {
        var dialPosition = 50
        var zeroCount = 0
        var wrapCount = 0

        fun evaluateStepResult(newDialPosition: Int, wrapped: Boolean) {
            // If dialPosition was 0 before, we don't count any change as a wrap.
            if (wrapped && dialPosition != 0) {
                if (newDialPosition == 0) {
                    zeroCount++
                } else {
                    wrapCount++
                }
            } else if (newDialPosition == 0) {
                zeroCount++
            }
        }

        input.forEach { inputLine ->
            val turnOperator = inputLine.first()
            val turnCount = inputLine.slice(1 until inputLine.length).toInt()

            wrapCount += turnCount / 100

            val delta = when (turnOperator) {
                'R' -> turnCount % 100
                'L' -> -(turnCount % 100)
                else -> throw IllegalStateException("Unknown in $inputLine")
            }

            val unclampedNewDialPosition = dialPosition + delta

            if (unclampedNewDialPosition >= 100) {
                // unclampedNewDialPosition is positive
                val newDialPosition = unclampedNewDialPosition % 100
                evaluateStepResult(newDialPosition, true)
                dialPosition = newDialPosition
            } else if (unclampedNewDialPosition < 0) {
                // unclampedNewDialPosition is negative
                val newDialPosition = 100 + unclampedNewDialPosition
                evaluateStepResult(newDialPosition, true)
                dialPosition = newDialPosition
            } else {
                // unclampedNewDialPosition = 0
                evaluateStepResult(unclampedNewDialPosition, false)
                dialPosition = unclampedNewDialPosition
            }
        }

        return SafeSimulationResult(
            zeroCount = zeroCount,
            wrapCount = wrapCount
        )
    }

    fun part1(input: List<String>): Int {
        return simulatePasswordCombination(input).zeroCount
    }

    fun part2(input: List<String>): Int {
        return simulatePasswordCombination(input).totalCount
    }

    // Test if implementation meets criteria from the description, like:
    check(part1(listOf("R50")) == 1)
    check(part2(listOf("R50")) == 1)

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("day01/Day01_test")
    check(part1(testInput) == 3)
    check(part2(testInput) == 6)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("day01/Day01")
    part1(input).println()
    part2(input).println()
}
