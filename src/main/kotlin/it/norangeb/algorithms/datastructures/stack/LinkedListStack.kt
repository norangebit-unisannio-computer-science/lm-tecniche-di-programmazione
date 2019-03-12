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

package it.norangeb.algorithms.datastructures.stack

import arrow.core.None
import arrow.core.Option
import arrow.core.Some
import arrow.core.toOption

class LinkedListStack<T>() : Stack<T> {

    data class Node<T>(val value: T, var next: Option<Node<T>>)

    private var head: Option<Node<T>> = None
    private var size = 0

    override fun push(elem: T) {
        val node = Node(elem, head)
        head = node.toOption()
        size++
    }

    override fun pop(): Option<T> {
        val elem = head.map { it.value }

        if (elem is None)
            return elem

        head = head.flatMap { it.next }
        size--
        return elem
    }

    override fun peek(): Option<T> {
        return head.map { it.value }
    }

    override fun size(): Int = size

    override fun isEmpty(): Boolean = size == 0

    override fun clean() {
        head = None
        size = 0
    }

    override fun <A> map(transform: (T) -> A): Stack<A> {
        var newHead: Option<Node<A>> = None
        var newCurrent: Option<Node<A>> = None
        var oldCurrent = head

        while (oldCurrent is Some) {
            val node = Node(transform(oldCurrent.t.value), None).toOption()

            if (newHead is None)
                newHead = node

            newCurrent.map { it.next = node }
            newCurrent = node

            oldCurrent = oldCurrent.flatMap { it.next }
        }

        return LinkedListStack(newHead, size)
    }

    private constructor(head: Option<Node<T>>, size: Int) : this() {
        this.head = head
        this.size = size
    }

    override fun forEach(action: (T) -> Unit) {
        var next = head

        while (next is Some) {
            action(next.t.value)
            next = next.t.next
        }
    }
}