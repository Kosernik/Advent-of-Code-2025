import kotlin.math.max

fun main() {

    fun getBestTwoBatteries(input: String): Int {
        var first: Int = input[input.length - 2] - '0'
        var second: Int = input[input.length - 1] - '0'

        for (i in (input.length - 3) downTo 0) {
            val digit: Int = input[i] - '0'

            if (digit >= first) {
                second = max(first, second)
                first = digit
            }
        }

        return first * 10 + second
    }

    fun part1(input: List<String>): Int {
        var result = 0

        for (digits: String in input) {
            result += getBestTwoBatteries(digits)
        }

        return result
    }


    fun getBestTwelveBatteries(input: String): Long {
        var result: Long = 0
        val length = input.length

        var leftIdx = 0
        for (right in 12 downTo 1) {
            var curMaxDigit: Int = input[leftIdx] - '0'
            var curLeft = leftIdx

            for (i in leftIdx..(length - right)) {
                if ((input[i] - '0') > curMaxDigit) {
                    curMaxDigit = input[i] - '0'
                    curLeft = i
                }
            }

            leftIdx = curLeft + 1

            result = result * 10 + curMaxDigit
        }

        return result
    }

    fun part2(input: List<String>): Long {
        var result: Long = 0L

        for (digits in input) {
            result += getBestTwelveBatteries(digits)
        }

        return result
    }


    val testInput = readInput("Day03_test")
    println(part1(testInput) == 357)
    println(part2(testInput) == 3121910778619)

    val input = readInput("Day03")
    part1(input).println()
    part2(input).println()
}
