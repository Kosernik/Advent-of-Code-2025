import kotlin.math.max
import kotlin.math.min

fun main() {
    fun part1(coordinates: List<Pair<Int, Int>>): Long {
        var maxArea = 0L

        val sizes: Pair<Int, Int> = coordinates[coordinates.size - 1]
        val height: Int = sizes.first + 1
        val width: Int = sizes.second + 1

        val topHeight: MutableList<Int> = MutableList(width) { Int.MAX_VALUE }
        val leftWidth: MutableList<Int> = MutableList(height) { Int.MAX_VALUE }
        val rightWidth: MutableList<Int> = MutableList(height) { Int.MIN_VALUE }

        for (i in 0..<(coordinates.size - 1)) {
            val coordinate = coordinates[i]
            val h = coordinate.first
            val w = coordinate.second

            topHeight[w] = min(topHeight[w], h)
            leftWidth[h] = min(leftWidth[h], w)
            rightWidth[h] = max(rightWidth[h], w)
        }

        for (r in 0..<height) {
            if (leftWidth[r] == Int.MAX_VALUE || rightWidth[r] == Int.MIN_VALUE) continue
            for (c in 0..<width) {
                if (topHeight[c] == Int.MAX_VALUE) continue

                val curHeight = r - topHeight[c] + 1
                var curWidth = 0L

                if (leftWidth[r] != Int.MAX_VALUE) {
                    curWidth = c - leftWidth[r] + 1L
                }
                if (rightWidth[r] != Int.MIN_VALUE) {
                    curWidth = max(curWidth, rightWidth[r] - c + 1L)
                }

                maxArea = max(maxArea, curWidth * curHeight)
            }
        }

        return maxArea
    }

    fun part2(coordinates: MutableList<Pair<Int, Int>>): Long {
        return -1L
    }


    fun convertCoordinates(input: List<String>): MutableList<Pair<Int, Int>> {
        val coordinates: MutableList<Pair<Int, Int>> = mutableListOf()
        var maxX = Int.MIN_VALUE
        var maxY = Int.MIN_VALUE

        for (line in input) {
            val splited = line.split(",")
            val x = splited[1].toInt()
            val y = splited[0].toInt()

            coordinates.add(Pair(x, y))

            maxX = max(maxX, x)
            maxY = max(maxY, y)

        }

        coordinates.add(Pair(maxX, maxY))

        return coordinates
    }

    // Read a large test input from the `src/Day09_test.txt` file:
    val testInputRaw = readInput("Day09_test")
    val testInput = convertCoordinates(testInputRaw)
    println(part1(testInput) == 50L)
    println(part2(testInput) == 24L)

    println()
    // Read the input from the `src/Day09.txt` file.
    val inputRaw = readInput("Day09")
    val input = convertCoordinates(inputRaw)
    part1(input).println()
    part2(input).println()
}
