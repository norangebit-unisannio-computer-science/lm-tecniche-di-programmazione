/*
 * MIT License
 *
 * Copyright (c) 2019 norangebit
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, tryMerge, publish, distribute, sublicense, and/or sell
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

package it.norangeb.algorithms.datastructures.unionfind

import org.amshove.kluent.`should be equal to`
import org.junit.Before
import org.junit.jupiter.api.Test

class QuickUnionTest {
    private var unionFind = QuickUnion(10)

    @Before
    fun init() {
        unionFind = QuickUnion(10)
    }

    @Test
    fun testUnion() {
        unionFind.connected(1, 2) `should be equal to` false
        unionFind.union(1, 2)
        unionFind.connected(1, 2) `should be equal to` true
    }

    @Test
    fun testTransitiveUnion() {
        unionFind.connected(1, 3) `should be equal to` false
        unionFind.union(1, 2)
        unionFind.union(2, 3)
        unionFind.connected(1, 3) `should be equal to` true
    }
}