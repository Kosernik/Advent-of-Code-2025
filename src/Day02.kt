fun main() {

    fun isRepeatedTwice(candidate: Long): Boolean {
        val str = candidate.toString()
        val length = str.length
        if (length % 2 == 1) return false

        return str.take(length/2) == str.substring(length/2)
    }

    fun part1(input: List<String>): Long {
        var result = 0L

        val splitted: List<String> = input[0].split(",")

        for (range in splitted) {
            val startEnd: List<String> = range.split("-")

            val start: Long = startEnd[0].toLong()
            val end: Long = startEnd[1].toLong()

            for (candidate in start..end) {
                if (isRepeatedTwice(candidate)) {
                    result += candidate
                }
            }
        }

        return result
    }

    fun isRepeated(candidate: Long): Boolean {
        if (isRepeatedTwice(candidate)) return true

        val digits: CharArray = candidate.toString().toCharArray()
        val length = digits.size

        for (subLength in length/2 downTo 1) {
            if (length % subLength != 0) continue
            var valid: Boolean = true

            for (startIdx in 0..<subLength) {
                var nextIdx = startIdx + subLength

                while (nextIdx < length) {
                    if (digits[nextIdx] != digits[startIdx]) {
                        valid = false
                        break
                    }
                    nextIdx += subLength
                }

            }

            if (valid) return true
        }

        return false
    }

    fun part2(input: List<String>): Long {
        var result: Long = 0L

        val splitted: List<String> = input[0].split(",")

        for (range in splitted) {
            val startEnd: List<String> = range.split("-")

            val start: Long = startEnd[0].toLong()
            val end: Long = startEnd[1].toLong()

            for (candidate in start..end) {
                if (isRepeated(candidate)) {
                    result += candidate
                }
            }
        }

        return result
    }


    val testInput = readInput("Day02_test")
    println(part1(testInput) == 1227775554L)
    println(part2(testInput) == 4174379265L)


    val input = readInput("Day02")
    part1(input).println()
    part2(input).println()
}
