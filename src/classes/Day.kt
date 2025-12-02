package classes

import averageRuntime
import log
import readInput
import kotlin.time.TimedValue
import kotlin.time.measureTimedValue

abstract class Day<T> {
    val name: String
        get() = this::class.simpleName.toString()

    val day: String
        get() = name.takeLast(2)

    abstract val testData: List<String>
    abstract val part1ExampleSolution: T
    abstract val part2ExampleSolution: T

    abstract fun algorithmPart1(input: List<String>): T
    abstract fun algorithmPart2(input: List<String>): T

    fun part1Example() {
        check(algorithmPart1(testData).log("Result of the part one test was: ") == part1ExampleSolution)
    }

    protected open val data = readInput("Day$day")

    fun part1(): TimedValue<T> = measureTimedValue {
        algorithmPart1(data)
    }

    fun part2Example() {
        check(algorithmPart2(testData).log("Result of the part two test was: ") == part2ExampleSolution)
    }

    fun part2(): TimedValue<T> = measureTimedValue {
        algorithmPart2(data)
    }


    fun runEverything() {
        println(name)
        part1Example()
        part2Example()
        val result1 = part1()
        val result2 = part2()
        println(
            """
            The result of part one is: ${result1.value}. Elapsed time: ${result1.duration}
            The result of part two is: ${result2.value}. Elapsed time: ${result2.duration}
        """.trimIndent()
        )
    }

    fun benchmarkPart1() = averageRuntime {
        part1()
    }.log("Average runtime of part 1 was ")

    fun benchmarkPart2() = averageRuntime {
        part2()
    }.log("Average runtime of part 2 was ")
}