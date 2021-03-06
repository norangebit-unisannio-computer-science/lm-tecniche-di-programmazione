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

import arrow.core.None
import arrow.core.Some
import org.amshove.kluent.shouldEqual
import org.junit.jupiter.api.Test

class KMinTest {

    @Test
    fun test() {
        val input = listOf(15, 17, 9, 12, 22, 4, 73, 87, 12, 5)

        val kmin = KMin<Int>(3)
        val result = input.map { kmin.insert(it) }

        result shouldEqual listOf(
            None,
            None,
            Some(17),
            Some(15),
            Some(15),
            Some(12),
            Some(12),
            Some(12),
            Some(12),
            Some(9)
        )
    }

    @Test
    fun test5() {
        val input = listOf(15, 17, 9, 12, 22, 4, 73, 87, 12, 5)

        val kmin = KMin<Int>(5)
        val result = input.map { kmin.insert(it) }

        result shouldEqual listOf(
            None,
            None,
            None,
            None,
            Some(22),
            Some(17),
            Some(17),
            Some(17),
            Some(15),
            Some(12)
        )
    }
}