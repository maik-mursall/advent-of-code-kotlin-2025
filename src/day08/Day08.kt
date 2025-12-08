package day08

import checkEquals
import println
import readInput
import kotlin.math.sqrt

data class Vector3D(val x: Int, val y: Int, val z: Int) {
    operator fun plus(other: Vector3D) = Vector3D(x + other.x, y + other.y, z + other.z)
    operator fun minus(other: Vector3D) = Vector3D(x - other.x, y - other.y, z - other.z)
    operator fun times(number: Int) = Vector3D(x * number, y * number, z * number)
    operator fun div(number: Int) = Vector3D(x / number, y / number, z / number)

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
            val parts = input.split(",").map { it.trim().toInt() }
            return Vector3D(parts[0], parts[1], parts[2])
        }
    }
}

fun main() {
    fun part1(input: List<String>): Long {
        return 0L
    }

    fun part2(input: List<String>): Long {
        return 0L
    }

    // Test if implementation meets criteria from the description, like:
    checkEquals(part1(listOf()), 0L)
    checkEquals(part2(listOf()), 0L)

    // Or read a large test input from the `src/day08/Day08_test.txt` file:
    val testInput = readInput("day08/Day08_test")
    checkEquals(part1(testInput), 0L)
    checkEquals(part2(testInput), 0L)

    // Read the input from the `src/day08/Day08.txt` file.
    val input = readInput("day08/Day08")
    part1(input).println("Part 1: ")
    part2(input).println("Part 2: ")
}
