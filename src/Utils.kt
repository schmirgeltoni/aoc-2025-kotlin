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

/**
 * Logs any value to the console and returns the same value.
 * Therefore, it can be put into any chain of operation to see the value of the current stage
 * while not disrupting the operation.
 * @param prefix a prefix [String] to be logged before the value.
 * @param postfix a postfix [String] to be logged after the value.
 */
fun <T> T.log(prefix: String = "", postfix: String = ""): T = this.also {
    println(prefix + it + postfix)
}

fun averageRuntime(repetitions: Int = 25, function: () -> Unit): Duration =
    List(repetitions) { measureTime(function) }
        .reduce(Duration::plus) / repetitions
