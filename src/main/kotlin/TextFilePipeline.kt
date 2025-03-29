package org.alexiscrack3

import org.alexiscrack3.connectors.PrintStrings
import org.apache.beam.sdk.Pipeline
import org.apache.beam.sdk.io.TextIO
import org.apache.beam.sdk.options.PipelineOptionsFactory
import org.apache.beam.sdk.transforms.*
import org.apache.beam.sdk.values.PCollection
import org.apache.beam.sdk.values.TypeDescriptors
import java.util.*

class TextFilePipeline : FeaturePipeline() {
    override fun executePipeline(): Pipeline {
        val options = PipelineOptionsFactory.create()
        val pipeline: Pipeline = Pipeline.create(options)
        val filePath = "src/main/resources/lipsum.txt"

        val input: PCollection<String> = pipeline
            .apply("Reading file", TextIO.read().from(filePath))
            .apply(Filter.by(ProcessFunction { line -> line.isNotEmpty() }))

        val sample: PTransform<PCollection<String>, PCollection<MutableIterable<String>>> = Sample.fixedSizeGlobally(10)

        val sampleLines: PCollection<String> = input
            .apply(sample)
            .apply(Flatten.iterables())
            .apply("Print lines", ParDo.of(PrintStrings()))

        val words = pipeline
            .apply(TextIO.read().from(filePath))
            .apply(
                FlatMapElements.into(TypeDescriptors.strings()).via(
                    ProcessFunction { line: String ->
                        Arrays.asList(
                            *line.split("[^\\p{L}]+".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                        )
                    })
            )
            .apply(Filter.by(ProcessFunction { word: String -> word.isNotEmpty() }))

        val sampleWords = words
            .apply(sample)
            .apply(Flatten.iterables())
            .apply("Print words", ParDo.of(PrintStrings()))

        sampleWords.apply(TextIO.write().to("build/sample-words"))

        return pipeline
    }
}