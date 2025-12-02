fun main() {
    fun prepareIndices(input: List<String>, handleIndex: (Long) -> Long): Long {
        return input.fold(0L) { sumOfInvalidIds, it ->
            val (startId, endId) = it.split("-").map(String::toLong)

            sumOfInvalidIds + (startId..endId).fold(0L) { sumOfInvalidIdsInRange, id ->
                sumOfInvalidIdsInRange + handleIndex(id)
            }
        }
    }

    fun part1(input: List<String>): Long {
        return prepareIndices(input) { id ->
            val idStr = id.toString()

            if (idStr.length % 2 != 0) {
                return@prepareIndices 0L
            }

            val idStrHalfLength = idStr.length / 2
            if (idStr.take(idStrHalfLength) == idStr.takeLast(idStrHalfLength)) {
                return@prepareIndices id
            } else {
                return@prepareIndices 0L
            }
        }
    }

    fun part2(input: List<String>): Long {
        return prepareIndices(input) { id ->
            0
        }
    }

    // Test if implementation meets criteria from the description, like:
    check(part1(listOf("11-22")) == 33L)
    check(part1(listOf("565653-565659")) == 0L)

    // Or read a large test input from the `src/Day02_test.txt` file:
    val testInput = readInputCsv("Day02_test")
    check(part1(testInput) == 1227775554L)

    // Read the input from the `src/Day02.txt` file.
    val input = readInputCsv("Day02")
    part1(input).println()
}
