package classes

import readInput

abstract class Day {

    val name: String
        get() = this::class.simpleName.toString()

    val day: String
        get() = name.takeLast(2)

    abstract val testData: List<String>
    abstract val part1ExampleSolution: Any
    abstract val part2ExampleSolution: Any

    abstract fun algorithmPart1(input: List<String>): Any
    abstract fun algorithmPart2(input: List<String>): Any

    fun part1Example() {
        check(algorithmPart1(testData) == part1ExampleSolution)
    }

    /**
     * Run part one of this day
     */
    fun part1(): Any = algorithmPart2(readInput("Day${day}"))

    fun part2Example() {
        check(algorithmPart2(testData) == part2ExampleSolution)
    }

    /**
     * Run part two of this day
     */
    fun part2(): Any = algorithmPart2(readInput("Day${day}"))


    fun runEverything() {
        part1Example()
        part2Example()
        println(
            """
            $name
            The result of part one is: ${part1()}
            The result of part two is: ${part2()}
        """.trimIndent()
        )
    }
}