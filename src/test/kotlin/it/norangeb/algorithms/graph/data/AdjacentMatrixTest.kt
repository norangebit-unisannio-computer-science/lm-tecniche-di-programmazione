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

import org.amshove.kluent.`should be equal to`
import org.amshove.kluent.`should contain`
import org.amshove.kluent.`should equal`
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.gherkin.Feature

object AdjacentMatrixTest : Spek({
    Feature("adjacent matrix graph data") {
        val graphData by memoized { AdjacentMatrix(10) }

        Scenario("add edges") {
            When("add edge 1 -> 7") {
                graphData.addEdge(1, 7)
            }

            Then("adjacent of 1 should contains 7") {
                graphData.adjacent(1) `should contain` 7
            }

            Then("adjacent of 7 should be empty") {
                graphData.adjacent(7).isEmpty() `should be equal to` true
            }

            Then("edgeNumber should be equal to 1") {
                graphData.edgeNumber() `should be equal to` 1
            }

            When("add edge 1 -> 8, 7 -> 9, 1 -> 3, 3 -> 1") {
                graphData.addEdge(1, 8)
                graphData.addEdge(7, 9)
                graphData.addEdge(1, 3)
                graphData.addEdge(3, 1)
            }

            Then("adjacent of 1 should equal listOf(3, 7, 8)") {
                graphData.adjacent(1) `should equal` listOf(3, 7, 8)
            }

            Then("adjacent of 7 should equal listOf(9)") {
                graphData.adjacent(7) `should equal` listOf(9)
            }

            Then("adjacent of 3 should equal listOf(1)") {
                graphData.adjacent(3) `should equal` listOf(1)
            }

            Then("edgeNumber should be equal to 5") {
                graphData.edgeNumber() `should be equal to` 5
            }
        }
    }
})