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
import org.amshove.kluent.`should be`
import org.amshove.kluent.shouldEqual
import org.junit.jupiter.api.Test


class ImmutableBSTTest {

    @Test
    fun test() {
        val orderedMap: OrderedDictionary<String, Int> = ImmutableBST()

        orderedMap.isEmpty() `should be` true
        orderedMap.size() `should be equal to` 0
        orderedMap.contains("UNO") `should be` false
        orderedMap.max() shouldEqual  None
        orderedMap.min() shouldEqual  None

        orderedMap["QUATTRO"] = 4
        orderedMap["UNO"] = 0

        orderedMap.contains("UNO") `should be` true

        orderedMap["TRE"] = 3
        orderedMap["UNO"] = 1

        orderedMap.size() `should be equal to` 3
        orderedMap["UNO"] shouldEqual Some(1)

        orderedMap["DUE"] = 2

        orderedMap["DUE"] shouldEqual Some(2)
        orderedMap.max() shouldEqual Some("UNO")
        orderedMap.min() shouldEqual Some("DUE")
    }

    @Test
    fun testDelete() {
        val orderedMap: OrderedDictionary<Int, String> = ImmutableBST()

        orderedMap[7] = "sette"
        orderedMap[2] = "due"
        orderedMap[3] = "tre"
        orderedMap[10] = "dieci"
        orderedMap[1] = "uno"
        orderedMap[8] = "otto"
        orderedMap[13] = "tredici"
        orderedMap[5] = "cinque"
        orderedMap[11] = "undici"

        orderedMap.size() `should be equal to` 9

        // delete absent key
        orderedMap.delete(0)

        orderedMap.size() `should be equal to` 9

        //delete node with no child
        orderedMap.delete(1)

        orderedMap.size() `should be equal to` 8
        orderedMap.min() shouldEqual  Some(2)

        //delete node with only right child
        orderedMap.delete(2)

        orderedMap.size() `should be equal to` 7
        orderedMap.min() shouldEqual  Some(3)

        //delete node with two child
        orderedMap.delete(10)

        orderedMap.size() `should be equal to` 6

        orderedMap.delete(13)
        //delete node with only left child
        orderedMap.delete(11)

        orderedMap.size() `should be equal to` 4
        orderedMap.max() shouldEqual Some(8)
    }

    @Test
    fun testRanAndSelect() {
        val orderedMap: OrderedDictionary<Int, Int> = ImmutableBST()

        orderedMap.select(0) shouldEqual None
        orderedMap.rank(0) `should be equal to` 0

        orderedMap[9] = 9
        orderedMap[4] = 4
        orderedMap[14] = 14

        orderedMap.rank(5) `should be equal to` 1
        orderedMap.rank(20) `should be equal to` 3
        orderedMap.rank(3) `should be equal to` 0
        orderedMap.select(0) shouldEqual Some(4)
        orderedMap.select(1) shouldEqual Some(9)
        orderedMap.select(2) shouldEqual Some(14)
        orderedMap.select(3) shouldEqual None

        orderedMap[13] = 13
        orderedMap[15] = 15
        orderedMap[3] = 3
        orderedMap[5] = 5
        orderedMap[2] = 2
        orderedMap[6] = 6
        orderedMap[12] = 12
        orderedMap[16] = 16
        orderedMap[1] = 1

        orderedMap.select(0) shouldEqual orderedMap.min()
        orderedMap.select(11) shouldEqual orderedMap.max()

        orderedMap[0] = 0
        orderedMap[11] = 11
        orderedMap[10] = 10
        orderedMap[17] = 17
        orderedMap[18] = 18
        orderedMap[7] = 7
        orderedMap[8] = 8

        orderedMap.select(-1) shouldEqual None
        orderedMap.select(0) shouldEqual Some(0)
        orderedMap.select(1) shouldEqual Some(1)
        orderedMap.select(5) shouldEqual Some(5)
        orderedMap.select(8) shouldEqual Some(8)
        orderedMap.select(9) shouldEqual Some(9)
        orderedMap.select(10) shouldEqual Some(10)
        orderedMap.select(14) shouldEqual Some(14)
        orderedMap.select(17) shouldEqual Some(17)
        orderedMap.select(18) shouldEqual Some(18)
        orderedMap.select(19) shouldEqual None

        val orderList = ArrayList<Int>()
        orderedMap.inOrder { orderList.add(it) }

        orderList shouldEqual listOf(
            0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12,
            13, 14, 15, 16, 17, 18
        )

        val postOrderList = ArrayList<Int>()
        orderedMap.postOrder { postOrderList.add(it) }

        postOrderList shouldEqual listOf(
            0, 1, 2, 3, 8, 7, 6, 5, 4, 10, 11, 12,
            13, 18, 17, 16, 15, 14, 9
        )

        val preOrderList = ArrayList<Int>()
        orderedMap.preOrder { preOrderList.add(it) }

        preOrderList shouldEqual listOf(
            9, 4, 3, 2, 1, 0, 5, 6, 7, 8, 14, 13,
            12, 11, 10, 15, 16, 17, 18
        )
    }

    @Test
    fun testFloorAndCeiling() {
        val orderedMap: OrderedDictionary<Char, Boolean> = ImmutableBST()

        orderedMap.floor('Z') shouldEqual None
        orderedMap.ceiling('Z') shouldEqual None

        orderedMap['S'] = true
        orderedMap['X'] = true
        orderedMap['E'] = true
        orderedMap['A'] = true
        orderedMap['C'] = true
        orderedMap['R'] = true
        orderedMap['H'] = true
        orderedMap['M'] = true

        orderedMap.floor('G') shouldEqual Some('E')
        orderedMap.floor('D') shouldEqual Some('C')
        orderedMap.floor('R') shouldEqual Some('R')
        orderedMap.ceiling('Q') shouldEqual Some('R')
        orderedMap.ceiling('A') shouldEqual Some('A')
    }
}