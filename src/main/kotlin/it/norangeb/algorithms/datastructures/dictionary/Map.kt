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
import arrow.core.getOrElse
import arrow.core.toOption

class Map<K, V> : Dictionary<K, V> {
    private var size = 0
    private var head: Option<Node<K, V>> = None

    override fun get(key: K): Option<V> = head.flatMap { node ->
        node.find { elem.first == key }
            .map { it.elem.second }
    }

    override fun set(key: K, value: V) = head
        .flatMap { it.find { elem.first == key } }
        .fold(
            {
                size++
                val node = Node(Pair(key, value), head)
                head = node.toOption()
            },
            { it.elem = Pair(key, value) }
        )

    override fun delete(key: K) {
        head.flatMap {
            it.find { next.map { it.elem.first == key }.getOrElse { false } }
        }.map {
            size--
            it.next = it.next.flatMap { it.next }
        }
    }

    override fun contains(key: K): Boolean = get(key) != None

    override fun isEmpty(): Boolean = size == 0

    override fun size(): Int = size

    data class Node<K, V>(var elem: Pair<K, V>, var next: Option<Node<K, V>>) {
        fun find(
            predicate: Node<K, V>.() -> Boolean
        ): Option<Node<K, V>> = when {
            predicate(this) -> this.toOption()
            else -> next.flatMap { it.find(predicate) }
        }
    }
}