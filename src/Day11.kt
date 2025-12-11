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

    fun part2(input: List<String>): Long {
        return -1
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
    val testInput = readInput("Day11_test")
    val testGraph: Map<String, Device> = buildGraph(testInput)
    println(part1(testGraph) == 5L)
//    println(part2(testInput) == -1L)

    println()
    // Read the input from the `src/Day11.txt` file.
    val input = readInput("Day11")
    val graph = buildGraph(input)
    part1(graph).println()
//    part2(input).println()
}

class Device(val name: String) {
    val neighbours: MutableList<Device> = mutableListOf()
}
