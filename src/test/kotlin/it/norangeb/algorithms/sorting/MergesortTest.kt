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

package it.norangeb.algorithms.sorting

import org.amshove.kluent.`should equal`
import org.junit.jupiter.api.Test
import kotlin.reflect.full.memberFunctions
import kotlin.reflect.jvm.isAccessible

class MergesortTest {

    @Test
    fun testMerge() {
        val merge = Mergesort::class.memberFunctions
            .find { it.name == "merge" }
            .also { it?.isAccessible = true }

        val array = arrayOf(3, 7, 9, 12, 1, 4, 6, 10)
        val low = 0
        val mid = 3
        val high = array.size - 1
        val auxiliary = arrayOfNulls<Int>(array.size)

        merge?.call(
            Mergesort,
            array,
            auxiliary,
            low,
            mid,
            high,
            { int1: Int, int2: Int ->
                int1 < int2
            }
        )

        array `should equal` array.sortedArray()
    }

    @Test
    fun testSort() {
        val array = arrayOf(5, 2, 3, 1, 4)

        Mergesort.sort(array)

        array `should equal` array.sortedArray()
    }

    @Test
    fun testSortBy() {
        val array = arrayOf(5, 2, 1, 4, 3)

        Mergesort.sortBy(array) { it }

        array `should equal` array.sortedArray()
    }

    @Test
    fun testSortWith() {
        val array = arrayOf(4, 5, 3, 2, 1)

        Mergesort.sortWith(array) { t1, t2 ->
            t1.compareTo(t2)
        }

        array `should equal` array.sortedArray()
    }
}