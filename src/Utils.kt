import java.math.BigInteger
import java.security.MessageDigest
import kotlin.io.path.Path
import kotlin.io.path.readText

/**
 * Reads the entire content of the given txt file.
 */
fun readFile(name: String) = Path("src/$name.txt").readText().trim()

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = readFile(name).lines()

/**
 * Reads comma-separated values from the given input txt file.
 */
fun readInputCsv(name: String) = readFile(name).split(",").map(String::trim)

/**
 * Converts string to md5 hash.
 */
fun String.md5() = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray()))
    .toString(16)
    .padStart(32, '0')

/**
 * The cleaner shorthand for printing output.
 */
fun Any?.println() = println(this)

/**
 * Shorthand for printing output with optional prefix and postfix.
 */
fun Any?.println(prefix: String = "", postfix: String = "") = kotlin.io.println("$prefix$this$postfix")

fun checkEquals(expected: Any?, actual: Any?) {
    check(actual == expected) { "$actual is not equal to $expected" }
}
