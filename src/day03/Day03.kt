package day03

import checkEquals
import println
import readInput

fun main() {
    fun part1(input: List<String>) = input.sumOf { batteryBank ->
        (0 until (batteryBank.length - 1)).maxOf { i ->
            val currentCandidate = batteryBank[i]
            batteryBank.substring(i + 1, batteryBank.length).maxOf { nextCandidate ->
                "$currentCandidate$nextCandidate".toLong()
            }
        }
    }

    fun part2(input: List<String>) = input.sumOf { batteryBank ->
        val usedBatteries = mutableListOf<Char>()

        var currentIndex = 0
        while (usedBatteries.size < 12) {
            val maxIndex = batteryBank.length - (12 - usedBatteries.size)
            if (currentIndex > maxIndex) {
                usedBatteries.addAll(batteryBank.slice(currentIndex until batteryBank.lastIndex).toList())
                break
            }

            val maxBatteryIndex = (currentIndex..maxIndex).maxBy { batteryBank[it] }
            usedBatteries.add(batteryBank[maxBatteryIndex])
            currentIndex = maxBatteryIndex + 1
        }

        usedBatteries.joinToString("").toLong()
    }

    // Test if implementation meets criteria from the description, like:
    checkEquals(part1(listOf("12345")), 45L)
    checkEquals(part2(listOf("987654321111111")), 987654321111L)
    checkEquals(part2(listOf("191876543211119")), 987654321119L)
    checkEquals(part2(listOf("818181911112111")), 888911112111L)

    // Or read a large test input from the `src/Day03_test.txt` file:
    val testInput = readInput("day03/Day03_test")
    checkEquals(part1(testInput), 357L)
    checkEquals(part2(testInput), 3121910778619L)

    // Read the input from the `src/Day03.txt` file.
    val input = readInput("day03/Day03")
    part1(input).println("Part 1: ")
    part2(input).println("Part 2: ")
}
