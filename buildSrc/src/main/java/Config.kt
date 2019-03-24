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

object Config {
    object Versions {
        val arrow = "0.8.2"
        val kluent = "1.48"
        val koin = "1.0.2"
        val junit = "5.1.0"
        val spek = "2.0.1"
        val kotlin = "1.3.21"
        val mockk = "1.9.2"
        val gson = "2.8.5"
    }

    object Libs {
        val arrowCore = "io.arrow-kt:arrow-core:${Versions.arrow}"
        val koin = "org.koin:koin-core:${Versions.koin}"
        val junit = "org.junit.jupiter:junit-jupiter-api:${Versions.junit}"
        val kluent = "org.amshove.kluent:kluent:${Versions.kluent}"
        val spekDsl = "org.spekframework.spek2:spek-dsl-jvm:${Versions.spek}"
        val kotlinReflect = "org.jetbrains.kotlin:kotlin-reflect:${Versions.kotlin}"
        val spekRunner = "org.spekframework.spek2:spek-runner-junit5:${Versions.spek}"
        val mockk = "io.mockk:mockk:${Versions.mockk}"
        val gson = "com.google.code.gson:gson:${Versions.gson}"
    }
}