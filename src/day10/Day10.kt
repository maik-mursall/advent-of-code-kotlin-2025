package day10

import checkEquals
import println
import readInput

typealias ButtonConfiguration = List<Int>

fun ButtonConfiguration.applyTo(lights: CharArray): CharArray {
    forEach { index ->
        lights[index] = if (lights[index] == '.') '#' else '.'
    }

    return lights
}

data class MachineConfiguration(
    val lightsGoal: String,
    val buttonConfigurations: List<ButtonConfiguration>,
    val joltageRequirements: List<Long>,
) {
    companion object {
        fun fromString(input: String): MachineConfiguration {
            val sections = input.split(' ')
            var lightsGoal = ""
            val buttonConfigurations = mutableListOf<ButtonConfiguration>()
            var joltageRequirements = emptyList<Long>()

            sections.forEach {
                val content = it.substring(1, it.length - 1)
                when (it[0]) {
                    '[' -> {
                        lightsGoal = content
                    }

                    '{' -> {
                        joltageRequirements = content.split(",").map(String::toLong)
                    }

                    '(' -> {
                        buttonConfigurations.add(
                            content.split(",").map { numStr -> numStr.toInt() }
                        )
                    }
                }
            }

            return MachineConfiguration(
                lightsGoal = lightsGoal,
                buttonConfigurations = buttonConfigurations,
                joltageRequirements = joltageRequirements
            )
        }
    }
}

typealias MachineState = CharArray

fun main() {
    fun part1(input: List<String>): Long {
        return 0L
    }

    fun part2(input: List<String>): Long {
        return 0L
    }

    // Test MachineConfiguration parsing
    val testMachineConfigurationInput = "[.##.] (3) (1,3) (2) (2,3) (0,2) (0,1) {3,5,4,7}"
    val testMachineConfiguration = MachineConfiguration.fromString(testMachineConfigurationInput)
    checkEquals(testMachineConfiguration.lightsGoal, ".##.")
    checkEquals(
        testMachineConfiguration.buttonConfigurations, listOf(
            listOf(3),
            listOf(1, 3),
            listOf(2),
            listOf(2, 3),
            listOf(0, 2),
            listOf(0, 1)
        )
    )
    checkEquals(testMachineConfiguration.joltageRequirements, listOf(3L, 5L, 4L, 7L))

    // Test if implementation meets criteria from the description, like:
    checkEquals(part1(listOf()), 0L)
    checkEquals(part2(listOf()), 0L)

    // Or read a large test input from the `src/day10/Day09_test.txt` file:
    val testInput = readInput("day10/Day09_test")
    checkEquals(part1(testInput), 0L)
    checkEquals(part2(testInput), 0L)

    // Read the input from the `src/day10/Day10.txt` file.
    val input = readInput("day10/Day10")
    part1(input).println("Part 1: ")
    part2(input).println("Part 2: ")
}
