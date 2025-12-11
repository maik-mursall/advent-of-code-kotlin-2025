package math

import kotlin.math.sqrt

data class Vector3L(val x: Long, val y: Long, val z: Long) {
    operator fun plus(other: Vector3L) = Vector3L(x + other.x, y + other.y, z + other.z)
    operator fun minus(other: Vector3L) = Vector3L(x - other.x, y - other.y, z - other.z)
    operator fun times(number: Long) = Vector3L(x * number, y * number, z * number)
    operator fun div(number: Long) = Vector3L(x / number, y / number, z / number)

    fun magnitude(): Double {
        return sqrt((x * x + y * y + z * z).toDouble())
    }

    fun distanceTo(other: Vector3L): Double {
        val dx = x - other.x
        val dy = y - other.y
        val dz = z - other.z
        return Vector3L(dx, dy, dz).magnitude()
    }

    companion object {
        fun fromString(input: String): Vector3L {
            val parts = input.split(",").map { it.trim().toLong() }
            return Vector3L(parts[0], parts[1], parts[2])
        }
    }
}