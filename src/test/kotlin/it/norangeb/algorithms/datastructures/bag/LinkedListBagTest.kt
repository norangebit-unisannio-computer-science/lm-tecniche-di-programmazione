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

package it.norangeb.algorithms.datastructures.bag

import org.amshove.kluent.`should be equal to`
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class LinkedListBagTest {
    private lateinit var bag: LinkedListBag<Int>

    @BeforeEach
    fun init() {
        bag = LinkedListBag()
    }

    @Test
    fun testAdd() {
        bag.size() `should be equal to` 0
        bag.add(1)
        bag.size() `should be equal to` 1
        bag.add(1)
        bag.size() `should be equal to` 2
        bag.add(1)
        bag.size() `should be equal to` 3
    }

    @Test
    fun testIsEmpty() {
        bag.isEmpty() `should be equal to` true
        bag.add(1)
        bag.isEmpty() `should be equal to` false
    }

    @Test
    fun testMap() {
        bag.add(1)
        bag.add(2)
        bag.add(3)
        bag.add(4)
        bag.add(5)

        bag.map { it * 2 }.size() `should be equal to` 5
    }

    @Test
    fun testForEach() {
        bag.add(1)
        bag.add(2)
        bag.add(3)
        bag.add(4)
        bag.add(5)

        var x = 0

        bag.forEach { x++ }

        x `should be equal to` 5
    }

    @Test
    fun testClear() {
        bag.add(1)
        bag.add(2)
        bag.add(3)
        bag.add(4)
        bag.add(5)
        bag.clear()

        bag.size() `should be equal to` 0
    }
}