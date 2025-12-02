fun main() {
    fun findInvalidIndices(input: List<String>, handleIndex: (Long) -> Long): Long {
        return input.fold(0L) { sumOfInvalidIds, it ->
            val (startId, endId) = it.split("-").map(String::toLong)

            sumOfInvalidIds + (startId..endId).fold(0L) { sumOfInvalidIdsInRange, id ->
                sumOfInvalidIdsInRange + handleIndex(id)
            }
        }
    }

    fun part1(input: List<String>): Long {
        return findInvalidIndices(input) { id ->
            val idString = id.toString()

            if (idString.length % 2 != 0) {
                return@findInvalidIndices 0L
            }

            val idStringHalfLength = idString.length / 2
            if (idString.take(idStringHalfLength) == idString.takeLast(idStringHalfLength)) {
                return@findInvalidIndices id
            } else {
                return@findInvalidIndices 0L
            }
        }
    }

    fun part2(input: List<String>): Long {
        return findInvalidIndices(input) { id ->
            val idString = id.toString()

            for (i in 1..(idString.length / 2)) {
                val subSet = idString.take(i)

                val allMatch = idString.windowed(subSet.length, subSet.length, true).all { it == subSet }

                if (allMatch) {
                    return@findInvalidIndices id
                }
            }

            0L
        }
    }

    // Test if implementation meets criteria from the description, like:
    checkEquals(part1(listOf("11-22")), 33L)
    checkEquals(part1(listOf("565653-565659")), 0L)
    checkEquals(part2(listOf("11-22")), 33L)
    checkEquals(part2(listOf("565653-565659")), 565656L)
    checkEquals(part2(listOf("11-22", "12-21", "565653-565659")), 565689L)

    // Or read a large test input from the `src/Day02_test.txt` file:
    val testInput = readInputCsv("Day02_test")
    checkEquals(part1(testInput), 1227775554L)
    checkEquals(part2(testInput), 4174379265L)

    // Read the input from the `src/Day02.txt` file.
    val input = readInputCsv("Day02")
    part1(input).println("Part 1: ")
    part2(input).println("Part 2: ")
}
