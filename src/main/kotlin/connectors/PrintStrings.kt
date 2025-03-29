package org.alexiscrack3.connectors

import org.apache.beam.sdk.transforms.DoFn

class PrintStrings: DoFn<String, String>() {
    @ProcessElement
    @Throws(Exception::class)
    fun processElement(c: ProcessContext) {
        val str = c.element()
        println(str)
        c.output(str)
    }
}
