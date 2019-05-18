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

import org.amshove.kluent.`should be equal to`
import org.amshove.kluent.`should equal`
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.gherkin.Feature

object DiGraphTest : Spek({
    Feature("directed graph") {
        val graph by memoized { DiGraph() }

        Scenario("add edges") {
            When("add edge 1 -> 4") {
                graph.addEdge(1, 4)
            }

            Then("number of edge should equal to 1") {
                graph.edgeNumber() `should be equal to` 1
            }

            Then("adjacent of 1 should equal listOf(4)") {
                graph.adjacentVertex(1) `should equal` listOf(4)
            }

            Then("adjacent of 4 should be empty") {
                graph.adjacentVertex(4).isEmpty() `should be equal to` true
            }

            When("add edge 1 -> 3, 3 -> 4, 1 -> 5") {
                graph.addEdge(1, 3)
                graph.addEdge(3, 4)
                graph.addEdge(1, 5)
            }

            Then("number of edge should equal to 4") {
                graph.edgeNumber() `should be equal to` 4
            }

            Then("adjacent of 1 should equal listOf(3, 4, 5)") {
                graph.adjacentVertex(1).sorted() `should equal` listOf(3, 4, 5)
            }

            Then("adjacent of 3 should equal listOf(4)") {
                graph.adjacentVertex(3) `should equal` listOf(4)
            }
        }

        Scenario("reverse test") {
            val diGraph by memoized { DiGraph() }

            Given("directed graph 0 -> 2, 0 -> 4, 1 -> 0, 1 -> 2, 4 -> 2") {
                diGraph.addEdge(0, 2)
                diGraph.addEdge(0, 4)
                diGraph.addEdge(1, 0)
                diGraph.addEdge(1, 2)
                diGraph.addEdge(4, 2)
            }

            lateinit var result: DirectedGraph

            When("compute the reversed graph") {
                result = diGraph.reverse()
            }

            Then("adjacent of 0 should euqals listOf(1)") {
                result.adjacentVertex(0) `should equal` listOf(1)
            }

            Then("adjacent of 1 should be empty") {
                result.adjacentVertex(1).isEmpty() `should be equal to` true
            }

            Then("adjacent of 2 should equal listOf(0, 1, 4)") {
                result.adjacentVertex(2).sorted() `should equal` listOf(0, 1, 4)
            }
        }
    }
})