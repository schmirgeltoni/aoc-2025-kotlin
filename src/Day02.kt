import classes.Day

object Day02 : Day<Long>() {
    override val testData: List<String> = """
        11-22,95-115,998-1012,1188511880-1188511890,222220-222224,
        1698522-1698528,446443-446449,38593856-38593862,565653-565659,
        824824821-824824827,2121212118-2121212124
    """.trimIndent().split(',').map { it.trim() }

    override val part1ExampleSolution: Long = 1227775554L
    override val part2ExampleSolution: Long = 4174379265L

    override val data = readInput("Day$day")[0].split(',')

    override fun algorithmPart1(input: List<String>): Long = baseAlgorithm(input, Regex("(\\d+)\\1"))

    override fun algorithmPart2(input: List<String>): Long = baseAlgorithm(input, Regex("(\\d+)\\1+"))

    private fun baseAlgorithm(input: List<String>, regex: Regex): Long {
        var sum = 0L
        for (range in input) {
            val (start, end) = range.split('-', limit = 2)
            for (id in start.toLong()..end.toLong()) {
                if (regex.matches(id.toString())) {
                    sum += id
                }
            }
        }

        return sum
    }
}

fun main() {
    Day02.runEverything()
}