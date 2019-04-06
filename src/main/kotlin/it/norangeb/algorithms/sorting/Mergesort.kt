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

package it.norangeb.algorithms.sorting

object Mergesort : Sorter() {
    override fun <T> sort(array: Array<T>, isLess: (T, T) -> Boolean) {
        val auxiliary = arrayOfNulls<Any>(array.size) as Array<T>
        sort(array, auxiliary, 0, array.size - 1, isLess)
    }

    private fun <T> sort(
        array: Array<T>,
        auxiliary: Array<T>,
        low: Int,
        high: Int,
        isLess: (T, T) -> Boolean
    ) {
        if (high <= low) return

        val mid = low + (high - low) / 2

        sort(array, auxiliary, low, mid, isLess)
        sort(array, auxiliary, mid + 1, high, isLess)

        if (!isLess(array[mid+1], array[mid]))
            return

        merge(array, auxiliary, low, mid, high, isLess)
    }

    private inline fun <T> merge(
        array: Array<T>,
        auxiliary: Array<T>,
        low: Int,
        mid: Int,
        high: Int,
        isLess: (T, T) -> Boolean
    ) {
        for (i in low..high)
            auxiliary[i] = array[i]

        var i = low
        var j = mid + 1

        for (k in low..high)
            when {
                i > mid -> array[k] = auxiliary[j++]
                j > high -> array[k] = auxiliary[i++]
                isLess(auxiliary[j], auxiliary[i]) -> array[k] = auxiliary[j++]
                else -> array[k] = auxiliary[i++]
            }
    }
}