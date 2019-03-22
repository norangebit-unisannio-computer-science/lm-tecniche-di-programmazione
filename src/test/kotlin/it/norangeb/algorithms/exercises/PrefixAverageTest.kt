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

package it.norangeb.algorithms.exercises

import org.amshove.kluent.`should equal`
import org.junit.jupiter.api.Test

class PrefixAverageTest {

    @Test
    fun testNominalCase() {
        val arrayIn = intArrayOf(21, 23, 25, 31, 20, 18, 16)

        val arrayOut = PrefixAverage.prefixAverage(arrayIn)

        arrayOut `should equal` intArrayOf(
            21, 22, 23, 25, 24, 23, 22
        )
    }

    @Test
    fun testEmptyArray() {
        val arrayIn = intArrayOf()

        val arrayOut = PrefixAverage.prefixAverage(arrayIn)

        arrayOut `should equal` intArrayOf()
    }

    @Test
    fun testOneElementArray() {
        val arrayIn = intArrayOf(25)

        val arrayOut = PrefixAverage.prefixAverage(arrayIn)

        arrayOut `should equal` intArrayOf(25)
    }

    @Test
    fun testAlwaysSameElementArray() {
        val arrayIn = intArrayOf(21, 21, 21, 21, 21)

        val arrayOut = PrefixAverage.prefixAverage(arrayIn)

        arrayOut `should equal` intArrayOf(21, 21, 21, 21, 21)
    }

    @Test
    fun testNominalCaseRecursive() {
        val arrayIn = intArrayOf(21, 23, 25, 31, 20, 18, 16)

        val arrayOut = PrefixAverage.recursivePrefixAverage(arrayIn)

        arrayOut `should equal` intArrayOf(
            21, 22, 23, 25, 24, 23, 22
        )
    }

    @Test
    fun testEmptyArrayRecursive() {
        val arrayIn = intArrayOf()

        val arrayOut = PrefixAverage.recursivePrefixAverage(arrayIn)

        arrayOut `should equal` intArrayOf()
    }

    @Test
    fun testOneElementArrayRecursive() {
        val arrayIn = intArrayOf(25)

        val arrayOut = PrefixAverage.recursivePrefixAverage(arrayIn)

        arrayOut `should equal` intArrayOf(25)
    }

    @Test
    fun testAlwaysSameElementArrayRecursive() {
        val arrayIn = intArrayOf(21, 21, 21, 21, 21)

        val arrayOut = PrefixAverage.recursivePrefixAverage(arrayIn)

        arrayOut `should equal` intArrayOf(21, 21, 21, 21, 21)
    }
}