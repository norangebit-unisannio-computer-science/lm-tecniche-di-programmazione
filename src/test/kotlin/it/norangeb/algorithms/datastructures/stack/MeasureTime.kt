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

package it.norangeb.algorithms.datastructures.stack

import org.koin.dsl.module.module
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject
import org.koin.standalone.StandAloneContext.startKoin
import java.io.PrintStream
import kotlin.random.Random
import kotlin.system.measureNanoTime

fun main(args: Array<String>) {
    startKoin(listOf(stackModule))

    val outPushData = PrintStream(args[0])
    val outPushPopData = PrintStream(args[1])

    MeasureTime().run(outPushData, outPushPopData)
}

val stackModule = module {
    single { ResizingArrayStack<Int>() as Stack<Int> }
}

class MeasureTime : KoinComponent {
    private val stack: Stack<Int> by inject()

    fun run(outPushData: PrintStream, outPushPopData: PrintStream) {
        (0..100000).map {
            measureNanoTime {
                stack.push(1)
            }
        }.forEach {
            outPushData.println(it)
        }

        val random = Random(System.currentTimeMillis())
        stack.clean()

        (0..100000).map {
            measureNanoTime {
                if (random.nextBoolean())
                    stack.push(1)
                else
                    stack.pop()
            }
        }.forEach {
            outPushPopData.println(it)
        }
    }
}