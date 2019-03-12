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
import arrow.core.toOption

class ResizingArrayStack<T>(capacity: Int = DEFAULT_CAPACITY) : Stack<T> {
    private var stack: Array<T?> = arrayOfNulls<Any>(capacity) as Array<T?>
    private var size = 0

    override fun isEmpty(): Boolean = size == 0

    private fun isFull(): Boolean = size == stack.size

    override fun size(): Int = size

    override fun peek(): Option<T> = if (size == 0)
        None
    else
        stack[size - 1].toOption()

    override fun pop(): Option<T> {
        val elem = peek()

        if (elem is None)
            return elem

        stack[--size] = null

        if (isOneQuarterFull())
            resizeArray(stack.size / RESIZE_FACTOR)

        return elem
    }

    override fun push(elem: T) {
        if (isFull())
            resizeArray(size * RESIZE_FACTOR)

        stack[size++] = elem
    }

    private fun resizeArray(capacity: Int) {
        stack = stack.copyOf(capacity)
    }

    private fun isOneQuarterFull(): Boolean {
        return size > 0 &&
                size == stack.size / 4
    }

    override fun forEach(action: (T) -> Unit) {
        for (elem in stack)
            if (elem != null)
                action(elem)
    }

    override fun <A> map(transform: (T) -> A): Stack<A> {
        val transformedStack: Array<A?> = arrayOfNulls<Any>(stack.size)
                as Array<A?>

        for (i in 0 until stack.size) {
            val elem = stack[i]
            if (elem == null)
                transformedStack[i] = null
            else
                transformedStack[i] = transform(elem)
        }

        return ResizingArrayStack(transformedStack, size)
    }

    override fun clean() {
        stack = arrayOfNulls<Any>(DEFAULT_CAPACITY) as Array<T?>
        size = 0
    }

    private constructor(stack: Array<T?>, size: Int) : this() {
        this.stack = stack
        this.size = size
    }

    companion object {
        const val RESIZE_FACTOR = 2
        const val DEFAULT_CAPACITY = 2
    }
}