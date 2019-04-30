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
import arrow.core.Some
import org.amshove.kluent.`should be equal to`
import org.amshove.kluent.shouldEqual
import org.junit.jupiter.api.Test

class HashTableTest {

    @Test
    fun test() {
        val map = HashTable<Int, String>()

        map.size() `should be equal to` 0
        map.isEmpty() `should be equal to` true
        map.contains(3) `should be equal to` false

        map[1] = "Zero"
        map[4] = "Quattro"

        map.contains(1) `should be equal to` true
        map[1] shouldEqual Some("Zero")
        map.size() `should be equal to` 2
        map.isEmpty() `should be equal to` false
        map.delete(5)

        map[1] = "Uno"

        map[1] shouldEqual Some("Uno")

        map[41] = "41"
        map[81] = "81"
        map[121] = "121"

        map.contains(41) `should be equal to` true
        map.size() `should be equal to` 5
        map[1] shouldEqual Some("Uno")
        map[81] shouldEqual Some("81")
        map[121] shouldEqual Some("121")

        map.delete(41)

        map.size() `should be equal to` 4
        map.contains(41) `should be equal to` false

        map.delete(121)

        map.size() `should be equal to` 3
        map.contains(121) `should be equal to` false
        map.contains(81) `should be equal to` true
        map[1] shouldEqual Some("Uno")
        map[41] shouldEqual None
    }
}