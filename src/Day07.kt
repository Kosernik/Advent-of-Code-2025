fun main() {
    val tachyon = 'S'
    val splitter = '^'
    val empty = '.'

    fun part1(input: List<String>): Int {
        var result = 0

        val width = input[0].length

        var line: MutableList<Char> = MutableList(width) { empty }
        for (i in 0..<width) {
            if (input[0][i] == tachyon) line[i] = tachyon
        }

        for (i in 1..<input.size) {
            val nextLine = MutableList(width) { empty }

            for (j in 0..<width) {
                if (line[j] == tachyon) {
                    if (input[i][j] == splitter) {
                        result++

                        if ((j - 1) >= 0) {
                            nextLine[j - 1] = tachyon
                        }
                        if ((j + 1) < width) {
                            nextLine[j + 1] = tachyon
                        }
                    } else {
                        nextLine[j] = tachyon
                    }
                }
            }

            line = nextLine
        }

        return result
    }

    fun part2(input: List<String>): Long {
        var result = 0L

        val width = input[0].length
        var line: MutableList<Long> = MutableList(width) { 0L }
        for (i in 0..<width) {
            if (input[0][i] == tachyon) line[i] = 1
        }

        for (i in 1..<input.size) {
            val nextLine = MutableList(width) { 0L }

            for (j in 0..<width) {
                if (line[j] > 0) {
                    if (input[i][j] == splitter) {
                        if ((j - 1) >= 0) {
                            nextLine[j - 1] += line[j]
                        }
                        if ((j + 1) < width) {
                            nextLine[j + 1] += line[j]
                        }
                    } else {
                        nextLine[j] += line[j]
                    }
                }
            }

            line = nextLine
        }

        for (count in line) {
            result += count
        }
        return result
    }

    // Read a large test input from the `src/Day07_test.txt` file:
    val testInput = readInput("Day07_test")
    println(part1(testInput) == 21)
    println(part2(testInput) == 40L)

    // Read the input from the `src/Day07.txt` file.
    val input = readInput("Day07")
    part1(input).println()
    part2(input).println()
}
