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

import it.norangeb.algorithms.graph.DiGraph
import org.amshove.kluent.`should be equal to`
import org.amshove.kluent.`should equal`
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.gherkin.Feature

object DirectedCycleTest : Spek({
    Feature("directed cycle") {
        Scenario("try to find a cycle into DAG") {
            val dag by memoized { DiGraph() }

            Given("a directed acyclic graph") {
                dag.addEdge(0, 1)
                dag.addEdge(0, 6)
                dag.addEdge(1, 2)
                dag.addEdge(2, 3)
                dag.addEdge(3, 4)
                dag.addEdge(5, 4)
                dag.addEdge(5, 6)
            }

            lateinit var directedCycleResult: DirectedCycle

            When("try to find a cycle") {
                directedCycleResult = DirectedCycle(dag)
            }

            Then("has cycle should be false") {
                directedCycleResult.hasCycle() `should be equal to` false
            }

            Then("cycle should be null") {
                directedCycleResult.cycle `should equal` null
            }
        }

        Scenario("try to find a cycle into cyclic graph") {
            val cyclicGraph by memoized { DiGraph() }

            Given("a cyclic graph") {
                cyclicGraph.addEdge(0, 1)
                cyclicGraph.addEdge(0, 6)
                cyclicGraph.addEdge(1, 2)
                cyclicGraph.addEdge(2, 3)
                cyclicGraph.addEdge(3, 4)
                cyclicGraph.addEdge(3, 5)
                cyclicGraph.addEdge(5, 0)
                cyclicGraph.addEdge(5, 4)
                cyclicGraph.addEdge(5, 6)
            }

            lateinit var directedCycleResult: DirectedCycle

            When("try to find a cycle") {
                directedCycleResult = DirectedCycle(cyclicGraph)
            }

            Then("hasCycle should be true") {
                directedCycleResult.hasCycle() `should be equal to` true
            }

            Then("cycle should equal listOf(5, 3, 2, 1, 0, 5)") {
                directedCycleResult.cycle `should equal` listOf(5, 3, 2, 1, 0, 5)
            }
        }

        Scenario("try to find a cycle into cyclic graph with cycle not on root") {
            val cyclicGraph by memoized { DiGraph() }

            Given("a cyclic graph") {
                cyclicGraph.addEdge(0, 1)
                cyclicGraph.addEdge(0, 3)
                cyclicGraph.addEdge(1, 3)
                cyclicGraph.addEdge(4, 0)
                cyclicGraph.addEdge(4, 2)
                cyclicGraph.addEdge(4, 5)
                cyclicGraph.addEdge(4, 6)
                cyclicGraph.addEdge(5, 7)
                cyclicGraph.addEdge(7, 4)
            }

            lateinit var directedCycleResult: DirectedCycle

            When("try to find a cycle") {
                directedCycleResult = DirectedCycle(cyclicGraph)
            }

            Then("hasCycle should be true") {
                directedCycleResult.hasCycle() `should be equal to` true
            }

            Then("cycle should equal listOf(7, 5, 4, 7)") {
                directedCycleResult.cycle `should equal` listOf(7, 5, 4, 7)
            }
        }
    }
})