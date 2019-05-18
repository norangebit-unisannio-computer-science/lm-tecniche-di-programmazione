/*
 * MIT License
 *
 * Copyright (c) 2019 norangebit
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 */

package it.norangeb.algorithms.graph.operations

import it.norangeb.algorithms.graph.DirectedGraph
import it.norangeb.algorithms.graph.UndirectedGraph
import java.util.Stack

class DirectedCycle(graph: DirectedGraph) {
    var cycle: MutableCollection<Int>? = null
    private val graphInfo = Array(graph.vertexNumber()) { VertexInfo() }

    init {
        (0 until graph.vertexNumber())
            .forEach {
                if (!graphInfo[it].visited)
                    dfs(graph, it)
            }
    }

    fun hasCycle(): Boolean = cycle != null

    private fun dfs(graph: UndirectedGraph, vertex: Int) {
        graphInfo[vertex].visited = true
        graphInfo[vertex].onStack = true

        graph.adjacentVertex(vertex)
            .forEach {
                when {
                    hasCycle() -> return@forEach
                    !graphInfo[it].visited -> exploreChild(graph, vertex, it)
                    graphInfo[it].onStack -> cycle = makeCycle(vertex, it)
                }
            }

        graphInfo[vertex].onStack = false
    }

    private fun makeCycle(start: Int, end: Int): MutableCollection<Int> {
        val cycle = Stack<Int>()

        var currentVertex = start

        while (currentVertex != end) {
            cycle.add(currentVertex)
            currentVertex = graphInfo[currentVertex].previously
        }

        cycle.add(end)
        cycle.add(start)

        return cycle
    }

    private fun exploreChild(graph: UndirectedGraph, parent: Int, child: Int) {
        graphInfo[child].previously = parent
        dfs(graph, child)
    }

    data class VertexInfo(
        var visited: Boolean = false,
        var onStack: Boolean = false,
        var previously: Int = -1
    )
}