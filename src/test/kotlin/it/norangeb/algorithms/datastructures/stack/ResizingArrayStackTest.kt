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
import arrow.core.getOrElse
import org.amshove.kluent.`should be equal to`
import org.junit.jupiter.api.Test

class ResizingArrayStackTest {

    @Test
    fun testPush() {
        val stack = ResizingArrayStack<Int>()

        stack.size() `should be equal to` 0
        stack.push(1)
        stack.size() `should be equal to` 1
    }

    @Test
    fun testIsEmpty() {
        val stack = ResizingArrayStack<Int>()

        stack.isEmpty() `should be equal to` true
        stack.push(1)
        stack.isEmpty() `should be equal to` false
    }

    @Test
    fun testPeek() {
        val stack = ResizingArrayStack<Int>()

        (stack.peek() is None) `should be equal to` true
        stack.push(1)
        (stack.peek().getOrElse { 0 }) `should be equal to` 1
        stack.size() `should be equal to` 1
    }

    @Test
    fun testPop() {
        val stack = ResizingArrayStack<Int>()

        (stack.pop() is None) `should be equal to` true
        stack.push(1)
        (stack.pop().getOrElse { 0 }) `should be equal to` 1
        stack.size() `should be equal to` 0
    }

    @Test
    fun testIncrease() {
        val stack = ResizingArrayStack<Int>()

        stack.push(1)
        stack.push(2)
        stack.push(3)
        stack.push(4)
        stack.push(5)
    }

    @Test
    fun testDescrese() {
        val stack = ResizingArrayStack<Int>()

        stack.push(1)
        stack.push(2)
        stack.push(3)
        stack.push(4)
        stack.push(5)

        stack.pop()
        stack.pop()
        stack.pop()
        stack.pop()
        stack.pop()
    }

    @Test
    fun testMap() {
        val stack = ResizingArrayStack<Int>()

        stack.push(1)
        stack.push(2)
        stack.push(3)
        stack.push(4)
        stack.push(5)

        val newStack = stack.map { it * 2 } as ResizingArrayStack

        newStack.size() `should be equal to` 5
        newStack.pop().getOrElse { 0 } `should be equal to` 10
        newStack.pop().getOrElse { 0 } `should be equal to` 8
        newStack.pop().getOrElse { 0 } `should be equal to` 6
        newStack.pop().getOrElse { 0 } `should be equal to` 4
        newStack.pop().getOrElse { 0 } `should be equal to` 2
    }

    @Test
    fun testForEach() {
        val stack = ResizingArrayStack<Int>()

        stack.push(1)
        stack.push(2)
        stack.push(3)
        stack.push(4)
        stack.push(5)

        var x = 0
        stack.forEach { x++ }

        x `should be equal to` 5
    }

    @Test
    fun testClean() {
        val stack = ResizingArrayStack<Int>()

        stack.push(1)
        stack.push(2)
        stack.clean()

        stack.size() `should be equal to` 0
    }
}
