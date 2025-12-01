import jdk.internal.org.jline.utils.Colors.s

fun main() {
    fun part1(input: List<String>): Int {
        val totalNumbers = 100

        var number = 50
        var countZeroes = 0

        for (rotation: String in input) {
            number = if (rotation[0] == 'L') {
                (number + totalNumbers - rotation.substring(1).toInt()) % totalNumbers
            } else { // rotation[0] == 'R'
                (number + rotation.substring(1).toInt()) % totalNumbers
            }

            if (number == 0) countZeroes++
        }

        return countZeroes
    }

    fun part2(input: List<String>): Int {
        val totalNumbers = 100

        var number = 50
        var countZeroes = 0

        for (rotation: String in input) {
            var curRotation = rotation.substring(1).toInt()

            if (curRotation == 0) {
                if (number == 0) countZeroes++;
                continue
            }

            countZeroes += curRotation / totalNumbers

            curRotation %= totalNumbers

            val shiftedNumber: Int
            if (rotation[0] == 'L') {
                shiftedNumber = number - curRotation

                if (shiftedNumber <= 0 && number != 0) {
                    countZeroes++
                }
            } else { // rotation[0] == 'R'
                shiftedNumber = number + curRotation

                if (shiftedNumber >= totalNumbers) {
                    countZeroes++
                }
            }

            number = (shiftedNumber + totalNumbers) % totalNumbers
        }

        return countZeroes
    }


    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day01_test")
    println(part1(testInput) == 3)
    println(part2(testInput) == 6)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}
