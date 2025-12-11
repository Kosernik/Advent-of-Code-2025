fun main() {
    fun postorderTraversal(
        start: String,
        finish: String,
        graph: Map<String, Device>,
        computed: MutableMap<String, Long>
    ): Long {
        if (start.equals(finish)) return 1L
        if (computed.containsKey(start)) return computed.get(start)!!

        var paths = 0L

        val node = graph.get(start)!!

        for (neighbour in node.neighbours) {
            val neighbourPaths = postorderTraversal(neighbour.name, finish, graph, computed)

            paths += neighbourPaths
        }

        computed.put(start, paths)
        return computed.get(start)!!
    }

    fun part1(graph: Map<String, Device>): Long {
        val start = "you"
        val finish = "out"

        val computed: MutableMap<String, Long> = mutableMapOf()

        val result = postorderTraversal(start, finish, graph, computed)
        return result
    }

    fun part2(graph: Map<String, Device>): Long {
        val start = "svr"
        val dac = "dac"
        val fft = "fft"
        val finish = "out"

        val startToDac = postorderTraversal(start, dac, graph, mutableMapOf())
        val startToFft = postorderTraversal(start, fft, graph, mutableMapOf())

        val dacToFft = postorderTraversal(dac, fft, graph, mutableMapOf())
        val fftToDac = postorderTraversal(fft, dac, graph, mutableMapOf())

        val dacToFinish = postorderTraversal(dac, finish, graph, mutableMapOf())
        val fftToFinish = postorderTraversal(fft, finish, graph, mutableMapOf())

        return startToDac * dacToFft * fftToFinish + startToFft * fftToDac * dacToFinish
    }

    fun buildGraph(input: List<String>): Map<String, Device> {
        val graph: MutableMap<String, Device> = mutableMapOf()

        for (line in input) {
            var idx = 0
            while (line[idx] != ':') idx++

            val name = line.substring(0, idx)

            if (!graph.containsKey(name)) {
                graph.put(name, Device(name))
            }
            val device: Device = graph.get(name)!!

            val neighbours: List<String> = line.substring(idx + 2).split(" ")

            for (neighbourName in neighbours) {
                if (!graph.containsKey(neighbourName)) {
                    graph.put(neighbourName, Device(neighbourName))
                }
                val neighbour = graph.get(neighbourName)!!

                device.neighbours.add(neighbour)
            }
        }

        return graph
    }

    fun printGraph(graph: Map<String, Device>) {
        for (entry: Map.Entry<String, Device> in graph.entries) {
            print(entry.key + " = " + entry.value.name + " : ")
            for (n in entry.value.neighbours) {
                print(n.name + " ")
            }
            println()
        }
    }

    // Read a large test input from the `src/Day11_test.txt` file:
    val testInput1 = readInput("Day11_test")
    val testGraph1: Map<String, Device> = buildGraph(testInput1)
    println(part1(testGraph1) == 5L)

    val testInput2 = readInput("Day11_test2")
    val testGraph2: Map<String, Device> = buildGraph(testInput2)
    println(part2(testGraph2) == 2L)

    println()
    // Read the input from the `src/Day11.txt` file.
    val input = readInput("Day11")
    val graph = buildGraph(input)
    part1(graph).println()
    part2(graph).println()
}

class Device(val name: String) {
    val neighbours: MutableList<Device> = mutableListOf()
}
