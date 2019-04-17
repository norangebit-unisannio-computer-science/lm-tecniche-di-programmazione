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

package it.norangeb.algorithms.datastructures.dictionary

import arrow.core.Option

interface Dictionary<K, V> {
    operator fun get(key: K): Option<V>
    operator fun set(key: K, value: V)
    fun delete(key: K)
    fun contains(key: K): Boolean
    fun isEmpty(): Boolean
    fun size(): Int
}

interface OrderedDictionary<K : Comparable<K>, V> : Dictionary<K, V> {
    fun max(): Option<K>
    fun min(): Option<K>
    fun floor(key: K): Option<K>
    fun ceiling(key: K): Option<K>
    fun select(pos: Int): Option<K>
    fun <R> preOrder(transform: (K) -> R)
    fun <R> inOrder(transform: (K) -> R)
    fun <R> postOrder(transform: (K) -> R)
}
