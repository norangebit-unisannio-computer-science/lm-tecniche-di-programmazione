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
import org.junit.Before
import org.junit.Test

class OrderedListSorterTest {

    lateinit var lists: List<List<Int>>

    @Before
    fun init() {
        lists = listOf(
            listOf(1, 5, 9, 10, 12, 14, 15, 15, 20, 25, 29, 31, 35, 37, 43, 48),
            listOf(2, 7, 12, 21, 23, 33, 35, 43, 45),
            listOf(3, 7, 9, 12, 14, 16, 27, 28, 37, 39, 45, 45, 46, 50),
            listOf(1, 8, 9, 11, 13, 21, 25, 33, 34, 41, 47, 48)
        )
    }

    @Test
    fun testSortUnsignedInt() {
        val out = OrderedListSorter.sortUnsignedInt(*lists.toTypedArray())

        out `should equal` listOf(1, 1, 2, 3, 5, 7, 7, 8, 9, 9, 9, 10, 11, 12,
            12, 12, 13, 14, 14, 15, 15, 16, 20, 21, 21, 23, 25, 25, 27, 28, 29,
            31, 33, 33, 34, 35, 35, 37, 37, 39, 41, 43, 43, 45, 45, 45, 46, 47,
            48, 48, 50)
    }

    @Test
    fun testSortInt() {
        val out = OrderedListSorter.sort(*lists.toTypedArray())

        out `should equal` listOf(1, 1, 2, 3, 5, 7, 7, 8, 9, 9, 9, 10, 11, 12,
            12, 12, 13, 14, 14, 15, 15, 16, 20, 21, 21, 23, 25, 25, 27, 28, 29,
            31, 33, 33, 34, 35, 35, 37, 37, 39, 41, 43, 43, 45, 45, 45, 46, 47,
            48, 48, 50)
    }

    @Test
    fun testSortStrings() {
        val out = OrderedListSorter.sort(
            listOf("AAA", "BCE", "BCZ", "DCC", "DDD", "EEE"),
            listOf("AAB", "AAC", "BBB", "DZZ", "EAA", "EAB"),
            listOf("CCC", "CCZ", "DAA", "DDD")
        )

        out `should equal` listOf("AAA", "AAB", "AAC", "BBB", "BCE", "BCZ", "CCC", "CCZ", "DAA",
            "DCC", "DDD", "DDD", "DZZ", "EAA", "EAB", "EEE")
    }
}