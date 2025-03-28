package org.alexiscrack3

import org.apache.beam.sdk.Pipeline
import org.apache.beam.sdk.options.PipelineOptionsFactory
import org.apache.beam.sdk.transforms.Create
import org.apache.beam.sdk.transforms.DoFn
import org.apache.beam.sdk.transforms.ParDo
import org.apache.beam.sdk.values.PCollection

fun main() {
    inMemoryCollection()
}

fun inMemoryCollection() {
    val options = PipelineOptionsFactory.create()
    val pipeline: Pipeline = Pipeline.create(options)

    // Now create the PCollection using list of strings
    val words: PCollection<String> =
        pipeline.apply(
            Create.of("To", "be", "or", "not", "to", "be", "that", "is", "the", "question")
        )


    // Create a numerical PCollection
    val numbers: PCollection<Int> =
        pipeline.apply(
            Create.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
        )
    words.apply("Print words", ParDo.of(PrintWords()))
    numbers.apply("Print numbers", ParDo.of(PrintIntegers()))

    pipeline.run().waitUntilFinish()
}

class PrintWords: DoFn<String, String>() {
    @ProcessElement
    @Throws(Exception::class)
    fun processElement(c: ProcessContext) {
        val word = c.element()
        println(word)
        c.output(word)
    }
}

class PrintIntegers: DoFn<Int, Int>() {
    @ProcessElement
    @Throws(Exception::class)
    fun processElement(c: ProcessContext) {
        val number = c.element()
        println(number)
        c.output(number)
    }
}