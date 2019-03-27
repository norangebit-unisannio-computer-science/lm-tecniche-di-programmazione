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

import com.google.gson.Gson
import java.io.File
import java.io.PrintStream
import java.util.Scanner

class BranchAndMerge<T>(
    private val typeClass: Class<T>,
    var compare: (obj1: T, obj2: T) -> Int
) {
    companion object {
        private const val TMP_FILE_PATH_1 = "/tmp/file1"
        private const val TMP_FILE_PATH_2 = "/tmp/file2"
        private val GSON = Gson()
    }

    fun sort(source: File) {
        tryBranch(source)

        while (!isSorted()) {
            tryMerge(source)
            tryBranch(source)
        }
    }

    private fun isSorted(): Boolean {
        val sc1 = Scanner(File(TMP_FILE_PATH_1))
        val sc2 = Scanner(File(TMP_FILE_PATH_2))
        return !sc1.hasNextLine() || !sc2.hasNextLine()
    }

    private fun tryBranch(fileToSort: File) {
        try {
            val destination1 = PrintStream(File(TMP_FILE_PATH_1))
            val destination2 = PrintStream(File(TMP_FILE_PATH_2))
            val source = Scanner(fileToSort)

            branch(source, destination1, destination2)

            destination1.close()
            destination2.close()
            source.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun branch(
        source: Scanner,
        destination1: PrintStream,
        destination2: PrintStream
    ) {
        var destination = destination1
        var current: T
        var last: T? = null

        while (source.hasNextLine()) {
            current = fromJson(source.nextLine())
            if (isLess(current, last))
                destination = switchDestination(
                    destination, destination1, destination2
                )
            destination.println(toJson(current))
            last = current
        }
    }

    private fun tryMerge(fileToSort: File) {
        try {
            val source1 = Scanner(File(TMP_FILE_PATH_1))
            val source2 = Scanner(File(TMP_FILE_PATH_2))
            val destination = PrintStream(fileToSort)

            merge(source1, source2, destination)

            source1.close()
            source2.close()
            destination.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun merge(source1: Scanner, source2: Scanner, destination: PrintStream) {
        var source = source1

        var current: T
        var cache: T? = null

        while (source1.hasNextLine() && source2.hasNextLine()) {
            if (cache == null)
                cache = fromJson(
                    switchSource(source, source1, source2).nextLine()
                )

            current = fromJson(source.nextLine())

            if (!isLess(current, cache)) {
                source = switchSource(source, source1, source2)
                current = cache!!.also { cache = current }
            }

            destination.println(toJson(current))
        }
        destination.println(toJson(cache!!))

        mergeTail(source1, source2, destination)
    }

    private fun mergeTail(
        source1: Scanner,
        source2: Scanner,
        destination: PrintStream
    ) {
        while (source1.hasNextLine())
            destination.println(source1.nextLine())

        while (source2.hasNextLine())
            destination.println(source2.nextLine())
    }

    private fun isLess(first: T, second: T?): Boolean {
        if (second == null)
            return false

        return compare(first, second) < 0
    }

    private fun switchDestination(
        currentDestination: PrintStream,
        destination1: PrintStream,
        destination2: PrintStream
    ) = when (currentDestination) {
        destination1 -> destination2
        else -> destination1
    }

    private fun switchSource(
        currentSource: Scanner,
        source1: Scanner,
        source2: Scanner
    ) = when (currentSource) {
        source1 -> source2
        else -> source1
    }

    private fun fromJson(json: String) = GSON.fromJson(json, typeClass)

    private fun toJson(obj: T) = GSON.toJson(obj)
}