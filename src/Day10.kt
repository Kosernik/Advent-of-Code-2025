import kotlin.math.min

fun main() {
    val OFF = '.'
    val ON = '#'

    fun parseInput(input: String, parseJoltage: Boolean): List<List<Int>> {
        val parsed: MutableList<MutableList<Int>> = mutableListOf()

        val splited = input.split(" ")

        val length: Int = splited.size + if (parseJoltage) 0 else -1
        for (i in 1..<length) {
            val curScheme = mutableListOf<Int>()
            val entry: List<String> = splited[i].substring(1, splited[i].length - 1).split(",")

            for (e in entry) {
                curScheme.add(e.toInt())
            }

            parsed.add(curScheme)
        }

        return parsed
    }

    fun parsePattern(input: String): String {
        var endIdx = 0

        while (input[endIdx] != ']') endIdx++

        return input.substring(1, endIdx)
    }

    fun countSwitchesSchema(input: String): Int {
        var result = Int.MAX_VALUE

        val targetPattern: String = parsePattern(input)
        val parsedCommands: List<List<Int>> = parseInput(input, false)

        val numberOfCombinations: Int = 1 shl parsedCommands.size

        for (mask in 0..<numberOfCombinations) {
            val curOutput: MutableList<Char> = MutableList(targetPattern.length) { OFF }

            var shiftedMask = mask
            for (i in 0..<parsedCommands.size) {
                if ((shiftedMask and 1) == 1) {
                    for (idx in parsedCommands[i]) {
                        if (curOutput[idx] == OFF) {
                            curOutput[idx] = ON
                        } else {
                            curOutput[idx] = OFF
                        }
                    }
                }

                shiftedMask = shiftedMask shr 1
            }

            var output = ""
            for (ch in curOutput) output += ch

            if (targetPattern.equals(output)) {
                result = min(result, mask.countOneBits())
            }
        }

        return result
    }

    fun part1(input: List<String>): Long {
        var result = 0L

        for (line in input) {
            result += countSwitchesSchema(line)
        }

        return result
    }

    fun part2(input: List<String>): Long {
        return -1L
    }

    // Read a large test input from the `src/Day10_test.txt` file:
    val testInput = readInput("Day10_test")
    println(part1(testInput) == 7L)
    println(part2(testInput) == 33L)

    println()
    // Read the input from the `src/Day10.txt` file.
    val input = readInput("Day10")
    part1(input).println()
    part2(input).println()
}
