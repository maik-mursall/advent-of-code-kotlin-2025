package day10

import checkEquals
import println
import readInput

typealias ButtonConfiguration = Long

fun ButtonConfiguration.applyTo(lights: Long): Long {
    return lights xor this
}

data class MachineConfiguration(
    val lightsGoal: Long,
    val buttonConfigurations: List<ButtonConfiguration>,
    val joltageRequirements: List<Long>,
) {
    companion object {
        fun fromString(input: String): MachineConfiguration {
            val sections = input.split(' ')
            var lightsGoal = 0L
            val buttonConfigurations = mutableListOf<ButtonConfiguration>()
            var joltageRequirements = emptyList<Long>()

            sections.forEach {
                val content = it.substring(1, it.length - 1)
                when (it[0]) {
                    '[' -> {
                        lightsGoal = content.foldIndexed(0L) { index, acc, ch ->
                            if (ch == '#') {
                                acc or (1L shl index)
                            } else {
                                acc
                            }
                        }
                    }

                    '{' -> {
                        joltageRequirements = content.split(",").map(String::toLong)
                    }

                    '(' -> {
                        buttonConfigurations.add(
                            content.split(",")
                                .map { numStr -> numStr.toInt() }
                                .fold(0L) { acc, index ->
                                    acc or (1L shl index)
                                }
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

fun main() {
    fun part1(input: List<String>): Long {
        val maschineConfigurations = input.map(MachineConfiguration::fromString)

        return maschineConfigurations.sumOf { maschineConfiguration ->
            val buttonConfigurations = maschineConfiguration.buttonConfigurations

            var foundAtLeastOne = false
            fun step(
                lights: Long,
                lastButtonConfiguration: ButtonConfiguration,
                currentDepth: Int = 0
            ): Long = if (currentDepth >= 7) 1L else
                buttonConfigurations.minOf { buttonConfiguration ->
                    if (lastButtonConfiguration == buttonConfiguration) {
                        return@minOf Long.MAX_VALUE
                    }

                    val newLights = buttonConfiguration.applyTo(lights)

                    if (newLights == maschineConfiguration.lightsGoal) {
                        foundAtLeastOne = true
                        return@minOf 1L
                    } else {
                        1L + step(newLights, buttonConfiguration, currentDepth + 1)
                    }
                }

            step(0L, 0L)
        }
    }

    fun part2(input: List<String>): Long {
        return 0L
    }

    // Test MachineConfiguration parsing
    val testMachineConfigurationInput = "[.##.] (3) (1,3) (2) (2,3) (0,2) (0,1) {3,5,4,7}"
    val testMachineConfiguration = MachineConfiguration.fromString(testMachineConfigurationInput)
    checkEquals(testMachineConfiguration.lightsGoal, 6L)
    checkEquals(
        testMachineConfiguration.buttonConfigurations, listOf(
            8L,
            10L,
            4L,
            12L,
            5L,
            3L
        )
    )
    checkEquals(testMachineConfiguration.joltageRequirements, listOf(3L, 5L, 4L, 7L))

    // Test if implementation meets criteria from the description, like:
    checkEquals(part1(listOf(testMachineConfigurationInput)), 2L)
    checkEquals(part2(listOf()), 0L)

    println("Basic checks done!")

    // Or read a large test input from the `src/day10/Day10_test.txt` file:
    val testInput = readInput("day10/Day10_test")
    checkEquals(part1(testInput), 7L)
    checkEquals(part2(testInput), 0L)

    println("Test checks done!")

    // Read the input from the `src/day10/Day10.txt` file.
    val input = readInput("day10/Day10")
    part1(input).println("Part 1: ")
    part2(input).println("Part 2: ")
}
