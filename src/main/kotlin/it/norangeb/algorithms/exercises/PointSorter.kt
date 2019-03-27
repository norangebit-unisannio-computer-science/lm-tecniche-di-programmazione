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

package it.norangeb.algorithms.exercises

import it.norangeb.algorithms.sorting.Mergesort

data class Point(val x: Double, val y: Double) {

    constructor(x: Int, y: Int) : this(x.toDouble(), y.toDouble())

    operator fun minus(other: Point): Point {
        val x = this.x - other.x
        val y = this.y - other.y

        return Point(x, y)
    }

    fun theta(): Double = Math.atan2(y, x)

    companion object {
        fun thetaComparator(
            p1: Point,
            p2: Point,
            origin: Point = Point(0, 0)
        ): Int {
            var theta1 = (p1 - origin).theta()
            var theta2 = (p2 - origin).theta()

            if (theta1 < 0) theta1 += 2 * Math.PI
            if (theta2 < 0) theta2 += 2 * Math.PI

            return theta1.compareTo(theta2)
        }
    }
}

typealias P = Point

fun main(args: Array<String>) {
    if (args.size < 4 || args.size % 2 != 0) return

    val origin = P(args[0].toDouble(), args[1].toDouble())

    val list = ArrayList<Point>()

    for (i in 2 until args.size step 2)
        list.add(P(args[i].toDouble(), args[i + 1].toDouble()))

    val points = list.toTypedArray()

    val compare = { p1: Point, p2: Point ->
        Point.thetaComparator(p1, p2, origin)
    }

    Mergesort.sortWith(points, compare)

    println("origin in $origin")
    points.forEach {
        println(it)
    }
}