import classes.Day

/**
 * With 100 runs:
 *
 * Average runtime of part 1 was around `1 millisecond`
 *
 * Average runtime of part 2 was around `700 microseconds`
 */
object Day03 : Day<Long>() {
    override val testData: List<String> = """
        987654321111111
        811111111111119
        234234234234278
        818181911112111
    """.trimIndent().split("\n")

    override val part1ExampleSolution: Long = 357L
    override val part2ExampleSolution: Long = 3_121_910_778_619L

    override fun algorithmPart1(input: List<String>): Long = input.sumOf {
        batteryBankToMaxJoltage(it, 2)
    }

    override fun algorithmPart2(input: List<String>): Long = input.sumOf {
        batteryBankToMaxJoltage(it, 12)
    }

    fun batteryBankToMaxJoltage(batteryBank: String, batteryCount: Int): Long {
        var voltage = 0L
        var leftIndex = 0

        for (i in batteryCount downTo 1) {
            val largestBatteryInWindow =
                batteryBank.slice(leftIndex..batteryBank.length - i).map { it.digitToInt().toLong() }
                    .findMaxIndexed()

            leftIndex += largestBatteryInWindow.index + 1
            voltage = voltage concat largestBatteryInWindow.value
        }

        return voltage
    }


    fun oldPart1Algorithm(input: List<String>): Long =
        input.map { string ->
            string.map { it.digitToInt() }
        }.sumOf {
            val largestBattery = it.findMaxIndexed()
            if (largestBattery.index == it.lastIndex)
                it.slice(0..<it.lastIndex).max() concat largestBattery.value
            else
                largestBattery.value concat it.slice(largestBattery.index + 1..it.lastIndex).max()
        }.toLong()


}

fun main() {
    Day03.runEverything()
}