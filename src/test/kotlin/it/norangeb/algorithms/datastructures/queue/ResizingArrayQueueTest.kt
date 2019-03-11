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

package it.norangeb.algorithms.datastructures.queue

import arrow.core.None
import arrow.core.Some
import arrow.core.getOrElse
import org.amshove.kluent.`should be equal to`
import org.junit.jupiter.api.Test

class ResizingArrayQueueTest {

    @Test
    fun testEnqueue() {
        val queue = ResizingArrayQueue<Int>()
        queue.size() `should be equal to` 0
        queue.enqueue(1)
        queue.size() `should be equal to` 1
        queue.enqueue(1)
        queue.size() `should be equal to` 2
    }

    @Test
    fun testIsEmpty() {
        val queue = ResizingArrayQueue<Int>()

        queue.isEmpty() `should be equal to` true
        queue.enqueue(1)
        queue.isEmpty() `should be equal to` false
    }

    @Test
    fun testDequeue() {
        val queue = ResizingArrayQueue<Int>()

        queue.size() `should be equal to` 0
        (queue.dequeue() is None) `should be equal to` true
        queue.enqueue(1)
        queue.dequeue().getOrElse { 0 } `should be equal to` 1
        queue.size() `should be equal to` 0
    }

    @Test
    fun testPeek() {
        val queue = ResizingArrayQueue<Int>()

        (queue.peek() is None) `should be equal to` true
        queue.enqueue(1)
        (queue.peek() is Some) `should be equal to` true
        queue.size() `should be equal to` 1
    }

    @Test
    fun testIncreaseSize() {
        val queue = ResizingArrayQueue<Int>()

        queue.enqueue(1)
        queue.enqueue(2)
        queue.enqueue(3)
        queue.enqueue(4)
        queue.enqueue(5)
    }

    @Test
    fun testDecreaseSize() {
        val queue = ResizingArrayQueue<Int>()

        queue.enqueue(1)
        queue.enqueue(2)
        queue.enqueue(3)
        queue.enqueue(4)
        queue.enqueue(5)

        queue.dequeue()
        queue.dequeue()
        queue.dequeue()
        queue.dequeue()
        queue.dequeue()
    }

    @Test
    fun testMap() {
        val queue = ResizingArrayQueue<Int>()

        queue.enqueue(1)
        queue.enqueue(2)
        queue.enqueue(3)
        queue.enqueue(4)
        queue.enqueue(5)

        val newQueue = queue.map { it * 2 } as ResizingArrayQueue

        newQueue.size() `should be equal to` 5
        newQueue.dequeue().getOrElse { 0 } `should be equal to` 2
        newQueue.dequeue().getOrElse { 0 } `should be equal to` 4
        newQueue.dequeue().getOrElse { 0 } `should be equal to` 6
        newQueue.dequeue().getOrElse { 0 } `should be equal to` 8
        newQueue.dequeue().getOrElse { 0 } `should be equal to` 10
    }

    @Test
    fun testForEach() {
        val queue = ResizingArrayQueue<Int>()

        queue.enqueue(1)
        queue.enqueue(2)
        queue.enqueue(3)
        queue.enqueue(4)
        queue.enqueue(5)

        var x = 0
        queue.forEach { x++ }

        x `should be equal to` 5
    }

    @Test
    fun testClean() {
        val queue = ResizingArrayQueue<Int>()

        queue.enqueue(1)
        queue.enqueue(2)
        queue.clean()

        queue.size() `should be equal to` 0
    }
}