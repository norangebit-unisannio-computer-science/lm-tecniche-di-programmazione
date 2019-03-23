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

import io.mockk.every
import io.mockk.mockk
import org.amshove.kluent.`should equal`
import org.junit.jupiter.api.Test
import java.io.File
import java.io.PrintStream
import java.util.Scanner
import kotlin.reflect.full.memberFunctions
import kotlin.reflect.jvm.isAccessible

class BranchAndMergeTest {

    @Test
    fun branchTest() {
        val branch = BranchAndMerge::class.memberFunctions
            .find { it.name == "branch" }
            .also { it?.isAccessible = true }

        val list1 = arrayListOf<Int>()
        val list2 = arrayListOf<Int>()
        val source = mockk<Scanner>()
        val destination1 = mockk<PrintStream>()
        val destination2 = mockk<PrintStream>()

        every { source.hasNextInt() } returnsMany
                listOf(true, true, true, true, true, false)
        every { source.nextInt() } returnsMany listOf(3, 1, 4, 2, 0)
        every { destination1.println(any() as Int) } answers { list1.add(arg(0)) }
        every { destination2.println(any() as Int) } answers { list2.add(arg(0)) }

        branch?.call(BranchAndMerge, source, destination1, destination2)

        list1.toIntArray() `should equal` intArrayOf(3, 2)
        list2.toIntArray() `should equal` intArrayOf(1, 4, 0)
    }

    @Test
    fun noBranchTest() {
        val branch = BranchAndMerge::class.memberFunctions
            .find { it.name == "branch" }
            .also { it?.isAccessible = true }

        val list1 = arrayListOf<Int>()
        val list2 = arrayListOf<Int>()
        val source = mockk<Scanner>()
        val destination1 = mockk<PrintStream>()
        val destination2 = mockk<PrintStream>()

        every { source.hasNextInt() } returnsMany
                listOf(true, true, true, true, true, false)
        every { source.nextInt() } returnsMany listOf(1, 2, 3, 10, 12)
        every { destination1.println(any() as Int) } answers { list1.add(arg(0)) }

        branch?.call(BranchAndMerge, source, destination1, destination2)

        list1.toIntArray() `should equal` intArrayOf(1, 2, 3, 10, 12)
        list2.toIntArray() `should equal` intArrayOf()
    }

    @Test
    fun mergeTest() {
        val merge = BranchAndMerge::class.memberFunctions
            .find { it.name == "merge" }
            .also { it?.isAccessible = true }

        val list = arrayListOf<Int>()
        val source1 = mockk<Scanner>()
        val source2 = mockk<Scanner>()
        val destination = mockk<PrintStream>()

        every { source1.hasNextInt() } returnsMany
                listOf(true, true, true, false)
        every { source1.nextInt() } returnsMany listOf(3, 2)
        every { source2.hasNextInt() } returnsMany
                listOf(true, true, true, true, false)
        every { source2.nextInt() } returnsMany listOf(1, 4, 0)
        every { destination.println(any() as Int) } answers { list.add(arg(0)) }
        every { destination.println(any() as Int?) } answers { list.add(arg(0)) }

        merge?.call(BranchAndMerge, source1, source2, destination)

        list.toIntArray() `should equal` intArrayOf(1, 3, 2, 4, 0)
    }

    @Test
    fun switchSourceTest() {
        val switchSource = BranchAndMerge::class.memberFunctions
            .find { it.name == "switchSource" }
            .also { it?.isAccessible = true }

        val source1 = mockk<Scanner>()
        val source2 = mockk<Scanner>()

        val result1 = switchSource?.call(
            BranchAndMerge, source1, source1, source2
        )
        val result2 = switchSource?.call(
            BranchAndMerge, source2, source1, source2
        )

        result1 `should equal` source2
        result2 `should equal` source1
    }

    @Test
    fun switchDestinationTest() {
        val switchDestination = BranchAndMerge::class.memberFunctions
            .find { it.name == "switchDestination" }
            .also { it?.isAccessible = true }

        val destination1 = mockk<PrintStream>()
        val destination2 = mockk<PrintStream>()

        val result1 = switchDestination?.call(
            BranchAndMerge, destination1, destination1, destination2
        )
        val result2 = switchDestination?.call(
            BranchAndMerge, destination2, destination1, destination2
        )

        result1 `should equal` destination2
        result2 `should equal` destination1
    }

    @Test
    fun mergeTailEmptyTest() {
        val mergeTail = BranchAndMerge::class.memberFunctions
            .find { it.name == "mergeTail" }
            .also { it?.isAccessible = true }

        val list = arrayListOf<Int>()
        val source1 = mockk<Scanner>()
        val source2 = mockk<Scanner>()
        val destination = mockk<PrintStream>()

        every { source1.hasNextInt() } returns false
        every { source2.hasNextInt() } returns false
        every { destination.println(any() as Int) } answers { list.add(arg(0)) }

        mergeTail?.call(
            BranchAndMerge, source1, source2, destination
        )

        list.toIntArray() `should equal` intArrayOf()
    }

    @Test
    fun mergeTailFirstEmptyTest() {
        val mergeTail = BranchAndMerge::class.memberFunctions
            .find { it.name == "mergeTail" }
            .also { it?.isAccessible = true }

        val list = arrayListOf<Int>()
        val source1 = mockk<Scanner>()
        val source2 = mockk<Scanner>()
        val destination = mockk<PrintStream>()

        every { source1.hasNextInt() } returns false
        every { source2.hasNextInt() } returnsMany listOf(true, true, false)
        every { source2.nextInt() } returnsMany listOf(3, 7)
        every { destination.println(any() as Int) } answers { list.add(arg(0)) }

        mergeTail?.call(
            BranchAndMerge, source1, source2, destination
        )

        list.toIntArray() `should equal` intArrayOf(3, 7)
    }

    @Test
    fun mergeTailSecondEmptyTest() {
        val mergeTail = BranchAndMerge::class.memberFunctions
            .find { it.name == "mergeTail" }
            .also { it?.isAccessible = true }

        val list = arrayListOf<Int>()
        val source1 = mockk<Scanner>()
        val source2 = mockk<Scanner>()
        val destination = mockk<PrintStream>()

        every { source1.hasNextInt() } returnsMany listOf(true, false)
        every { source1.nextInt() } returns 1
        every { source2.hasNextInt() } returns false
        every { destination.println(any() as Int) } answers { list.add(arg(0)) }

        mergeTail?.call(
            BranchAndMerge, source1, source2, destination
        )

        list.toIntArray() `should equal` intArrayOf(1)
    }

    @Test
    fun sortAlreadySortedTest() {
        val path = "/tmp/test"
        val ps = PrintStream(path)
        val testSet = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 14, 16, 21, 34, 45)

        testSet.forEach { ps.println(it) }
        ps.close()

        BranchAndMerge.run(File(path))

        File(path).readLines().map { it.toInt() } `should equal` testSet
    }

    @Test
    fun sortTest() {
        val path = "/tmp/test"
        val ps = PrintStream(path)
        val testSet = listOf(6, 2, 39, 4, 5, 11, 7, 99, 9, 10, 3, 16, 1, 45, 44)

        testSet.forEach { ps.println(it) }
        ps.close()

        BranchAndMerge.run(File(path))

        File(path).readLines().map { it.toInt() } `should equal` testSet.sorted()
    }
}