package org.alexiscrack3

import org.apache.beam.sdk.Pipeline
import org.apache.beam.sdk.util.construction.renderer.PipelineDotRenderer
import java.nio.file.Files
import java.nio.file.Paths

abstract class FeaturePipeline {
    fun run() {
        val pipeline = executePipeline()
        pipeline.run().waitUntilFinish()
        visualizePipeline(pipeline)
    }

    protected abstract fun executePipeline(): Pipeline

    private fun visualizePipeline(pipeline: Pipeline) {
        val dotRepresentation = PipelineDotRenderer.toDotString(pipeline)
        val outputPath = Paths.get("build/pipeline_graph.dot")
        Files.write(outputPath, dotRepresentation.toByteArray())
        println("DOT file saved: ${outputPath.toAbsolutePath()}")
    }
}