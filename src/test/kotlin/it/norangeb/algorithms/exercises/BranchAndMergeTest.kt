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

        val list1 = arrayListOf<String>()
        val list2 = arrayListOf<String>()
        val source = mockk<Scanner>()
        val destination1 = mockk<PrintStream>()
        val destination2 = mockk<PrintStream>()

        every { source.hasNextLine() } returnsMany
                listOf(true, true, true, true, true, false)
        every { source.nextLine() } returnsMany
                listOf(3, 1, 4, 2, 0).map { it.toString() }
        every { destination1.println(any() as String) } answers { list1.add(arg(0)) }
        every { destination2.println(any() as String) } answers { list2.add(arg(0)) }

        branch?.call(
            BranchAndMerge(Int::class.java, this::defaultComparator),
            source,
            destination1,
            destination2
        )

        list1.map { it.toInt() }
            .toIntArray() `should equal` intArrayOf(3, 2)
        list2.map { it.toInt() }
            .toIntArray() `should equal` intArrayOf(1, 4, 0)
    }

    @Test
    fun noBranchTest() {
        val branch = BranchAndMerge::class.memberFunctions
            .find { it.name == "branch" }
            .also { it?.isAccessible = true }

        val list1 = arrayListOf<String>()
        val list2 = arrayListOf<String>()
        val source = mockk<Scanner>()
        val destination1 = mockk<PrintStream>()
        val destination2 = mockk<PrintStream>()

        every { source.hasNextLine() } returnsMany
                listOf(true, true, true, true, true, false)
        every { source.nextLine() } returnsMany
                listOf(1, 2, 3, 10, 12).map { it.toString() }
        every { destination1.println(any() as String) } answers { list1.add(arg(0)) }

        branch?.call(
            BranchAndMerge(Int::class.java, this::defaultComparator),
            source,
            destination1,
            destination2
        )

        list1.map { it.toInt() }
            .toIntArray() `should equal` intArrayOf(1, 2, 3, 10, 12)
        list2.map { it.toInt() }
            .toIntArray() `should equal` intArrayOf()
    }

    @Test
    fun mergeTest() {
        val merge = BranchAndMerge::class.memberFunctions
            .find { it.name == "merge" }
            .also { it?.isAccessible = true }

        val list = arrayListOf<String>()
        val source1 = mockk<Scanner>()
        val source2 = mockk<Scanner>()
        val destination = mockk<PrintStream>()

        every { source1.hasNextLine() } returnsMany
                listOf(true, true, true, false)
        every { source1.nextLine() } returnsMany
                listOf(3, 2).map { it.toString() }
        every { source2.hasNextLine() } returnsMany
                listOf(true, true, true, true, false)
        every { source2.nextLine() } returnsMany
                listOf(1, 4, 0).map { it.toString() }
        every { destination.println(any() as String) }
            .answers { list.add(arg(0)) }
        every { destination.println(any() as String?) }
            .answers { list.add(arg(0)) }

        merge?.call(
            BranchAndMerge(Int::class.java, this::defaultComparator),
            source1,
            source2,
            destination
        )

        list.map { it.toInt() }
            .toIntArray() `should equal` intArrayOf(1, 3, 2, 4, 0)
    }

    @Test
    fun switchSourceTest() {
        val switchSource = BranchAndMerge::class.memberFunctions
            .find { it.name == "switchSource" }
            .also { it?.isAccessible = true }

        val source1 = mockk<Scanner>()
        val source2 = mockk<Scanner>()

        val result1 = switchSource?.call(
            BranchAndMerge(Int::class.java, this::defaultComparator),
            source1,
            source1,
            source2
        )
        val result2 = switchSource?.call(
            BranchAndMerge(Int::class.java, this::defaultComparator),
            source2,
            source1,
            source2
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
            BranchAndMerge(Int::class.java, this::defaultComparator),
            destination1,
            destination1,
            destination2
        )
        val result2 = switchDestination?.call(
            BranchAndMerge(Int::class.java, this::defaultComparator),
            destination2,
            destination1,
            destination2
        )

        result1 `should equal` destination2
        result2 `should equal` destination1
    }

    @Test
    fun mergeTailEmptyTest() {
        val mergeTail = BranchAndMerge::class.memberFunctions
            .find { it.name == "mergeTail" }
            .also { it?.isAccessible = true }

        val list = arrayListOf<String>()
        val source1 = mockk<Scanner>()
        val source2 = mockk<Scanner>()
        val destination = mockk<PrintStream>()

        every { source1.hasNextLine() } returns false
        every { source2.hasNextLine() } returns false
        every { destination.println(any() as String) } answers { list.add(arg(0)) }

        mergeTail?.call(
            BranchAndMerge(Int::class.java, this::defaultComparator),
            source1,
            source2,
            destination
        )

        list.map { it.toInt() }
            .toIntArray() `should equal` intArrayOf()
    }

    @Test
    fun mergeTailFirstEmptyTest() {
        val mergeTail = BranchAndMerge::class.memberFunctions
            .find { it.name == "mergeTail" }
            .also { it?.isAccessible = true }

        val list = arrayListOf<String>()
        val source1 = mockk<Scanner>()
        val source2 = mockk<Scanner>()
        val destination = mockk<PrintStream>()

        every { source1.hasNextLine() } returns false
        every { source2.hasNextLine() } returnsMany listOf(true, true, false)
        every { source2.nextLine() } returnsMany
                listOf(3, 7).map { it.toString() }
        every { destination.println(any() as String) }
            .answers { list.add(arg(0)) }

        mergeTail?.call(
            BranchAndMerge(Int::class.java, this::defaultComparator),
            source1,
            source2,
            destination
        )

        list.map { it.toInt() }
            .toIntArray() `should equal` intArrayOf(3, 7)
    }

    @Test
    fun mergeTailSecondEmptyTest() {
        val mergeTail = BranchAndMerge::class.memberFunctions
            .find { it.name == "mergeTail" }
            .also { it?.isAccessible = true }

        val list = arrayListOf<String>()
        val source1 = mockk<Scanner>()
        val source2 = mockk<Scanner>()
        val destination = mockk<PrintStream>()

        every { source1.hasNextLine() } returnsMany listOf(true, false)
        every { source1.nextLine() } returns "1"
        every { source2.hasNextLine() } returns false
        every { destination.println(any() as String) }
            .answers { list.add(arg(0)) }

        mergeTail?.call(
            BranchAndMerge(Int::class.java, this::defaultComparator),
            source1,
            source2,
            destination
        )

        list.map { it.toInt() }
            .toIntArray() `should equal` intArrayOf(1)
    }

    @Test
    fun sortAlreadySortedTest() {
        val filePath = "/tmp/test"
        val ps = PrintStream(filePath)
        val testSet = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 14, 16, 21, 34, 45)

        testSet.forEach { ps.println(it) }
        ps.close()

        BranchAndMerge(Int::class.java, this::defaultComparator)
            .sort(File(filePath))

        File(filePath).readLines()
            .map { it.toInt() } `should equal` testSet
    }

    @Test
    fun sortTest() {
        val filePath = "/tmp/test"
        val ps = PrintStream(filePath)
        val testSet = listOf(6, 2, 39, 4, 5, 11, 7, 99, 9, 10, 3, 16, 1, 45, 44)

        testSet.forEach { ps.println(it) }
        ps.close()

        BranchAndMerge(Int::class.java, this::defaultComparator)
            .sort(File(filePath))

        File(filePath).readLines()
            .map { it.toInt() } `should equal` testSet.sorted()
    }

    @Test
    fun reverseSortTest() {
        val filePath = "/tmp/test"
        val ps = PrintStream(filePath)
        val testSet = listOf(6, 2, 39, 4, 5, 11, 7, 99, 9, 10, 3, 16, 1, 45, 44)

        testSet.forEach { ps.println(it) }
        ps.close()

        BranchAndMerge(Int::class.java) { it1, it2 -> it2.compareTo(it1) }
            .sort(File(filePath))

        File(filePath).readLines()
            .map { it.toInt() } `should equal` testSet.sorted().reversed()
    }

    data class Person(val firstName: String, val lastName: String, val age: Int)

    @Test
    fun sortPersonsByAge() {
        val filePath = "/tmp/test"
        val ps = PrintStream(filePath)
        val gson = Gson()
        val testSet = listOf(
            Person("Name1", "Surname1", 25),
            Person("Name2", "Surname2", 15),
            Person("Name3", "Surname3", 39),
            Person("Name4", "Surname4", 10),
            Person("Name5", "Surname5", 4)
        )

        testSet.map { gson.toJson(it) }
            .forEach { ps.println(it) }
        ps.close()

        val sortByAge = { p1: Person, p2: Person ->
            p1.age.compareTo(p2.age)
        }

        BranchAndMerge(Person::class.java, sortByAge)
            .sort(File(filePath))

        File(filePath).readLines()
            .map { gson.fromJson(it, Person::class.java) } `should equal`
                testSet.sortedBy { it.age }
    }

    private fun <T> defaultComparator(obj1: T, obj2: T): Int {
        return (obj1 as Comparable<T>).compareTo(obj2)
    }
}