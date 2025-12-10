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


    fun changeJoltage(joltage: MutableList<Int>, indices: List<Int>, add: Int) {
        for (i in indices) {
            joltage[i] += add
        }
    }

    fun backTrack(
        currentJoltage: MutableList<Int>,
        steps: Int,
        parsedCommands: List<List<Int>>,
        computed: MutableMap<String, Int>
    ): Int {
        val stringJoltage = currentJoltage.toString()

        if (computed.contains(stringJoltage)) {
            return computed[stringJoltage]!!
        }

        var isValid: Boolean = true

        for (i in 0..<currentJoltage.size) {
            if (currentJoltage[i] < 0) {
                return Int.MAX_VALUE
            } else if (currentJoltage[i] > 0) {
                isValid = false
            }
        }

        if (isValid) {
            return 0
        }

        var bestRes = Int.MAX_VALUE

        for (i in 0..<(parsedCommands.size - 1)) {
            val curIndices = parsedCommands[i]

            changeJoltage(currentJoltage, curIndices, -1)

            val candidateResult = backTrack(currentJoltage, steps + 1, parsedCommands, computed)
            if (candidateResult != Int.MAX_VALUE) {
                if (candidateResult < 0) {
                    println("Found negative!!")
                    println("$stringJoltage, stepps: $steps, candidate result is: $candidateResult")
                }
                bestRes = min(bestRes, candidateResult)
            }

            changeJoltage(currentJoltage, curIndices, 1)
        }

        if (bestRes == Int.MAX_VALUE) {
            bestRes -= 1
        }
        computed[stringJoltage] = 1 + bestRes
        return computed[stringJoltage]!!
    }


    fun countSwitchesJoltage(input: String): Int {
        val parsedCommands: List<List<Int>> = parseInput(input, true)

        val targetJoltage: List<Int> = parsedCommands[parsedCommands.size - 1]
        val targetJoltageMutable: MutableList<Int> = mutableListOf()

        for (i in 0..<targetJoltage.size) {
            targetJoltageMutable.add(targetJoltage[i])
        }

        val computed: MutableMap<String, Int> = mutableMapOf()

        val result = backTrack(targetJoltageMutable, 0, parsedCommands, computed)

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
        var result = 0L

        for (line in input) {
            val curRes = countSwitchesJoltage(line)
            result += curRes
        }

        return result
    }

    // Read a large test input from the `src/Day10_test.txt` file:
    val testInput = readInput("Day10_test")
    println(part1(testInput) == 7L)
    println(part2(testInput) == 33L)

    println()
    // Read the input from the `src/Day10.txt` file.
    val input = readInput("Day10")
    part1(input).println()  //461
//    part2(input).println()
}
