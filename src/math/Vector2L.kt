package math

import kotlin.math.sqrt

data class Vector2L(val x: Long, val y: Long) {
    operator fun plus(other: Vector2L) = Vector2L(x + other.x, y + other.y)
    operator fun minus(other: Vector2L) = Vector2L(x - other.x, y - other.y)
    operator fun times(number: Long) = Vector2L(x * number, y * number)
    operator fun div(number: Long) = Vector2L(x / number, y / number)

    fun magnitude(): Double {
        return sqrt((x * x + y * y).toDouble())
    }

    fun distanceTo(other: Vector2L): Double {
        val dx = x - other.x
        val dy = y - other.y
        return Vector2L(dx, dy).magnitude()
    }

    companion object {
        fun fromString(input: String): Vector2L {
            val parts = input.split(",").map { it.trim().toLong() }
            return Vector2L(parts[0], parts[1])
        }
    }
}