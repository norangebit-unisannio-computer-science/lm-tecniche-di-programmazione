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
import org.amshove.kluent.should
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class MapTest {
    lateinit var map: Dictionary<String, Int>

    @BeforeEach
    fun init() {
        map = Map()
    }

    @Test
    fun testMap() {
        map.size() `should be equal to` 0
        map.contains("TRE") `should be` false

        map["TRE"] = 3
        map["UNO"] = 0

        map["DUE"] `should be` None
        map["TRE"] should { this == Some(3) }

        map["QUATTRO"] = 4
        map["UNO"] = 1
        map["DUE"] = 2

        map.size() `should be equal to` 4
        map.contains("DUE") `should be` true
        map["UNO"] should { this == Some(1) }

        map["CINQUE"] = 5
        map.delete("DUE")
        map.delete("SEI")

        map.contains("DUE") `should be` false
        map.size() `should be equal to` 4
    }
}