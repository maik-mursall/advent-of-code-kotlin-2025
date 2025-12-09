package day08

import checkEquals
import println
import readInput
import kotlin.math.sqrt

data class Vector3D(val x: Long, val y: Long, val z: Long) {
    operator fun plus(other: Vector3D) = Vector3D(x + other.x, y + other.y, z + other.z)
    operator fun minus(other: Vector3D) = Vector3D(x - other.x, y - other.y, z - other.z)
    operator fun times(number: Long) = Vector3D(x * number, y * number, z * number)
    operator fun div(number: Long) = Vector3D(x / number, y / number, z / number)

    fun magnitude(): Double {
        return sqrt((x * x + y * y + z * z).toDouble())
    }

    fun distanceTo(other: Vector3D): Double {
        val dx = x - other.x
        val dy = y - other.y
        val dz = z - other.z
        return Vector3D(dx, dy, dz).magnitude()
    }

    companion object {
        fun fromString(input: String): Vector3D {
            val parts = input.split(",").map { it.trim().toLong() }
            return Vector3D(parts[0], parts[1], parts[2])
        }
    }
}

data class Link(
    val from: Vector3D,
    val to: Vector3D,
    val distance: Double
)

fun main() {
    fun part1(input: List<String>, maxIterations: Int): Long {
        val junctionBoxLocations = input.map(Vector3D::fromString)
        val possibleEdges = junctionBoxLocations.flatMapIndexed { index, junctionBoxToCheck ->
            junctionBoxLocations
                .drop(index + 1)
                .map { Link(junctionBoxToCheck, it, junctionBoxToCheck.distanceTo(it)) }
        }.sortedBy { it.distance }

        val circuits = mutableListOf<Set<Vector3D>>()
        possibleEdges.take(maxIterations).forEach { elementsWithSmallestDistance ->
            val fromCircuit = circuits.indexOfFirst { it.contains(elementsWithSmallestDistance.from) }
            val toCircuit = circuits.indexOfFirst { it.contains(elementsWithSmallestDistance.to) }

            if (fromCircuit == -1 && toCircuit == -1) {
                circuits.add(setOf(elementsWithSmallestDistance.from, elementsWithSmallestDistance.to))
            } else if (fromCircuit == toCircuit) {
                // no-op
            } else if (fromCircuit == -1) {
                circuits[toCircuit] += elementsWithSmallestDistance.from
            } else if (toCircuit == -1) {
                circuits[fromCircuit] += elementsWithSmallestDistance.to
            } else {
                circuits[fromCircuit] = circuits[fromCircuit] union circuits[toCircuit]
                circuits.removeAt(toCircuit)
            }
        }

        return circuits.sortedByDescending { it.size }.take(3).fold(1L) { acc, ds -> acc * ds.size }
    }

    fun part2(input: List<String>): Long {
        return 0L
    }

    // Test if implementation meets criteria from the description, like:
    val minimalTestData = listOf(
        "162,817,812",
        "57,618,57",
        "906,360,560",
        "592,479,940",
    )
    //checkEquals(part1(minimalTestData), 0L)
    //checkEquals(part2(minimalTestData), 0L)

    // Or read a large test input from the `src/day08/Day08_test.txt` file:
    val testInput = readInput("day08/Day08_test")
    checkEquals(part1(testInput, 10), 40L)
    checkEquals(part2(testInput), 0L)

    println("All tests passed. ===============================")

    // Read the input from the `src/day08/Day08.txt` file.
    val input = readInput("day08/Day08")
    part1(input, 1000).println("Part 1: ") // 27825, 15860 => wrong; 131150 => correct
    part2(input).println("Part 2: ")
}
