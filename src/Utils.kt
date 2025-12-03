import java.math.BigInteger
import java.security.MessageDigest
import kotlin.io.path.Path
import kotlin.io.path.readText
import kotlin.time.Duration
import kotlin.time.measureTime

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = Path("src/$name.txt").readText().trim().lines()

/**
 * Converts string to md5 hash.
 */
fun String.md5() = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray()))
    .toString(16)
    .padStart(32, '0')

object Logger {
    var shouldLog = true
}

/**
 * Logs any value to the console and returns the same value.
 * Therefore, it can be put into any chain of operation to see the value of the current stage
 * while not disrupting the operation.
 * @param prefix a prefix [String] to be logged before the value.
 * @param postfix a postfix [String] to be logged after the value.
 * @param overrideGlobalLogger makes this always log even if global logging is deactivated
 */
fun <T> T.log(prefix: String = "", postfix: String = "", overrideGlobalLogger: Boolean = false): T = this.also {
    if (Logger.shouldLog || overrideGlobalLogger)
        println(prefix + it + postfix)
}

fun averageRuntime(repetitions: Int = 100, function: () -> Unit): Duration =
    List(repetitions) { measureTime(function) }
        .reduce(Duration::plus) / repetitions

/**
 * Finds the largest element [T] of the list and returns it and the index it's found at
 */
fun <T : Comparable<T>> List<T>.findMaxIndexed(): IndexedValue<T> {
    if (isEmpty()) throw NoSuchElementException()
    var max = this.first()
    var index = 0
    for (i in 1..lastIndex) {
        val e = this[i]
        if (e > max) {
            max = e
            index = i
        }
    }
    return IndexedValue(index, max)
}

/**
 * Concat two [Integer]s.
 */
infix fun Int.concat(other: Int): Int = (this.toString() + other.toString()).toInt()

/**
 * Concat two [Long]s.
 */
infix fun Long.concat(other: Long): Long = (this.toString() + other.toString()).toLong()

fun <T> IndexedValue<T>.log() : String = "$value at $index"