package day08

import math.Vector3L
import checkEquals
import println
import readInput

data class Link(
    val from: Vector3L,
    val to: Vector3L,
    val distance: Double
)

fun main() {
    fun calculatePossibleLinks(junctionBoxLocations: List<Vector3L>) =
        junctionBoxLocations.flatMapIndexed { index, junctionBoxToCheck ->
            junctionBoxLocations
                .drop(index + 1)
                .map { Link(junctionBoxToCheck, it, junctionBoxToCheck.distanceTo(it)) }
        }.sortedBy { it.distance }

    fun MutableList<Set<Vector3L>>.performUnion(link: Link) {
        val fromCircuit = this.indexOfFirst { it.contains(link.from) }
        val toCircuit = this.indexOfFirst { it.contains(link.to) }

        if (fromCircuit == -1 && toCircuit == -1) {
            this.add(setOf(link.from, link.to))
        } else if (fromCircuit == toCircuit) {
            // no-op
        } else if (fromCircuit == -1) {
            this[toCircuit] += link.from
        } else if (toCircuit == -1) {
            this[fromCircuit] += link.to
        } else {
            this[fromCircuit] = this[fromCircuit] union this[toCircuit]
            this.removeAt(toCircuit)
        }
    }

    fun solveForCircuits(
        links: List<Link>
    ): List<Set<Vector3L>> {
        return links.fold(mutableListOf()) { circuits, link ->
            circuits.performUnion(link)
            circuits
        }
    }

    fun part1(input: List<String>, maxIterations: Int): Long {
        val junctionBoxLocations = input.map(Vector3L::fromString)
        val possibleEdges = calculatePossibleLinks(junctionBoxLocations)
        val circuits = solveForCircuits(possibleEdges.take(maxIterations))

        return circuits.sortedByDescending { it.size }.take(3).fold(1L) { acc, ds -> acc * ds.size }
    }

    fun solveForSingleCircuit(
        links: List<Link>,
        numberOfJunctions: Int,
    ): Link? {
        val circuits = mutableListOf<Set<Vector3L>>()
        links.forEach { link ->
            circuits.performUnion(link)

            if (circuits.singleOrNull()?.size == numberOfJunctions) {
                return link
            }
        }
        return null
    }

    fun part2(input: List<String>): Long {
        val junctionBoxLocations = input.map(Vector3L::fromString)
        val finalLink = solveForSingleCircuit(calculatePossibleLinks(junctionBoxLocations), input.size)!!

        return finalLink.from.x * finalLink.to.x
    }

    // Or read a large test input from the `src/day08/Day08_test.txt` file:
    val testInput = readInput("day08/Day08_test")
    checkEquals(part1(testInput, 10), 40L)
    checkEquals(part2(testInput), 25272L)

    println("All tests passed. ===============================")

    // Read the input from the `src/day08/Day08.txt` file.
    val input = readInput("day08/Day08")
    part1(input, 1000).println("Part 1: ")
    part2(input).println("Part 2: ")
}
