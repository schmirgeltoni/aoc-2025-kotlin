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
fun <T> T.log(
    prefix: String = "",
    postfix: String = "",
    overrideGlobalLogger: Boolean = false,
    replacement: (T) -> Any = {
        it.toString()
    }
): T = this.also {
    if (Logger.shouldLog || overrideGlobalLogger)
        println(prefix + replacement(this) + postfix)
}

/**
 * Prints the 2D collection along with indices along the x and y coordinate
 * @param replacements A map of elements of type [T], where the given value [String]
 * should be printed instead of the element itself
 * @param overrideGlobalLogger makes this always log even if global logging is deactivated
 * @return the matrix itself
 */
fun <T> Collection<Collection<T>>.logMatrix(
    replacements: Map<T, String>? = null,
    overrideGlobalLogger: Boolean = false
): Collection<Collection<T>> {
    if (Logger.shouldLog || overrideGlobalLogger) {
        print("  ")
        repeat(size) { print(it % 10) }
        println()
        var row = 0
        forEach {
            print("${row++ % 10} ")
            it.forEach { element ->
                print(replacements?.get(element) ?: element.toString())
            }
            println()
        }
    }
    return this
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

fun <T> IndexedValue<T>.log(): String = "$value at $index"

data class Point(val x: Int, val y: Int) {
    override fun toString(): String = "($y|$x)"
}

/**
 * Checks what is above the [point].
 * @return the value above the [point] or `null` if it's not possible to access.
 */
fun <T> List<List<T>>.above(point: Point): T? = if (point.y != 0) this[point.y - 1][point.x] else null

/**
 * Checks what is below the [point].
 * @return the value below the [point] or `null` if it's not possible to access.
 */
fun <T> List<List<T>>.below(point: Point): T? = if (point.y != size - 1) this[point.y + 1][point.x] else null

/**
 * Checks what is left of the [point].
 * @return the value left of the [point] or `null` if it's not possible to access.
 */
fun <T> List<List<T>>.leftOf(point: Point): T? = if (point.x != 0) this[point.y][point.x - 1] else null

/**
 * Checks what is right of the [point].
 * @return the value right of the [point] or `null` if it's not possible to access.
 */
fun <T> List<List<T>>.rightOf(point: Point): T? =
    if (point.x != this[point.y].size - 1) this[point.y][point.x + 1] else null

/**
 * @param p The point to be checked.
 * @param element The element to be checked for.
 * @return the amount of times a value of [element] neighbours [p]
 */
fun <T> List<List<T>>.countNeighbours(p: Point, element: T): Int {
    if (p.y !in indices || p.x !in this[0].indices)
        throw IndexOutOfBoundsException("The point $p is not in the matrix")

    var count = 0

    val above = above(p)
    val rightOf = rightOf(p)
    val below = below(p)
    val leftOf = leftOf(p)

    count += if (above == element) 1 else 0
    count += if (rightOf == element) 1 else 0
    count += if (below == element) 1 else 0
    count += if (leftOf == element) 1 else 0

    if (above != null) {
        if (leftOf != null)
        // top left
            count += if (this[p.y - 1][p.x - 1] == element) 1 else 0
        if (rightOf != null)
        //top right
            count += if (this[p.y - 1][p.x + 1] == element) 1 else 0
    }
    if (below != null) {
        if (leftOf != null)
        // bottom left
            count += if (this[p.y + 1][p.x - 1] == element) 1 else 0
        if (rightOf != null)
        //bottom right
            count += if (this[p.y + 1][p.x + 1] == element) 1 else 0
    }

    return count
}

fun String.splitInTwo(delimiter: String): Pair<String, String> {
    return split(delimiter, limit = 2).let {
        it.first() to it.last()
    }
}

fun main() {
    Day01.benchmarkPart1()
    Day01.benchmarkPart2()
    "".log()
    IndexedValue(0, 0).log()
    Day03.oldPart1Algorithm(listOf())
    "".md5()
    listOf<List<Any>>().logMatrix()
}
