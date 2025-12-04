import javax.swing.Spring.height

fun main() {
    val roll: Char = '@'
    val empty: Char = '.'

    val verticalHorizontal: IntArray = intArrayOf(0, 1, 0, -1, 0)
    val diagonal: IntArray = intArrayOf(1, 1, -1, -1, 1)


    fun isCellValid(row: Int, column: Int, matrix: List<List<Char>>): Boolean {
        return row >= 0 && column >= 0 && row < matrix.size && column < matrix[0].size && matrix[row][column] == roll
    }

    fun countNeighboringRolls(row: Int, column: Int, matrix: List<List<Char>>): Int {
        var count = 0

        for (i in 0..<(verticalHorizontal.size - 1)) {
            val nextRow = row + verticalHorizontal[i]
            val nextCol = column + verticalHorizontal[i + 1]

            if (isCellValid(nextRow, nextCol, matrix)) {
                count++
            }
        }

        for (i in 0..<(diagonal.size - 1)) {
            val nextRow = row + diagonal[i]
            val nextCol = column + diagonal[i + 1]

            if (isCellValid(nextRow, nextCol, matrix)) {
                count++
            }
        }

        return count
    }


    fun part1(matrix: List<List<Char>>): Int {
        val height = matrix.size
        val width = matrix[0].size

        var result = 0

        for (row in 0..<height) {
            for (col in 0..<width) {
                if (matrix[row][col] != empty && countNeighboringRolls(row, col, matrix) < 4) {
                    result++
                }
            }
        }

        return result
    }

    fun part2(matrix: List<List<Char>>): Int {
        val height = matrix.size
        val width = matrix[0].size

        var result = 0
        val updated: List<MutableList<Char>> = List(height) { mutableListOf<Char>() }

        for (row in 0..<height) {
            for (col in 0..<width) {
                updated[row].add(matrix[row][col])

                if (matrix[row][col] != empty && countNeighboringRolls(row, col, matrix) < 4) {
                    result++

                    updated[row][col] = empty
                }
            }
        }

        if (result == 0) return 0
        return result + part2(updated)
    }

    fun convertToMatrix(input: List<String>): List<List<Char>> {
        val height = input.size
        val width = input[0].length

        val result: List<MutableList<Char>> = List(height) { mutableListOf<Char>() }

        for (i in 0..<height) {
            for (j in 0..<width) {
                result[i].add(input[i][j])
            }
        }

        return result
    }


    val testInput: List<String> = readInput("Day04_test")
    val matrixInput: List<List<Char>> = convertToMatrix(testInput)
    println(part1(matrixInput) == 13)
    println(part2(matrixInput) == 43)

    val input: List<String> = readInput("Day04")
    val matrix: List<List<Char>> = convertToMatrix(input)
    part1(matrix).println()
    part2(matrix).println()
}
