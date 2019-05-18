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

package it.norangeb.algorithms.graph.data

import java.util.TreeMap
import kotlin.math.max

class AdjacentList : GraphData {
    private val graph = TreeMap<Int, MutableList<Int>>()
    private var edgeNumber = 0
    private var maxVertexId = 0

    override fun addEdge(firstVertex: Int, secondVertex: Int) {
        graph.computeIfAbsent(firstVertex) { mutableListOf() }

        graph[firstVertex]?.add(secondVertex)

        computeMaxVertexId(firstVertex, secondVertex)
        edgeNumber++
    }

    override fun adjacent(vertex: Int): Collection<Int> {
        return graph[vertex] ?: listOf()
    }

    override fun vertexNumber(): Int = maxVertexId + 1

    override fun edgeNumber(): Int = edgeNumber

    private fun computeMaxVertexId(firstVertex: Int, secondVertex: Int) {
        val max = max(firstVertex, secondVertex)
        maxVertexId = max(max, maxVertexId)
    }
}