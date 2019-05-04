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

import arrow.core.None
import arrow.core.Option
import arrow.core.Some
import arrow.core.toOption

class HashTable<K, V> : Dictionary<K, V> {
    private val array = Array<Option<Node<K, V>>>(HASHTABLE_SIZE) { None }
    private var size = 0

    override fun get(key: K): Option<V> {
        return get(array[getIndex(key)], key)
            .map { it.value }
    }

    private fun get(node: Option<Node<K, V>>, key: K): Option<Node<K, V>> {
        return node.flatMap {
            when (key) {
                it.key -> node
                else -> get(it.next, key)
            }
        }
    }

    override fun set(key: K, value: V) {
        val index = getIndex(key)

        array[index] = set(
            array[index],
            key,
            value
        ).toOption()
    }

    private fun set(
        node: Option<Node<K, V>>,
        key: K,
        value: V
    ): Node<K, V> {
        return node.fold(
            {
                size++
                Node(key, value)
            },
            {
                when (it.key) {
                    key -> it.clone(value = value)
                    else -> it.clone(next = set(it.next, key, value).toOption())
                }
            }
        )
    }

    override fun delete(key: K) {
        val index = getIndex(key)

        array[index] = delete(array[index], key)
    }

    private fun delete(node: Option<Node<K, V>>, key: K): Option<Node<K, V>> {
        return node.flatMap {
            when (it.key) {
                key -> {
                    size--
                    it.next
                }
                else -> it.clone(next = delete(it.next, key)).toOption()
            }
        }
    }

    override fun contains(key: K): Boolean = get(key) is Some

    override fun isEmpty(): Boolean = size == 0

    override fun size(): Int = size

    private fun getIndex(key: K): Int {
        return key.hashCode().and(0x7fffffff) % HASHTABLE_SIZE
    }

    data class Node<K, V>(
        val key: K,
        val value: V,
        val next: Option<Node<K, V>> = None
    ) {
        fun clone(
            key: K = this.key,
            value: V = this.value,
            next: Option<Node<K, V>> = this.next
        ) = Node(key, value, next)
    }

    companion object {
        const val HASHTABLE_SIZE = 40
    }
}