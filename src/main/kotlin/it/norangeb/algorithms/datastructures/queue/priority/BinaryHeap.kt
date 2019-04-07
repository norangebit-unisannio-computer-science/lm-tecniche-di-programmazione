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
import arrow.core.Option
import arrow.core.toOption

class BinaryHeap<T> private constructor(
    private var array: Array<T?>,
    private val shouldExchange: (T, T) -> Boolean
) : PriorityQueue<T> {
    private var size: Int = 0

    override fun insert(elem: T) {
        if (isFull())
            resizeArray(array.size * WAY)

        array[++size] = elem
        pushUp(array, size)
    }

    override fun pop(): Option<T> {
        if (size < 1) return None
        val result = array[FIRST_ELEMENT]

        exchange(array, FIRST_ELEMENT, size)
        array[size--] = null
        pullDown(array, FIRST_ELEMENT)

        if (isOneQuarterFull())
            resizeArray(array.size / WAY)

        return result.toOption()
    }

    private fun print() {
        print("|")
        array.forEach {
            print(" $it |")
        }
        println()
    }

    override fun peek(): Option<T> = if (size >= 1)
        array[1].toOption()
    else
        None

    override fun isEmpty(): Boolean = size == 0

    override fun size(): Int = size

    private fun pushUp(array: Array<T?>, k: Int) {
        when {
            k <= 1 -> return
            shouldExchange(
                array[k / WAY] ?: return,
                array[k] ?: return
            ) -> {
                exchange(array, k / WAY, k)
                pushUp(array, k / WAY)
            }
            else -> return
        }
    }

    private fun pullDown(array: Array<T?>, k: Int) {
        var i = k
        while (i * WAY <= size) {
            val child = i * WAY

            when {
                array[child + 1] == null -> exchange(array, i, child)
                shouldExchange(
                    array[child] ?: return,
                    array[child + 1] ?: return
                ) -> exchange(array, i, child + 1)
                else -> exchange(array, i, child)
            }

            i *= WAY
        }

        pushUp(array, i)
    }

    private fun exchange(array: Array<T?>, i: Int, j: Int) {
        array[i] = array[j].also {
            array[j] = array[i]
        }
    }

    private fun isFull(): Boolean = size + 1 == array.size

    private fun isOneQuarterFull(): Boolean {
        return size > 1 &&
                size + 1 == array.size / 4
    }

    private fun resizeArray(capacity: Int) {
        array = array.copyOf(capacity)
    }

    companion object : COPriorityQueue {
        private const val DEFAULT_CAPACITY = 3
        private const val WAY = 2
        private const val FIRST_ELEMENT = 1

        override fun <C : Comparable<C>> createMaxPriorityQueue(): PriorityQueue<C> {
            val array: Array<C?> = arrayOfNulls<Comparable<Any>>(DEFAULT_CAPACITY) as Array<C?>
            return BinaryHeap(array) { t1, t2 ->
                t1 < t2
            }
        }

        override fun <C : Comparable<C>> createMinPriorityQueue(): PriorityQueue<C> {
            val array: Array<C?> = arrayOfNulls<Comparable<Any>>(DEFAULT_CAPACITY) as Array<C?>
            return BinaryHeap(array) { t1, t2 ->
                t1 > t2
            }
        }

        override fun <T> createMaxPriorityQueue(compare: (T, T) -> Int): PriorityQueue<T> {
            val array: Array<T?> = arrayOfNulls<Any>(DEFAULT_CAPACITY) as Array<T?>
            return BinaryHeap(array) { t1, t2 ->
                compare(t1, t2) < 0
            }
        }

        override fun <T> createMinPriorityQueue(compare: (T, T) -> Int): PriorityQueue<T> {
            val array: Array<T?> = arrayOfNulls<Any>(DEFAULT_CAPACITY) as Array<T?>
            return BinaryHeap(array) { t1, t2 ->
                compare(t1, t2) > 0
            }
        }

        override fun <T, C : Comparable<C>> createMaxPriorityQueue(compareBy: (T) -> C): PriorityQueue<T> {
            val array: Array<T?> = arrayOfNulls<Any>(DEFAULT_CAPACITY) as Array<T?>
            return BinaryHeap(array) { t1, t2 ->
                compareBy(t1) < compareBy(t2)
            }
        }

        override fun <T, C : Comparable<C>> createMinPriorityQueue(compareBy: (T) -> C): PriorityQueue<T> {
            val array: Array<T?> = arrayOfNulls<Any>(DEFAULT_CAPACITY) as Array<T?>
            return BinaryHeap(array) { t1, t2 ->
                compareBy(t1) > compareBy(t2)
            }
        }

        override fun <C : Comparable<C>> createMaxPriorityQueueFromArray(array: Array<C>): PriorityQueue<C> {
            val heap = BinaryHeap.createMaxPriorityQueue<C>()
            array.forEach { heap.insert(it) }
            return heap
        }

        override fun <C : Comparable<C>> createMinPriorityQueueFromArray(array: Array<C>): PriorityQueue<C> {
            val heap = BinaryHeap.createMinPriorityQueue<C>()
            array.forEach { heap.insert(it) }
            return heap
        }

        override fun <T> createMaxPriorityQueueFromArray(
            array: Array<T>,
            compare: (T, T) -> Int
        ): PriorityQueue<T> {
            val initArray: Array<T?> = arrayOfNulls<Any>(DEFAULT_CAPACITY) as Array<T?>
            val heap = BinaryHeap(initArray) { t1, t2 ->
                compare(t1, t2) < 0
            }

            array.forEach { heap.insert(it) }

            return heap
        }

        override fun <T> createMinPriorityQueueFromArray(
            array: Array<T>,
            compare: (T, T) -> Int
        ): PriorityQueue<T> {
            val initArray: Array<T?> = arrayOfNulls<Any>(DEFAULT_CAPACITY) as Array<T?>
            val heap = BinaryHeap(initArray) { t1, t2 ->
                compare(t1, t2) > 0
            }

            array.forEach { heap.insert(it) }

            return heap
        }

        override fun <T, C : Comparable<C>> createMaxPriorityQueueFromArray(
            array: Array<T>,
            compareBy: (T) -> C
        ): PriorityQueue<T> {
            val initArray: Array<T?> = arrayOfNulls<Any>(DEFAULT_CAPACITY) as Array<T?>
            val heap = BinaryHeap(initArray) { t1, t2 ->
                compareBy(t1) < compareBy(t2)
            }

            array.forEach { heap.insert(it) }

            return heap
        }

        override fun <T, C : Comparable<C>> createMinPriorityQueueFromArray(
            array: Array<T>,
            compareBy: (T) -> C
        ): PriorityQueue<T> {
            val initArray: Array<T?> = arrayOfNulls<Any>(DEFAULT_CAPACITY) as Array<T?>
            val heap = BinaryHeap(initArray) { t1, t2 ->
                compareBy(t1) > compareBy(t2)
            }

            array.forEach { heap.insert(it) }

            return heap
        }
    }
}