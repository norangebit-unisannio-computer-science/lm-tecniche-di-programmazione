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
import arrow.core.Option
import arrow.core.toOption

class ResizingArrayQueue<T>(capacity: Int = DEFAULT_CAPACITY) : Queue<T> {
    private var queue: Array<T?> = arrayOfNulls<Any>(capacity) as Array<T?>
    private var size = 0
    private var tail = 0
    private var head = 0

    override fun isEmpty(): Boolean = size == 0

    private fun isFull(): Boolean = queue.size == size

    override fun size(): Int = size

    override fun enqueue(elem: T) {
        if (isFull())
            resizeArray(size * RESIZE_FACTOR)

        queue[tail] = elem
        tail = (tail + 1) % queue.size
        size++
    }

    override fun dequeue(): Option<T> {
        val elem = peek()

        if (elem is None)
            return elem

        queue[head] = null
        head = (head + 1) % queue.size
        size--

        if (isOneQuarterFull())
            resizeArray(queue.size / RESIZE_FACTOR)

        return elem
    }

    override fun peek(): Option<T> {
        if (isEmpty())
            return None

        return queue[head].toOption()
    }

    private fun resizeArray(capacity: Int) {
        val copy: Array<T?> = arrayOfNulls<Any>(capacity) as Array<T?>

        when {
            head >= tail -> {
                for (i in head until queue.size)
                    copy[i - head] = queue[i]

                val alreadyInsert = queue.size - head

                for (i in 0 until tail)
                    copy[i + alreadyInsert] = queue[i]
            }

            else ->
                for (i in head until tail)
                    copy[i - head] = queue[i]
        }

        queue = copy
        head = 0
        tail = size
    }

    private fun isOneQuarterFull(): Boolean = size == queue.size / 4 &&
            size > 0

    override fun forEach(action: (T) -> Unit) {
        for (elem in queue)
            if (elem != null)
                action(elem)
    }

    override fun <A> map(transform: (T) -> A): Queue<A> {
        val transformedQueue: Array<A?> = arrayOfNulls<Any>(queue.size)
                as Array<A?>

        for (i in 0 until queue.size) {
            val elem = queue[i]
            if (elem == null)
                transformedQueue[i] = null
            else
                transformedQueue[i] = transform(elem)
        }

        return ResizingArrayQueue(transformedQueue, head, tail, size)
    }

    override fun clean() {
        queue = arrayOfNulls<Any>(DEFAULT_CAPACITY) as Array<T?>
        size = 0
        tail = 0
        head = 0
    }

    private constructor(
        queue: Array<T?>,
        head: Int,
        tail: Int,
        size: Int
    ) : this() {
        this.queue = queue
        this.head = head
        this.tail = tail
        this.size = size
    }

    companion object {
        const val RESIZE_FACTOR = 2
        const val DEFAULT_CAPACITY = 2
    }
}