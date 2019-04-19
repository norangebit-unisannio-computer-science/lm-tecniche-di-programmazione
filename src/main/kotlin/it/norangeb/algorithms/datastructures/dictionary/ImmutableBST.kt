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

import arrow.core.*

class ImmutableBST<K : Comparable<K>, V> : OrderedDictionary<K, V> {
    private var root: Option<Node<K, V>> = None

    override fun get(key: K): Option<V> = get(root, key).map { it.value }

    private fun get(node: Option<Node<K, V>>, key: K): Option<Node<K, V>> = node
        .flatMap {
            when {
                it.key == key -> it.toOption()
                key > it.key -> get(it.right, key)
                else -> get(it.left, key)
            }
        }

    override fun set(key: K, value: V) {
        root = set(root, key, value)
    }

    private fun set(
        node: Option<Node<K, V>>,
        key: K, value: V
    ): Option<Node<K, V>> = node.fold(
        { Node(key, value) },
        {
            when {
                key == it.key -> it.clone(value = value)
                key > it.key -> it.clone(right = set(it.right, key, value))
                else -> it.clone(left = set(it.left, key, value))
            }
        }
    ).toOption()

    override fun delete(key: K) {
        root = delete(root, key)
    }

    private fun delete(
        node: Option<Node<K, V>>,
        key: K
    ): Option<Node<K, V>> = node.flatMap { deleteNode(it, key) }

    private fun deleteNode(
        node: Node<K, V>,
        key: K
    ): Option<Node<K, V>> = when {
        node.key == key -> when {
            node.left is None && node.right is None -> None
            node.left is None -> node.right
            node.right is None -> node.left
            else -> node.right.map { deleteNodeWithTwoChild(node, it) }
        }

        key > node.key -> node.clone(
            right = delete(node.right, key)
        ).toOption()

        else -> node.clone(left = delete(node.left, key)).toOption()
    }

    private fun deleteNodeWithTwoChild(
        father: Node<K, V>,
        rightChild: Node<K, V>
    ): Node<K, V> {
        val replacement = down(rightChild) { left }
        return replacement
            .clone(
                left = father.left,
                right = delete(rightChild.toOption(), replacement.key)
            )
    }

    override fun contains(key: K): Boolean = get(root, key) is Some

    override fun isEmpty(): Boolean = root is None

    override fun size(): Int = size(root)

    private fun size(node: Option<Node<K, V>>) = node.fold(
        { 0 },
        { it.child + 1 }
    )

    override fun max(): Option<K> = root.map {
        down(it) { right }.key
    }

    override fun min(): Option<K> = root.map {
        down(it) { left }.key
    }

    private fun down(
        node: Node<K, V>,
        side: Node<K, V>.() -> Option<Node<K, V>>
    ): Node<K, V> = side(node)
        .fold({ node }, { down(it, side) })

    override fun select(pos: Int): Option<K> = select(root, pos)

    private fun select(
        node: Option<Node<K, V>>,
        pos: Int
    ): Option<K> =
        node.flatMap {
            val leftChild = size(it.left)
            when {
                leftChild > pos -> select(it.left, pos)
                leftChild < pos -> select(it.right, pos - leftChild - 1)
                else -> it.key.toOption()
            }
        }

    override fun rank(key: K): Int = rank(root, key)

    private fun rank(node: Option<Node<K, V>>, key: K): Int = node.map {
            when {
                it.key == key -> it.child
                key < it.key -> rank(it.left, key)
                else -> size(it.left) + 1 + rank(it.right, key)
            }
        }.getOrElse { 0 }

    override fun floor(key: K): Option<K> = floor(root, key).map { it.key }

    private fun floor(node: Option<Node<K, V>>, key: K): Option<Node<K, V>> {
        return node.flatMap {
            when {
                it.key == key -> node
                key < it.key -> floor(it.left, key)
                else -> floorRightIfPossible(it, key)
            }
        }
    }

    private fun floorRightIfPossible(
        node: Node<K, V>,
        key: K
    ): Option<Node<K, V>> {
        val possibleFlor = floor(node.right, key)
        return if (possibleFlor is Some)
            possibleFlor
        else
            node.toOption()
    }

    override fun ceiling(key: K): Option<K> = ceiling(root, key).map { it.key }

    private fun ceiling(node: Option<Node<K, V>>, key: K): Option<Node<K, V>> {
        return node.flatMap {
            when {
                it.key == key -> node
                key > it.key -> ceiling(it.right, key)
                else -> ceilingLeftIfpossible(it, key)
            }
        }
    }

    private fun ceilingLeftIfpossible(
        node: Node<K, V>,
        key: K
    ): Option<Node<K, V>> {
        val possibleCeiling = ceiling(node.left, key)
        return if (possibleCeiling is Some)
            possibleCeiling
        else node.toOption()
    }

    override fun <R> inOrder(transform: (K) -> R) = inOrder(root, transform)

    private fun <R> inOrder(node: Option<Node<K, V>>, action: (K) -> R) {
        node.map {
            inOrder(it.left, action)
            action(it.key)
            inOrder(it.right, action)
        }
    }

    override fun <R> postOrder(transform: (K) -> R) = postOrder(root, transform)

    private fun <R> postOrder(node: Option<Node<K, V>>, action: (K) -> R) {
        node.map {
            postOrder(it.left, action)
            postOrder(it.right, action)
            action(it.key)
        }
    }

    override fun <R> preOrder(transform: (K) -> R) = preOrder(root, transform)

    private fun <R> preOrder(node: Option<Node<K, V>>, action: (K) -> R) {
        node.map {
            action(it.key)
            preOrder(it.left, action)
            preOrder(it.right, action)
        }
    }

    data class Node<K : Comparable<K>, V>(
        val key: K,
        val value: V,
        val left: Option<Node<K, V>> = None,
        val right: Option<Node<K, V>> = None,
        val child: Int = 0
    ) {
        fun clone(
            key: K = this.key,
            value: V = this.value,
            left: Option<Node<K, V>> = this.left,
            right: Option<Node<K, V>> = this.right
        ): Node<K, V> {
            val leftChild = left.fold({ 0 }, { it.child + 1 })
            val rightChild = right.fold({ 0 }, { it.child + 1 })
            return Node(key, value, left, right, leftChild + rightChild)
        }
    }
}