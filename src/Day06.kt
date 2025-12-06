fun main() {
    fun convertToNumbers(input: List<String>): List<List<Long>> {
        val converted: List<MutableList<Long>> = List(input.size - 1) { mutableListOf() }

        for (i in 0..<(input.size - 1)) {
            val line: List<String> = input[i].trim().split("\\s+".toRegex())

            for (number in line) {
                converted[i].add(number.toLong())
            }
        }

        return converted
    }

    fun convertOperation(input: List<String>): List<Char> {
        val result: MutableList<Char> = mutableListOf()

        val line: List<String> = input[input.size - 1].trim().split("\\s+".toRegex())

        for (operation in line) {
            result.add(operation[0])
        }

        return result
    }

    fun part1(input: List<String>): Long {
        var result: Long = 0L

        val convertedInputNumbers = convertToNumbers(input)
        val operations = convertOperation(input)

        val height = input.size - 1
        val width = convertedInputNumbers[0].size

        for (i in 0..<width) {
            var curRes = 0L

            if (operations[i] == '+') {
                for (j in 0..<height) {
                    curRes += convertedInputNumbers[j][i]
                }
            } else {    //  operations[i] == '*'
                curRes = 1L
                for (j in 0..<height) {
                    curRes *= convertedInputNumbers[j][i]
                }
            }

            result += curRes
        }

        return result
    }

    fun part2(input: List<String>): Long {
        var result = 0L

        val height = input.size - 1
        var horizontalIndex = 0

        while (true) {
            if (horizontalIndex >= input[height].length) break

            val operation = input[height][horizontalIndex]

            var curResult = if (operation == '+') 0L else 1L

            var hasNumber = true

            while (hasNumber) {
                hasNumber = false

                var curNumber = 0

                for (i in 0..height) {
                    if (horizontalIndex < input[i].length && input[i][horizontalIndex].isDigit()) {
                        curNumber = curNumber * 10 + (input[i][horizontalIndex] - '0')
                        hasNumber = true
                    }
                }

                if (hasNumber) {
                    if (operation == '+') {
                        curResult += curNumber
                    } else {
                        curResult *= curNumber
                    }
                    horizontalIndex++
                }
            }

            horizontalIndex++
            result += curResult
        }

        return result
    }

    fun part2Ugly(input: List<String>): Long {
        var result = 0L

        val height = input.size - 1
        var horizontalIndex = 0

        while (true) {
            if (horizontalIndex >= input[height].length) break

            val operation = input[height][horizontalIndex]

            var curResult = 0L
            var hasNumber = true

            if (operation == '+') {
                while (hasNumber) {
                    hasNumber = false

                    var curNumber = 0

                    for (i in 0..height) {
                        if (horizontalIndex < input[i].length && input[i][horizontalIndex].isDigit()) {
                            curNumber = curNumber * 10 + (input[i][horizontalIndex] - '0')
                            hasNumber = true
                        }
                    }

                    if (hasNumber) {
                        curResult += curNumber
                        horizontalIndex++
                    }
                }
            } else {
                curResult = 1L

                while (hasNumber) {
                    hasNumber = false

                    var curNumber = 0

                    for (i in 0..height) {
                        if (horizontalIndex < input[i].length && input[i][horizontalIndex].isDigit()) {
                            curNumber = curNumber * 10 + (input[i][horizontalIndex] - '0')
                            hasNumber = true
                        }
                    }

                    if (hasNumber) {
                        curResult *= curNumber
                        horizontalIndex++
                    }
                }
            }

            horizontalIndex++
            result += curResult
        }

        return result
    }


    val testInput = readInput("Day06_test")
    println(part1(testInput) == 4277556L)

    println(part2(testInput) == part2Ugly(testInput))
    println(part2(testInput) == 3263827L)


    println()
    val input = readInput("Day06")
    part1(input).println()

    println(part2(input) == part2Ugly(input))
    part2(input).println()
}
