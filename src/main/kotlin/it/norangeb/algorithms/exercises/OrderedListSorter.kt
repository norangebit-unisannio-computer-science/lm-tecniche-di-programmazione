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

import it.norangeb.algorithms.datastructures.queue.priority.BinaryHeap

object OrderedListSorter {
    fun sortUnsignedInt(vararg lists: List<Int>): List<Int> {
        val bias = lists.map { it.first() }.sorted().first()
        val upperBound = lists.map { it.last() }.sorted().last()
        val bucket = Array(upperBound - bias + 1) { 0 }

        lists.forEach {
            it.forEach {
                bucket[it - bias] += 1
            }
        }

        bucket.forEachIndexed { index, _ ->
            if (index == 0)
                return@forEachIndexed

            bucket[index] += bucket[index - 1]
        }

        val outList = arrayOfNulls<Int>(bucket[upperBound - bias])

        lists.forEach {
            it.forEach {
                outList[bucket[it - bias] - 1] = it
                bucket[it - bias] -= 1
            }
        }

        return (outList as Array<Int>).toList()
    }

    fun <T : Comparable<T>> sort(vararg lists: List<T>): List<T> {
        val heap = BinaryHeap.createMinPriorityQueue<Node<T>, T> { it.value }
        lists.forEachIndexed { index, list ->
            heap.insert(Node(list.first(), index, 0))
        }

        val outList = ArrayList<T>()

        while (!heap.isEmpty()) {
            heap.pop().map {
                outList.add(it.value)
                val k = it.k
                val n = it.n + 1

                if (lists[k].size > n)
                    heap.insert(Node(lists[k][n], k, n))
            }
        }

        return outList
    }

    data class Node<T>(val value: T, val k: Int, val n: Int)
}