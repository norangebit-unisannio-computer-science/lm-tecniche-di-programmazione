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

package it.norangeb.algorithms.graph

import it.norangeb.algorithms.graph.data.AdjacentList
import it.norangeb.algorithms.graph.data.GraphData

class DiGraph(
    private val data: GraphData = AdjacentList()
) : DirectedGraph {
    override fun vertexNumber(): Int = data.vertexNumber()

    override fun edgeNumber(): Int = data.edgeNumber()

    override fun addEdge(firstVertex: Int, secondVertex: Int) = data.addEdge(firstVertex, secondVertex)

    override fun adjacentVertex(vertex: Int): Collection<Int> = data.adjacent(vertex)

    override fun reverse(): DirectedGraph {
        val reverse = DiGraph()

        (0 until data.vertexNumber())
            .forEach { from ->
                data.adjacent(from).forEach { to ->
                    reverse.addEdge(to, from)
                }
            }

        return reverse
    }
}