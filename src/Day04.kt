import classes.Day

/**
 * With 100 runs:
 *
 * Average runtime of part 1 was around `1 millisecond`
 *
 * Average runtime of part 2 was around `10 milliseconds`
 */

object Day04 : Day<Int>() {

    enum class Tile {
        Paper,
        Empty
    }

    override val testData: List<String> = """
        ..@@.@@@@.
        @@@.@.@.@@
        @@@@@.@.@@
        @.@@@@..@.
        @@.@@@@.@@
        .@@@@@@@.@
        .@.@.@.@@@
        @.@@@.@@@@
        .@@@@@@@@.
        @.@.@@@.@.
    """.trimIndent().split("\n")

    override val part1ExampleSolution: Int = 13

    override val part2ExampleSolution: Int = 43

    override fun algorithmPart1(input: List<String>): Int {
        val grid = input.map { it.toList().map { c -> if (c == '.') Tile.Empty else Tile.Paper } }
        //grid.logMatrix(mapOf(Tile.Paper to "@", Tile.Empty to "."))
        var count = 0
        grid.forEachIndexed { y, row ->
            row.forEachIndexed { x, tile ->
                if (tile == Tile.Paper)
                    count += if (grid.countNeighbours(Point(x, y), tile) < 4) 1 else 0
            }
        }

        return count
    }


    override fun algorithmPart2(input: List<String>): Int {
        val grid = input.map { it.toList().map { c -> if (c == '.') Tile.Empty else Tile.Paper }.toMutableList() }
            .toMutableList()

        var removedPaperTotal = 0

        do {
            //grid.logMatrix(mapOf(Tile.Paper to "@", Tile.Empty to "."))
            val removedPaper = grid.removePaperRolls()
            removedPaperTotal += removedPaper
        } while (removedPaper > 0)

        return removedPaperTotal
    }

    fun MutableList<MutableList<Tile>>.removePaperRolls(): Int {
        var removed = 0
        forEachIndexed { y, row ->
            row.forEachIndexed { x, tile ->
                val point = Point(x, y)
                if (tile == Tile.Paper && countNeighbours(point, tile) < 4)
                    row[x] = Tile.Empty.also { removed += 1 }

            }
        }
        return removed
    }
}

fun main() {
    Day04.runEverything()

}