/*
 * MIT License
 *
 * Copyright (c) 2019 norangebit
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, tryMerge, publish, distribute, sublicense, and/or sell
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

object PrefixAverage {
    fun prefixAverage(arrayIn: IntArray): IntArray {

        val arrayOut = IntArray(arrayIn.size)
        var currentSum = 0

        for (i in 0 until arrayIn.size) {
            currentSum += arrayIn[i]
            arrayOut[i] = currentSum / (i + 1)
        }

        return arrayOut
    }

    fun recursivePrefixAverage(arrayIn: IntArray, position: Int = 0, acc: Int = 0): IntArray {
        return when (position) {
            arrayIn.size -> IntArray(arrayIn.size)
            else -> {
                val newAcc = arrayIn[position] + acc
                val array = recursivePrefixAverage(arrayIn, position + 1, newAcc)
                array[position] = newAcc / (position + 1)
                array
            }
        }
    }
}
