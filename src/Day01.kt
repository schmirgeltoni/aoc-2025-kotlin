import classes.Day

/**
 * With 100 runs:
 *
 * Average runtime of part 1 was around `300 microseconds`
 *
 * Average runtime of part 2 was around `650 microseconds`
 */

object Day01 : Day<Int>() {
    override val testData: List<String>
        get() = """
            L68
            L30
            R48
            L5
            R60
            L55
            L1
            L99
            R14
            L82
        """.trimIndent().split('\n')
    override val part1ExampleSolution = 3
    override val part2ExampleSolution = 6

    override fun algorithmPart1(input: List<String>): Int {
        var dial = 50

        return input.count {

            dial = if (it.first() == 'L')
                (dial - it.drop(1).toInt()) % 100
            else
                (dial + it.drop(1).toInt()) % 100
            dial == 0
        }
    }

    override fun algorithmPart2(input: List<String>): Int {
        var dial = 50
        var count = 0

        for (rotation in input) {
            val dir = rotation.first()
            val steps = rotation.drop(1).toInt()

            repeat(steps) {
                dial = when (dir) {
                    'L' -> if (dial == 0) 99 else dial - 1
                    'R' -> if (dial == 99) 0 else dial + 1
                    else -> dial
                }
                if (dial == 0) count++
            }
        }
        return count
    }
}

fun main() {
    Day01.runEverything()
}

