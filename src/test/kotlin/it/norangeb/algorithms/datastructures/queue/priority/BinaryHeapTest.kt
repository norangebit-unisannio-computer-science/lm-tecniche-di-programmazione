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

package it.norangeb.algorithms.datastructures.queue.priority

import arrow.core.None
import org.amshove.kluent.`should be equal to`
import org.amshove.kluent.`should equal`
import org.junit.jupiter.api.Test
import kotlin.reflect.full.declaredMemberFunctions
import kotlin.reflect.jvm.isAccessible

class BinaryHeapTest {

    @Test
    fun testExchange() {
        val exchange = BinaryHeap::class.declaredMemberFunctions
            .find { it.name == "exchange" }
            .also { it?.isAccessible = true }

        val binaryHeap = BinaryHeap.createMaxPriorityQueue<Int> { _, _ -> -1 }
        val array = arrayOf(1, 2)

        exchange?.call(binaryHeap, array, 0, 1)

        array `should equal` arrayOf(2, 1)
    }

    @Test
    fun testPushUp() {
        val pushUp = BinaryHeap::class.declaredMemberFunctions
            .find { it.name == "pushUp" }
            .also { it?.isAccessible = true }

        val binaryHeap = BinaryHeap.createMaxPriorityQueue<Int> { t1, t2 ->
            t1.compareTo(t2)
        }
        val array = arrayOf(Int.MAX_VALUE, 23, 5, 12, 3, 7)

        pushUp?.call(binaryHeap, array, 5)

        array `should equal` arrayOf(Int.MAX_VALUE, 23, 7, 12, 3, 5)
    }

    @Test
    fun testComparableMax() {
        val heap = BinaryHeap.createMaxPriorityQueue<Int>()
        heap.insert(25)
        heap.insert(5)
        heap.insert(15)
        heap.insert(12)

        heap.pop().map { it `should be equal to` 25 }
        heap.pop().map { it `should be equal to` 15 }
        heap.size() `should be equal to` 2

        heap.insert(1)
        heap.insert(13)

        heap.peek().map { it `should be equal to` 13 }
        heap.pop().map { it `should be equal to` 13 }
        heap.pop().map { it `should be equal to` 12 }
        heap.pop().map { it `should be equal to` 5 }
        heap.pop().map { it `should be equal to` 1 }

        (heap.pop() is None) `should be equal to` true
    }

    @Test
    fun testComparableMin() {
        val heap = BinaryHeap.createMinPriorityQueue<Int>()
        heap.insert(25)
        heap.insert(5)
        heap.insert(12)

        heap.pop().map { it `should be equal to` 5 }
        heap.pop().map { it `should be equal to` 12 }

        heap.insert(15)
        heap.insert(1)
        heap.insert(13)

        heap.peek().map { it `should be equal to` 1 }
        heap.pop().map { it `should be equal to` 1 }
        heap.pop().map { it `should be equal to` 13 }
        heap.pop().map { it `should be equal to` 15 }
        heap.pop().map { it `should be equal to` 25 }

        (heap.pop() is None) `should be equal to` true
    }

    @Test
    fun testCompareMax() {
        val heap = BinaryHeap.createMaxPriorityQueue<Int> { t1, t2 -> t1.compareTo(t2) }
        heap.insert(15)
        heap.insert(12)
        heap.insert(5)
        heap.insert(25)

        heap.pop().map { it `should be equal to` 25 }
        heap.pop().map { it `should be equal to` 15 }

        heap.insert(13)
        heap.insert(1)

        heap.peek().map { it `should be equal to` 13 }
        heap.pop().map { it `should be equal to` 13 }
        heap.pop().map { it `should be equal to` 12 }
        heap.pop().map { it `should be equal to` 5 }
        heap.pop().map { it `should be equal to` 1 }

        (heap.pop() is None) `should be equal to` true
    }

    @Test
    fun testCompareMin() {
        val heap = BinaryHeap.createMinPriorityQueue<Int> { t1, t2 -> t1.compareTo(t2) }
        heap.insert(25)
        heap.insert(5)
        heap.insert(12)
        heap.insert(15)

        heap.pop().map { it `should be equal to` 5 }
        heap.pop().map { it `should be equal to` 12 }

        heap.insert(1)
        heap.insert(13)

        heap.peek().map { it `should be equal to` 1 }
        heap.pop().map { it `should be equal to` 1 }
        heap.pop().map { it `should be equal to` 13 }
        heap.pop().map { it `should be equal to` 15 }
        heap.pop().map { it `should be equal to` 25 }

        (heap.pop() is None) `should be equal to` true
    }

    @Test
    fun testCompareByMax() {
        val heap = BinaryHeap.createMaxPriorityQueue<Int, Int> { it }
        heap.insert(15)
        heap.insert(25)
        heap.insert(12)

        heap.pop().map { it `should be equal to` 25 }
        heap.pop().map { it `should be equal to` 15 }

        heap.insert(1)
        heap.insert(5)
        heap.insert(13)

        heap.peek().map { it `should be equal to` 13 }
        heap.pop().map { it `should be equal to` 13 }
        heap.pop().map { it `should be equal to` 12 }
        heap.pop().map { it `should be equal to` 5 }
        heap.pop().map { it `should be equal to` 1 }

        (heap.pop() is None) `should be equal to` true
    }

    @Test
    fun testCompareByMin() {
        val heap = BinaryHeap.createMinPriorityQueue<Int, Int> { it }
        heap.insert(12)
        heap.insert(25)
        heap.insert(5)

        heap.pop().map { it `should be equal to` 5 }
        heap.pop().map { it `should be equal to` 12 }

        heap.insert(13)
        heap.insert(15)
        heap.insert(1)

        heap.peek().map { it `should be equal to` 1 }
        heap.pop().map { it `should be equal to` 1 }
        heap.pop().map { it `should be equal to` 13 }
        heap.pop().map { it `should be equal to` 15 }
        heap.pop().map { it `should be equal to` 25 }

        (heap.pop() is None) `should be equal to` true
    }

    @Test
    fun testComparableArrayMax() {
        val array = arrayOf(5, 12)
        val heap = BinaryHeap.createMaxPriorityQueueFromArray(array)

        heap.insert(25)
        heap.insert(15)

        heap.pop().map { it `should be equal to` 25 }
        heap.pop().map { it `should be equal to` 15 }

        heap.insert(1)
        heap.insert(13)

        heap.peek().map { it `should be equal to` 13 }
        heap.pop().map { it `should be equal to` 13 }
        heap.pop().map { it `should be equal to` 12 }
        heap.pop().map { it `should be equal to` 5 }
        heap.pop().map { it `should be equal to` 1 }

        (heap.pop() is None) `should be equal to` true
    }

    @Test
    fun testComparableArrayMin() {
        val array = arrayOf(12, 25)
        val heap = BinaryHeap.createMinPriorityQueueFromArray(array)

        heap.insert(5)

        heap.pop().map { it `should be equal to` 5 }
        heap.pop().map { it `should be equal to` 12 }

        heap.insert(15)
        heap.insert(1)
        heap.insert(13)

        heap.peek().map { it `should be equal to` 1 }
        heap.pop().map { it `should be equal to` 1 }
        heap.pop().map { it `should be equal to` 13 }
        heap.pop().map { it `should be equal to` 15 }
        heap.pop().map { it `should be equal to` 25 }

        (heap.pop() is None) `should be equal to` true
    }

    @Test
    fun testCompareMaxArray() {
        val array = arrayOf(15, 5)
        val heap = BinaryHeap.createMaxPriorityQueueFromArray(array) { t1, t2 ->
            t1.compareTo(t2)
        }

        heap.insert(12)
        heap.insert(25)

        heap.pop().map { it `should be equal to` 25 }
        heap.pop().map { it `should be equal to` 15 }

        heap.insert(13)
        heap.insert(1)

        heap.peek().map { it `should be equal to` 13 }
        heap.pop().map { it `should be equal to` 13 }
        heap.pop().map { it `should be equal to` 12 }
        heap.pop().map { it `should be equal to` 5 }
        heap.pop().map { it `should be equal to` 1 }

        (heap.pop() is None) `should be equal to` true
    }

    @Test
    fun testCompareMinArray() {
        val array = arrayOf(15, 12)
        val heap = BinaryHeap.createMinPriorityQueueFromArray(array) { t1, t2 ->
            t1.compareTo(t2)
        }

        heap.insert(25)
        heap.insert(5)

        heap.pop().map { it `should be equal to` 5 }
        heap.pop().map { it `should be equal to` 12 }

        heap.insert(1)
        heap.insert(13)

        heap.peek().map { it `should be equal to` 1 }
        heap.pop().map { it `should be equal to` 1 }
        heap.pop().map { it `should be equal to` 13 }
        heap.pop().map { it `should be equal to` 15 }
        heap.pop().map { it `should be equal to` 25 }

        (heap.pop() is None) `should be equal to` true
    }

    @Test
    fun testCompareByMaxArray() {
        val array = arrayOf(12)
        val heap = BinaryHeap.createMaxPriorityQueueFromArray<Int, Int>(array) { it }

        heap.insert(15)
        heap.insert(25)

        heap.pop().map { it `should be equal to` 25 }
        heap.pop().map { it `should be equal to` 15 }

        heap.insert(1)
        heap.insert(5)
        heap.insert(13)

        heap.peek().map { it `should be equal to` 13 }
        heap.pop().map { it `should be equal to` 13 }
        heap.pop().map { it `should be equal to` 12 }
        heap.pop().map { it `should be equal to` 5 }
        heap.pop().map { it `should be equal to` 1 }

        (heap.pop() is None) `should be equal to` true
    }

    @Test
    fun testCompareByMinArray() {
        val array = arrayOf(5, 12)
        val heap = BinaryHeap.createMinPriorityQueueFromArray<Int, Int>(array) { it }

        heap.insert(25)

        heap.pop().map { it `should be equal to` 5 }
        heap.pop().map { it `should be equal to` 12 }

        heap.insert(13)
        heap.insert(15)
        heap.insert(1)

        heap.peek().map { it `should be equal to` 1 }
        heap.pop().map { it `should be equal to` 1 }
        heap.pop().map { it `should be equal to` 13 }
        heap.pop().map { it `should be equal to` 15 }
        heap.pop().map { it `should be equal to` 25 }

        (heap.pop() is None) `should be equal to` true
    }

    @Test
    fun testResize() {
        val heap = BinaryHeap.createMaxPriorityQueue<Int>()

        (0 until 1000).forEach { heap.insert(it) }
        repeat(1010) { heap.pop() }
        heap.size() `should be equal to` 0
        heap.insert(10)
        heap.insert(11)

        heap.pop().map { it `should be equal to` 11 }
        heap.pop().map { it `should be equal to` 10 }
    }
}