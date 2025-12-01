fun main() {
    fun part1(input: List<String>): Int {
        var dialPosition = 50
        var hitCount = 0
        input.forEach { inputLine ->
            val turnOperator = inputLine.first()
            val turnCount = inputLine.slice(1 until inputLine.length).toInt()

            val delta = when (turnOperator) {
                'R' -> turnCount % 100
                'L' -> -(turnCount % 100)
                else -> throw IllegalStateException("Unknown in $inputLine")
            }

            val unclampedNewDialPosition = dialPosition + delta

            if (unclampedNewDialPosition >= 100) {
                // unclampedNewDialPosition is positive
                dialPosition = unclampedNewDialPosition % 100
            } else if (unclampedNewDialPosition < 0) {
                // unclampedNewDialPosition is negative
                dialPosition = 100 + unclampedNewDialPosition
            } else {
                // unclampedNewDialPosition = 0
                dialPosition = unclampedNewDialPosition
            }

            if (dialPosition == 0) {
                hitCount++
            }
        }

        return hitCount
    }

    fun part2(input: List<String>): Int {
        var dialPosition = 50
        var hitCount = 0
        input.forEach { inputLine ->
            val turnOperator = inputLine.first()
            val turnCount = inputLine.slice(1 until inputLine.length).toInt()

            val wrapCount = turnCount / 100
            hitCount += wrapCount

            val delta = when (turnOperator) {
                'R' -> turnCount % 100
                'L' -> -(turnCount % 100)
                else -> throw IllegalStateException("Unknown in $inputLine")
            }

            val unclampedNewDialPosition = dialPosition + delta

            if (unclampedNewDialPosition >= 100) {
                // unclampedNewDialPosition is positive
                if (dialPosition != 0) {
                    hitCount++
                }
                dialPosition = unclampedNewDialPosition % 100
            } else if (unclampedNewDialPosition < 0) {
                // unclampedNewDialPosition is negative
                if (dialPosition != 0) {
                    hitCount++
                }
                dialPosition = 100 + unclampedNewDialPosition
            } else {
                // unclampedNewDialPosition = 0
                if (unclampedNewDialPosition == 0) {
                    hitCount++
                }
                dialPosition = unclampedNewDialPosition
            }
        }

        return hitCount
    }

    // Test if implementation meets criteria from the description, like:
    check(part1(listOf("R50")) == 1)
    check(part2(listOf("R50")) == 1)

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day01_test")
    check(part1(testInput) == 3)
    check(part2(testInput) == 6)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}
