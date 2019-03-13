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

package it.norangeb.algorithms.datastructures.queue

import org.koin.dsl.module.module
import org.koin.standalone.KoinComponent
import org.koin.standalone.StandAloneContext.startKoin
import org.koin.standalone.inject
import java.io.PrintStream
import kotlin.math.roundToInt
import kotlin.random.Random
import kotlin.system.measureNanoTime

fun main(args: Array<String>) {
    startKoin(listOf(queueModule))

    val outEnqueueData = PrintStream(args[0])
    val outEnDeQueueData = PrintStream(args[1])

    TimeMeasure(outEnqueueData, outEnDeQueueData)
        .run()
}

val queueModule = module {
    single { ResizingArrayQueue<Int>() as Queue<Int> }
}

class TimeMeasure(
    private val outEnqueueData: PrintStream,
    private val outEnDeQueueData: PrintStream
) : KoinComponent {
    private val queue: Queue<Int> by inject()

    fun run() {
        (0 until NUMBER_OF_ITERATION).map {
            queue.clean()
            val numberOfSamples = DEFAULT_SAMPLES *
                    Math.pow(GROWTH_FACTOR.toDouble(), it.toDouble())
                        .roundToInt()

            val totalTime = measureNanoTime {
                repeat(numberOfSamples) {
                    queue.enqueue(1)
                }
            }

            Pair(numberOfSamples, totalTime)
        }.forEach {
            outEnqueueData.println("${it.first}, ${it.second}")
        }

        val random = Random(System.currentTimeMillis())

        (0 until NUMBER_OF_ITERATION).map {
            queue.clean()
            val numberOfSamples = DEFAULT_SAMPLES *
                    Math.pow(GROWTH_FACTOR.toDouble(), it.toDouble())
                        .roundToInt()

            val totalTime = measureNanoTime {
                repeat(numberOfSamples) {
                    if (random.nextBoolean())
                        queue.enqueue(1)
                    else
                        queue.dequeue()
                }
            }

            Pair(numberOfSamples, totalTime)
        }.forEach {
            outEnDeQueueData.println("${it.first}, ${it.second}")
        }
    }

    companion object {
        const val DEFAULT_SAMPLES = 1000
        const val NUMBER_OF_ITERATION = 15
        const val GROWTH_FACTOR = 2
    }
}