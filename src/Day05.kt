import classes.Day

/**
 * With 100 runs:
 *
 * Average runtime of part 1 was around `750 microseconds`
 *
 * Average runtime of part 2 was around `130 microseconds`
 */

object Day05 : Day() {
    override val testData: List<String> = """
        3-5
        10-14
        16-20
        12-18

        1
        5
        8
        11
        17
        32
    """.trimIndent().split("\n")

    override val part1ExampleSolution: Int = 3
    override val part2ExampleSolution: Long = 14L

    override fun algorithmPart1(input: List<String>): Int {
        var freshIDs = 0

        val emptyLineIndex = input.indexOf("")

        val combinedRanges = combineRanges(input.subList(0, emptyLineIndex).map {
            it.splitInTwo("-")
        }.map {
            it.first.toLong()..it.second.toLong()
        })

        val ingredientIds = input.subList(emptyLineIndex + 1, input.size).map { it.toLong() }

        for (id in ingredientIds) {
            for (range in combinedRanges) {
                if (id in range) {
                    freshIDs++
                    break
                }
            }
        }

        return freshIDs
    }

    override fun algorithmPart2(input: List<String>): Long {
        return combineRanges(input.subList(0, input.indexOf("")).map {
            it.splitInTwo("-")
        }.map {
            it.first.toLong()..it.second.toLong()
        }).sumOf { it.last - it.first + 1 }


    }

    fun combineRanges(ranges: List<LongRange>): List<LongRange> {
        val sortedRanges = ranges.sortedBy { it.first }
        val combinedRanges = mutableListOf<LongRange>()

        var currentStart = sortedRanges.first().first
        var currentEnd = sortedRanges.first().last

        for (i in 1..sortedRanges.lastIndex) {
            val nextRange = sortedRanges[i]

            if (nextRange.first <= currentEnd + 1) {
                currentEnd = maxOf(currentEnd, nextRange.last)
            } else {
                // No overlap, add the current range and start a new one
                combinedRanges.add(currentStart..currentEnd)
                currentStart = nextRange.first
                currentEnd = nextRange.last
            }
        }

        combinedRanges.add(currentStart..currentEnd)
        return combinedRanges
    }
}

fun main() {
    Day05.runEverything()
}