package org.alexiscrack3.connectors

import org.apache.beam.sdk.transforms.DoFn

class PrintIntegers: DoFn<Int, Int>() {
    @ProcessElement
    @Throws(Exception::class)
    fun processElement(c: ProcessContext) {
        val number = c.element()
        println(number)
        c.output(number)
    }
}
