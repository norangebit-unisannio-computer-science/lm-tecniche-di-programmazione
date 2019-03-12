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

class ResizingArrayBag<T>(capacity: Int = DEFAULT_CAPACITY) : Bag<T> {
    private var bag: Array<T?> = arrayOfNulls<Any?>(capacity) as Array<T?>
    private var size = 0

    override fun add(elem: T) {
        if (isFull())
            resizeArray(size * RESIZE_FACTOR)

        bag[size++] = elem
    }

    private fun isFull(): Boolean = size == bag.size

    private fun resizeArray(capacity: Int) {
        bag = bag.copyOf(capacity)
    }

    override fun size(): Int = size

    override fun isEmpty(): Boolean = size == 0

    override fun <A> map(transform: (T) -> A): Bag<A> {
        val newBag: Array<A?> = arrayOfNulls<Any?>(size) as Array<A?>

        for (i in 0 until size) {
            val elem = bag[i] ?: continue
            newBag[i] = transform(elem)
        }

        return ResizingArrayBag(size, newBag)
    }

    private constructor(size: Int, bag: Array<T?>) : this() {
        this.size = size
        this.bag = bag
    }

    override fun forEach(action: (T) -> Unit) {
        for (elem in bag)
            if (elem != null)
                action(elem)
    }

    override fun clear() {
        size = 0
        resizeArray(DEFAULT_CAPACITY)
    }

    companion object {
        const val DEFAULT_CAPACITY = 2
        const val RESIZE_FACTOR = 2
    }
}