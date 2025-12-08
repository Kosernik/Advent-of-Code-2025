fun main() {
    fun find(node: Int, parents: MutableList<Int>): Int {
        if (parents[node] != node) {
            parents[node] = find(parents[node], parents)
        }

        return parents[node]
    }

    fun union(first: Int, second: Int, parents: MutableList<Int>, sizes: MutableList<Int>) {
        val parentFirst = find(first, parents)
        val parentSecond = find(second, parents)

        if (parentFirst == parentSecond) return

        parents[parentFirst] = parentSecond
        sizes[parentSecond] += sizes[parentFirst]
    }

    fun areNodesConnected(first: Int, second: Int, connections: MutableMap<Int, MutableSet<Int>>): Boolean {
        if (connections.containsKey(first) && connections[first]!!.contains(second)) return true
        if (connections.containsKey(second) && connections[second]!!.contains(first)) return true
        return false
    }

    fun part1Slow(input: List<Node>, numberOfConnections: Int): Long {
        val parents: MutableList<Int> = MutableList(input.size) { i -> i }
        val sizes: MutableList<Int> = MutableList(input.size) { 1 }

        //  node -> {neighbours}
        val connections: MutableMap<Int, MutableSet<Int>> = mutableMapOf()

        for (c in 0..<numberOfConnections) {
            var minDist: Long = Long.MAX_VALUE
            var first = 0
            var second = 1

            for (i in 0..<(input.size - 1)) {
                val node = input[i]
                for (j in (i + 1)..<input.size) {
                    val candidate = input[j]

                    val distance = node.computeDistanceSquare(candidate)

                    if (distance < minDist && !areNodesConnected(i, j, connections)) {
                        minDist = distance
                        first = i
                        second = j
                    }
                }
            }

            union(first, second, parents, sizes)

            if (!connections.containsKey(first)) {
                connections[first] = mutableSetOf()
            }
            connections[first]!!.add(second)

            if (!connections.containsKey(second)) {
                connections[second] = mutableSetOf()
            }
            connections[second]!!.add(first)
        }

        var largest = 0
        var medium = 0
        var smol = 0

        for (i in 0..<parents.size) {
            if (parents[i] != i) continue

            val size = sizes[i]
            if (size > largest) {
                smol = medium
                medium = largest
                largest = size
            } else if (size > medium) {
                smol = medium
                medium = size
            } else if (size > smol) {
                smol = size
            }
        }

        return largest.toLong() * medium * smol
    }

    fun part1(input: List<Node>, numberOfConnections: Int): Long {
        val indices: MutableMap<Node, Int> = mutableMapOf()
        for (i in 0..<input.size) {
            indices[input[i]] = i
        }

        val parents: MutableList<Int> = MutableList(input.size) { i -> i }
        val sizes: MutableList<Int> = MutableList(input.size) { 1 }

        val edges: MutableList<Edge> = mutableListOf()
        for (i in 0..<(input.size - 1)) {
            val node = input[i]
            for (j in (i + 1)..<input.size) {
                edges.add(Edge(node, input[j]))
            }
        }
        edges.sort()

        for (e in 0..<numberOfConnections) {
            val edge = edges[e]
            val firstNode = edge.first
            val first = indices[firstNode]!!
            val secondNode = edge.second
            val second = indices[secondNode]!!

            union(first, second, parents, sizes)
        }

        var largest = 0
        var medium = 0
        var smol = 0

        for (i in 0..<parents.size) {
            if (parents[i] != i) continue

            val size = sizes[i]
            if (size > largest) {
                smol = medium
                medium = largest
                largest = size
            } else if (size > medium) {
                smol = medium
                medium = size
            } else if (size > smol) {
                smol = size
            }
        }

        return largest.toLong() * medium * smol
    }

    fun part2Slow(input: List<Node>): Long {
        val parents: MutableList<Int> = MutableList(input.size) { i -> i }
        val sizes: MutableList<Int> = MutableList(input.size) { 1 }

        //  node -> {neighbours}
        val connections: MutableMap<Int, MutableSet<Int>> = mutableMapOf()

        var prevFirst: Int
        var prevSecond: Int

        while (true) {
            var minDist: Long = Long.MAX_VALUE
            var first = 0
            var second = 1

            for (i in 0..<(input.size - 1)) {
                val node = input[i]
                for (j in (i + 1)..<input.size) {
                    val candidate = input[j]

                    val distance = node.computeDistanceSquare(candidate)

                    if (distance < minDist && !areNodesConnected(i, j, connections)) {
                        minDist = distance
                        first = i
                        second = j
                    }
                }
            }

            union(first, second, parents, sizes)

            if (!connections.containsKey(first)) {
                connections[first] = mutableSetOf()
            }
            connections[first]!!.add(second)

            if (!connections.containsKey(second)) {
                connections[second] = mutableSetOf()
            }
            connections[second]!!.add(first)

            prevFirst = first
            prevSecond = second

            val curSize = sizes[find(first, parents)]
            if (curSize == input.size) break
        }

        return input[prevFirst].x.toLong() * input[prevSecond].x
    }

    fun part2(input: List<Node>): Long {
        val indices: MutableMap<Node, Int> = mutableMapOf()
        for (i in 0..<input.size) {
            indices[input[i]] = i
        }

        val parents: MutableList<Int> = MutableList(input.size) { i -> i }
        val sizes: MutableList<Int> = MutableList(input.size) { 1 }

        val edges: MutableList<Edge> = mutableListOf()
        for (i in 0..<(input.size - 1)) {
            val node = input[i]
            for (j in (i + 1)..<input.size) {
                edges.add(Edge(node, input[j]))
            }
        }
        edges.sort()

        var prevFirst: Int = 0
        var prevSecond: Int = 1

        var index = 0

        while (index < edges.size) {
            val edge = edges[index]
            val firstNode = edge.first
            val first = indices[firstNode]!!
            val secondNode = edge.second
            val second = indices[secondNode]!!

            union(first, second, parents, sizes)

            prevFirst = first
            prevSecond = second

            val curSize = sizes[find(first, parents)]
            if (curSize == input.size) break
            index++
        }

        return input[prevFirst].x.toLong() * input[prevSecond].x
    }

    fun getGraph(input: List<String>): List<Node> {
        val result: MutableList<Node> = mutableListOf()

        for (line in input) {
            val coordinates = line.split(",")

            result.add(Node(coordinates[0].toInt(), coordinates[1].toInt(), coordinates[2].toInt()))
        }

        return result
    }

    // Read a large test input from the `src/Day08_test.txt` file:
    val testInput = readInput("Day08_test")
    val testNodes = getGraph(testInput)

    var connections = 10

//    println(part1(testNodes, connections) == part1Slow(testNodes, connections))
    println(part1(testNodes, connections) == 40L)
//    println(part2(testNodes) == part2Slow(testNodes))
    println(part2(testNodes) == 25272L)

    println()
    // Read the input from the `src/Day08.txt` file.
    val input = readInput("Day08")
    val nodes = getGraph(input)

    connections = 1000
    val part1Result = part1(nodes, connections)
//    val part1ResultSlow = part1Slow(nodes, connections)
//    println(part1Result == part1ResultSlow)
    part1Result.println()

    val part2Result =  part2(nodes)
//    val part2ResultSlow =  part2Slow(nodes)
//    println(part2Result == part2ResultSlow)
    part2Result.println()
}

class Node(val x: Int, val y: Int, val z: Int) {
    fun computeDistanceSquare(other: Node): Long {
        val xDistance: Long = x.toLong() - other.x
        val yDistance: Long = y.toLong() - other.y
        val zDistance: Long = z.toLong() - other.z

        return xDistance * xDistance + yDistance * yDistance + zDistance * zDistance
    }
}

class Edge(val first: Node, val second: Node) : Comparable<Edge> {
    val distance: Long = first.computeDistanceSquare(second)

    override fun compareTo(other: Edge): Int {
        return if (distance < other.distance) {
            -1
        } else if (distance > other.distance) {
            1
        } else {
            0
        }
    }
}
